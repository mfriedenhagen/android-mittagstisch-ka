/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Some basic helper routines mostly stolen from org.apache.commons.io.IOUtils.
 * 
 * @author mirko
 */
public final class IOUtils {

    /**
     * The default buffer size to use.
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * 
     */
    private IOUtils() {
        // final Util class
    }

    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a <code>BufferedInputStream</code>.
     * 
     * @param input
     *            the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException
     *             if the input is null
     * @throws IOException
     *             if an I/O error occurs
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    /**
     * Writes bytes from a <code>byte[]</code> to an <code>OutputStream</code>.
     * 
     * @param data
     *            the byte array to write, do not modify during output, null ignored
     * @param output
     *            the <code>OutputStream</code> to write to
     * @throws IllegalArgumentException
     *             if data or output is null
     * @throws IOException
     *             if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void write(byte[] data, OutputStream output) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("data must not be null");
        } else if (output == null) {
            throw new IllegalArgumentException("output must not be null");
        } else {
            output.write(data);
        }
    }

    /**
     * Copy bytes from an <code>InputStream</code> to an <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a <code>BufferedInputStream</code>.
     * <p>
     * Large streams (over 2GB) will return a bytes copied value of <code>-1</code> after the copy has completed since
     * the correct number of bytes cannot be returned as an int. For large streams use the
     * <code>copyLarge(InputStream, OutputStream)</code> method.
     * 
     * @param input
     *            the <code>InputStream</code> to read from
     * @param output
     *            the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException
     *             if the input or output is null
     * @throws IOException
     *             if an I/O error occurs
     * @throws ArithmeticException
     *             if the byte count is too large
     * @since Commons IO 1.1
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a <code>BufferedInputStream</code>.
     * 
     * @param input
     *            the <code>InputStream</code> to read from
     * @param output
     *            the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException
     *             if the input or output is null
     * @throws IOException
     *             if an I/O error occurs
     * @since Commons IO 1.3
     */
    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Converts the given String to a byte array using UTF-8 decoding. This just rethrows the
     * {@link UnsupportedEncodingException} as a {@link RuntimeException}, which really should never happen, as UTF-8 is
     * always included in the JDK.
     * 
     * @param string
     *            input string
     * @return utf-8 bytes of the String.
     */
    public static byte[] toUtf8Bytes(final String string) {
        try {
            return string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not available?", e);
        }
    }

    /**
     * Converts the given byte array to a String using UTF-8 encoding. This just rethrows the
     * {@link UnsupportedEncodingException} as a {@link RuntimeException}, which really should never happen, as UTF-8 is
     * always included in the JDK.
     * 
     * @param bytes
     *            to convert
     * @return the bytes as UTF-8 string.
     */
    public static String toUtf8String(final byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not available?", e);
        }
    }

}
