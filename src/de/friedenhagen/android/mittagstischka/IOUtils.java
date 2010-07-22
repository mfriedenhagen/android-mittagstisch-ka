/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author mirko
 *
 */
public final class IOUtils {
    
    /**
     * 
     */
    private IOUtils() {
        // TODO Auto-generated constructor stub
    }
    
    public static byte[] toByteArray(final InputStream inputStream) throws IOException {
        final byte[] sBuffer = new byte[1024];
        final ByteArrayOutputStream content = new ByteArrayOutputStream();
        // Read response into a buffered stream
        int readBytes = 0;
        while ((readBytes = inputStream.read(sBuffer)) != -1) {
            content.write(sBuffer, 0, readBytes);
        }
        // Return result from buffered stream
        return content.toByteArray();
    }
    
    public static void toOutputStream(final OutputStream outputStream, byte[] content) throws IOException {
        outputStream.write(content);
    }
    
    public static byte[] toUtf8Bytes(final String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Message:", e);
        }
    }
    
    public static String toUtf8String(final byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Message:", e);
        }
    }

}
