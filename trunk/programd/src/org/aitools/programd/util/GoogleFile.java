package org.aitools.programd.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GoogleFile extends File {
    String name;
    String path = "";
    /** The logger. */
    private static Logger logger = Logger.getLogger("programd");

    public GoogleFile(final String pathname) {
        super(pathname);
        this.name = pathname;
        Key key = KeyFactory.createKey("FileName", this.name);
        Entity e = new Entity(key);
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        service.put(e);
    }

    public GoogleFile(final String path, final String name) {
        super(path, name);
        this.path = path;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public File getCanonicalFile() throws IOException {
        return this;
    }

    @Override
    public boolean createNewFile() throws IOException {
        Key key = KeyFactory.createKey("FileName", this.name);
        Entity e = new Entity(key);
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        try {
            service.put(e);
            logger.info("Done created");
            logger.info("Done created");
            logger.info("Done created");
            logger.info("Done created");
            logger.info("Done created");
            logger.info("Done created");
            logger.info("Done created");

        } catch (Exception ex) {

            return false;
        }
        return true;
    }

    @Override
    public boolean isDirectory() {
        // TODO Auto-generated method stub

        return false;
    }

}
