import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.PresenceShow;
import com.google.appengine.api.xmpp.PresenceType;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
public class PresenceMonitor extends HttpServlet {
    private static final Logger log = Logger.getLogger(PresenceMonitor.class
            .getName());
    TreeSet<String> jidList = new TreeSet<String>();

    @Override
    public void doPost(final HttpServletRequest req,
            final HttpServletResponse res) throws IOException {
        XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        Presence presence = xmpp.parsePresence(req);

        // Split the XMPP address (e.g., user@gmail.com)
        // from the resource (e.g., gmail.CD6EBC4A)
        JID jid = presence.getFromJid();
        if (!jidList.contains(jid.getId())) {
            jidList.add(jid.getId());
            xmpp.sendPresence(jid, PresenceType.AVAILABLE, PresenceShow.CHAT,
                    "New Personality Added on Sep 25 2011");
            log.warning("Sending presence to " + jid.getId());
        }

        // ...
    }
}