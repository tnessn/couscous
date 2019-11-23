package com.github.tnessn.couscous.lang.http.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class ServletOutputStreamCopier.
 *
 * @author huangjinfeng
 */
public class ServletOutputStreamCopier extends ServletOutputStream {

    /** The output stream. */
    private OutputStream outputStream;
    
    /** The copy. */
    private ByteArrayOutputStream copy;

    /**
     * Instantiates a new servlet output stream copier.
     *
     * @param outputStream the output stream
     */
    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    /* (non-Javadoc)
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    /**
     * Gets the copy.
     *
     * @return the copy
     */
    public byte[] getCopy() {
        return copy.toByteArray();
    }

}