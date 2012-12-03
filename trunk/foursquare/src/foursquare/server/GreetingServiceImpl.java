package foursquare.server;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteUser;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import foursquare.client.GreetingService;
import foursquare.client.VenueList;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {
    private static String CLIENT_ID = "M5SQTXRXWFYLXXKBYXL30JTAURDNLVAQCBQRSD0KB2MQ2NWP";
    private static String CLIENT_SECRET = "0PF0ZPS21SVKJ2DHT5MTHS1FQF1TCJH5WBCP514EQOL2LIWV";
    private static String PUSH_SECRET = "M1YKYF412WMNWUITQSQFAQWLXI4PBBEJSLZAAW3CTYU0HZMS";

    @Override
    public VenueList[] greetServer(final String input[])
            throws IllegalArgumentException {

        String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        String code = input[0];
        String location = input[1];
        FoursquareApi api = new FoursquareApi(CLIENT_ID, CLIENT_SECRET,
                "http://www.chimbsthebot.appspot.com");

        Result<VenuesSearchResult> result;
        try {
            api.authenticateCode(code);
            result = api
                    .venuesSearch(location, null,
                            null, null, null, null, null, null,
                            null, null, null);
            if (result.getMeta().getCode() == 200) {
                CompactVenue[] venues = result.getResult().getVenues();
                VenueList[] ret = new VenueList[venues.length];
                for (int i = 0; i < venues.length; i++) {
                    VenueList venue = new VenueList();
                    venue.setName(venues[i].getName());
                    venue.setId(venues[i].getId());
                    ret[i] = venue;
                }

                return ret;
            }
        } catch (Exception e) {

        }
        return new VenueList[1];
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html
     *            the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(final String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(
                        ">", "&gt;");
    }

    @Override
    public String structuredGreetServer(final VenueList name)
            throws IllegalArgumentException {
        String code = name.getCode();


        if (!name.isValidated()) {
            FoursquareApi api = new FoursquareApi(CLIENT_ID, CLIENT_SECRET,
                    "http://www.chimbsthebot.appspot.com");
            return "surl" + api.getAuthenticationUrl();
        }
        try {
            CirclesUser user = createOrGetUser(code);
            checkIn(code, name.getVenueId());

        } catch (FoursquareApiException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("code is " + code);
        }
        Logger logger = Logger.getLogger("impl");

        logger.info("xcode is " + name.getCode());
        return "success";
    }

    private CirclesUser createOrGetUser(final String appcode)
            throws FoursquareApiException {
        FoursquareApi api = new FoursquareApi(CLIENT_ID, CLIENT_SECRET,
                "http://www.chimbsthebot.appspot.com");
        api.authenticateCode(appcode);
        CompleteUser user = api.user("self").getResult();
        CirclesUser circlesUser = CirclesUser.getUser(user.getId());
        if (circlesUser.isNew()) {
            circlesUser.setFirstName(user.getFirstName());
            circlesUser.setLastName(user.getLastName());
            circlesUser.setLoginId(circlesUser.getLoginId());
            circlesUser.setContact(user.getContact().getEmail());
            circlesUser.store();
        }
        else {
            // User already exists
        }
        return circlesUser;
    }

    private void checkIn(final String appcode, final String venueId)
            throws FoursquareApiException {

        FoursquareApi api = new FoursquareApi(CLIENT_ID, CLIENT_SECRET,
                "http://www.chimbsthebot.appspot.com");

        api.authenticateCode(appcode);
        Result<CompleteVenue> result = api.venue(venueId);
        Logger.getLogger("test").info(
                "code is :+ " + result.getMeta().getCode()
                + result.getMeta().getErrorDetail()
                + result.getMeta().getErrorType());

        if (result.getMeta().getCode() == 200) {
            CompleteVenue venue = result.getResult();
            api.checkinsAdd(venueId, null, "Chimbs Rocks", "public",
                    "70.11,40.22",
                    null, null, null);

            CompleteUser user = api.user("self").getResult();

            Key key = KeyFactory.createKey("venuescheckin", System
                    .currentTimeMillis());

            Entity e = new Entity(key);
            e.setProperty("loginid", user.getId());
            e.setProperty("venueId", venue.getId());
            e.setProperty("venueName", venue.getName());
            e.setProperty("name", user.getFirstName());

            DatastoreService service = DatastoreServiceFactory
                    .getDatastoreService();
            service.put(e);
        }
        else {
            throw new RuntimeException("code is :+ "
                    + result.getMeta().getCode()
                    + "venue" + venueId
                    + result.getMeta().getErrorType());
        }

    }

    @Override
    public String getNumber(final String id) throws IllegalArgumentException {
        CirclesUser user = CirclesUser.getUser(id);
        return "Hello " + user.getFirstName() + ". You have "
                + user.getCheckinCount() + " points.";
    }
}
