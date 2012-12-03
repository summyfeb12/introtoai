package org.aitools.programd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GoogleFileOutputStream extends FileOutputStream {
    String filename = "";

    public GoogleFileOutputStream(final File file) throws FileNotFoundException {
        super(file);
        this.filename = file.getName();
        // TODO Auto-generated constructor stub
    }

    String tempstring = "";

    @Override
    public void write(final byte[] b) throws IOException {
        // TODO Auto-generated method stub
        for (byte block : b) {
            tempstring += b;
        }

    }

    @Override
    public void write(final int b) throws IOException {
        // TODO Auto-generated method stub
        tempstring += b;
    }

    @Override
    public void flush() throws IOException {
        Key key = KeyFactory.createKey("FileContent",
                System.currentTimeMillis());
        Entity e = new Entity(key);
        e.setProperty("Filename", this.filename);
        e.setProperty("Value", tempstring);
        tempstring = "";
        DatastoreService service = DatastoreServiceFactory
                .getDatastoreService();
        service.put(e);
    }
}
