package org.aitools.programd.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GoogleFileInputStream extends FileInputStream {

    public GoogleFileInputStream(final GoogleFile file)
            throws FileNotFoundException {
        super(file);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int read() throws IOException {
        // TODO Auto-generated method stub
        return super.read();
    }

}
