import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aitools.programd.Core;
import org.aitools.programd.interfaces.Console;
import org.aitools.programd.util.URLTools;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import trial.server.Chimbsbot;

import com.google.api.GoogleAPI;
import com.google.api.detect.Detect;
import com.google.api.detect.DetectResult;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.PresenceShow;
import com.google.appengine.api.xmpp.PresenceType;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
public class XMPPReceiverServlet extends HttpServlet {
    public static HashMap<String, Core> coreMap = new HashMap<String, Core>();
    /** The logger for the Core. */
    private Logger logger = LogManager.getLogger("programd");

    @Override
    public void doPost(final HttpServletRequest req,
            final HttpServletResponse res) throws IOException {
        logger.info("Hello");
        XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        Message message = xmpp.parseMessage(req);
        JID fromJid = message.getFromJid();
        xmpp.sendPresence(fromJid, PresenceType.AVAILABLE, PresenceShow.CHAT,
                "New Personality Added on Oct 4 2011");
        String body = message.getBody();
        String base = "WEB-INF/BotBrain/";
        Language original = Language.ENGLISH;
        String prop = base + "conf/core.xml";

        body = body.replace("$", "");
        try {
            GoogleAPI.setKey("AIzaSyB8fvRHbqhtDnVXBc1pirSkk_hxE4BGof0");
            GoogleAPI.setHttpReferrer("http://chimbs86.appspot.com");

            DetectResult d = Detect.execute(body);
            original = d.getLanguage();

            String obj = Translate.DEFAULT.execute(body, original,
                    Language.ENGLISH);
            body = obj;
        } catch (Exception detectEx) {

        }

        Core core = null;
        core = coreMap.get(fromJid.getId());
        Throwable m = null;
        if (core == null) {
            try {
                core = new Core(URLTools.createValidURL(base),
                        URLTools.createValidURL(prop));
                coreMap.put(fromJid.getId(), core);
            } catch (FileNotFoundException e) {

            }
        } else {
            Key reUsekey = KeyFactory.createKey("coreReuse",
                    System.currentTimeMillis());
            Entity coreReuse = new Entity(reUsekey);
            coreReuse.setProperty("loginid", fromJid.getId());
            coreReuse.setProperty("Time", DateFormat.getDateTimeInstance()
                    .format(new Date(System.currentTimeMillis())));

            DatastoreService serviceCore = DatastoreServiceFactory
                    .getDatastoreService();
            serviceCore.put(coreReuse);
        }
        // trials
        Console console = new Console();
        // console.attachTo(core);
        String msgBody = "";
        try {
            msgBody = core.getResponse(body, "chimbs", "SampleBot");
            if (original != Language.ENGLISH) {
                try {
                    GoogleAPI.setKey("AIzaSyB8fvRHbqhtDnVXBc1pirSkk_hxE4BGof0");
                    GoogleAPI.setHttpReferrer("http://chimbs86.appspot.com");

                    String obj = Translate.DEFAULT.execute(msgBody,
                            Language.ENGLISH, original);
                    msgBody = "I think you spoke in :" + original.name() + " "
                            + obj;

                } catch (Exception detectEx) {
                    logger.error(detectEx);
                }
            }
        } catch (Throwable e) {
            Chimbsbot b = new Chimbsbot();
            b.sInput = body;
            b.preprocess_input();

            msgBody = b.respond();
            msgBody += "(My answer is a lil lame because of an internal problem with one of my brains)";
            m = e;
        }

        Message msg = new MessageBuilder().withRecipientJids(fromJid)
                .withBody(msgBody.trim()).build();
        XMPPService xmpp2 = XMPPServiceFactory.getXMPPService();
        // log.warning("The user " + fromJid.toString() + " says :" + body);
        xmpp2.sendInvitation(fromJid);
        SendResponse status = xmpp2.sendMessage(msg);
        boolean messageSent = false;
        // log.warning("I replied: " + msgBody);
        Key key = KeyFactory.createKey("LogTime1", System.currentTimeMillis());

        Entity e = new Entity(key);
        e.setProperty("loginid", fromJid.getId());
        e.setProperty("question", body);
        e.setProperty("answer", msgBody);

        e.setProperty(
                "Time",
                DateFormat.getDateTimeInstance().format(
                        new Date(System.currentTimeMillis())));

        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        service.put(e);

        messageSent = (status.getStatusMap().get(fromJid) == SendResponse.Status.SUCCESS);
        if (m != null) {
            throw new RuntimeException(m);
        }
        // ...
    }
}