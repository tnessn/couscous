package com.github.tnessn.couscous.lang.http.wrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpServletResponseCopier.
 *
 * @author huangjinfeng
 */
public class HttpServletResponseCopier extends HttpServletResponseWrapper {

    /** The output stream. */
    private ServletOutputStream outputStream;
    
    /** The writer. */
    private PrintWriter writer;
    
    /** The copier. */
    private ServletOutputStreamCopier copier;

    /**
     * Instantiates a new http servlet response copier.
     *
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public HttpServletResponseCopier(HttpServletResponse response) throws IOException {
        super(response);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getOutputStream()
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            copier = new ServletOutputStreamCopier(outputStream);
        }

        return copier;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#flushBuffer()
     */
    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            copier.flush();
        }
    }

    /**
     * Gets the copy.
     *
     * @return the copy
     */
    public byte[] getCopy() {
        if (copier != null) {
            return copier.getCopy();
        } else {
            return new byte[0];
        }
    }

}