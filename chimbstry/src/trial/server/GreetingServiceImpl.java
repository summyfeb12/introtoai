package trial.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Logger;

import trial.client.GreetingService;
import trial.shared.FieldVerifier;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {
    private static final Logger log = Logger
            .getLogger(GreetingServiceImpl.class.getName());

    @Override
    public String greetServer(String input) throws IllegalArgumentException {
        if (input.equals("SPAMALL")) {
            File f = new File("WEB-INF/amm.txt");
            String s = "";
            String msgBody = "Thank you loyal chimbers, Finally i got a complete revamp of my brain. Now I can answer with even more power. There are a few issues still pending, and another major issue from Google's side. Knowing my power, they just provide me with 32 mb of memory. So i sometimes run out of memory, and cannot calculate precisely. In such cases, my answer will be in ALL CAPS. So if I answer in ALL CAPS, ITS AN INDICATION THAT I RAN OUT OF MEMORY, POSSIBLY BECAUSE A LOT OF USERS ACCESSED ME SIMULTANEOUSLY. Apart from that, feel free to enjoy my conversations. And yes, please show your support by liking me on facebook. http://www.facebook.com/pages/Chimbs/178151682260424 . ";
            XMPPService service = XMPPServiceFactory.getXMPPService();
            try {
                java.util.Scanner sc = new java.util.Scanner(f);
                while (sc.hasNext()) {
                    JID jid = new JID(sc.next().trim());
                    Message msg = new MessageBuilder().withRecipientJids(jid)
                            .withBody(msgBody).build();
                    boolean messageSent = false;
                    SendResponse status = service.sendMessage(msg);
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Spammed all";
        }
        if (input.startsWith("SENDMSG")) {
            input = input.replace("SENDMSG", "");
            String[] msgs = input.split(":");
            String email = msgs[0];
            String msg = msgs[1];

            JID jid1 = new JID(email.trim());
            log.info("here");
            Message msgse = new MessageBuilder().withRecipientJids(jid1)
                    .withBody(msg).build();
            XMPPService xmpp = XMPPServiceFactory.getXMPPService();

            SendResponse status = xmpp.sendMessage(msgse);
            return email + " and the msg is: " + msg;
        }
        // Verify that the input is valid. if
        if (!FieldVerifier.isValidName(input)) { // If the input is not valid,
            // throw an IllegalArgumentException back // to // the client.
            throw new IllegalArgumentException(
                    "Name must be at least 4 characters long");
        }

        String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        // Escape data from the client to avoid cross-site script //
        // vulnerabilities.
        input = escapeHtml(input);
        userAgent = escapeHtml(userAgent); // vulnerabilities.
        JID jid = new JID(input);

        String msgBody = "Thanks for adding me... Chimbs Rocks";
        Message msg = new MessageBuilder().withRecipientJids(jid)
                .withBody(msgBody).build();
        XMPPService xmpp = XMPPServiceFactory.getXMPPService();

        xmpp.sendInvitation(jid);
        SendResponse status = xmpp.sendMessage(msg);
        boolean messageSent = false;
        messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
        log.warning("The name : " + input + "has registered");
        Key key = KeyFactory.createKey("RegisteredUser", input);

        Entity e = new Entity(key);
        e.setProperty(
                "time",
                DateFormat.getDateInstance().format(
                        new Date(System.currentTimeMillis())));

        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        try {
            service.put(e);
        } catch (Exception alread) {
            return "I think you have previously registered";
        }

        return "Registered succesfully. Please check your google talk. ";
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
                .replaceAll(">", "&gt;");
    }
}
