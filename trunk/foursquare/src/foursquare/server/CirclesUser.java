package foursquare.server;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class CirclesUser {
    private String loginId;
    private String firstName;
    private String lastName;
    private String contact;
    private boolean isNew = false;

    private CirclesUser(final String fourSquareId) {
        loginId = fourSquareId;

    }

    public static CirclesUser getUser(final String fourSquareId) {
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        FilterPredicate filter = new FilterPredicate("loginid",
                FilterOperator.EQUAL, fourSquareId);
        Query q = new Query("login").addFilter("loginid",
                FilterOperator.EQUAL, fourSquareId);
        PreparedQuery pq = service.prepare(q);
        List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(30));
        CirclesUser user = new CirclesUser(fourSquareId);
        if (result.isEmpty()) {
            user.isNew = true;
            user.loginId = fourSquareId;
            return user;
        }
        else {
            Entity entity = result.get(0);
            user.loginId = fourSquareId;
            user.isNew = false;
            if (entity.getProperty("firstName") != null) {
                user.firstName = entity.getProperty("firstName").toString();
            }
        }
        return user;

    }

    public void store() {

        Key key = KeyFactory.createKey("login", System
                .currentTimeMillis());

        Entity e = new Entity(key);
        e.setProperty("firstName", getFirstName());
        e.setProperty("lastName", getLastName());
        e.setProperty("loginid", getLoginId());
        e.setProperty("contact", getContact());
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        service.put(e);

    }

    public int getCheckinCount() {
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        FilterPredicate filter = new FilterPredicate("venuescheckin",
                FilterOperator.EQUAL, getLoginId());
        Query q = new Query("venuescheckin").addFilter("loginid",
                FilterOperator.EQUAL, getLoginId());
        PreparedQuery pq = service.prepare(q);
        List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
        return result.size();
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId
     *            the loginId to set
     */
    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact
     *            the contact to set
     */
    public void setContact(final String contact) {
        this.contact = contact;
    }

    /**
     * @return the isNew
     */
    public boolean isNew() {
        return isNew;
    }

}
