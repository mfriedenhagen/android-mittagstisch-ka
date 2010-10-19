/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author mirko
 *
 */
@RunWith(PowerMockRunner.class)
public class IOUtilsTest {

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.IOUtils#write(byte[], java.io.OutputStream)}.
     * @throws IOException 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testWriteDataNull() throws IOException {
        IOUtils.write(null, new ByteArrayOutputStream());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.IOUtils#write(byte[], java.io.OutputStream)}.
     * @throws IOException 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testWriteOutputNull() throws IOException {
        IOUtils.write(new byte[1], null);
    }
    
    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.IOUtils#copyLarge(java.io.InputStream, java.io.OutputStream)}.
     */
    @Test
    @PrepareForTest
    @Ignore
    public void testCopy() {
        PowerMockito.mockStatic(IOUtils.class);
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.IOUtils#toUtf8Bytes(java.lang.String)}.
     * @throws UnsupportedEncodingException 
     */
    @Test(expected=UnsupportedEncodingException.class)
    @PrepareOnlyThisForTest(String.class)
    @Ignore
    public void testToUtf8Bytes() throws UnsupportedEncodingException {
        final String sut = "Hallo";
        final String spy = PowerMockito.spy(sut);
        PowerMockito.doThrow(new UnsupportedEncodingException()).when(spy).getBytes("utf-8");
        System.out.println("IOUtilsTest.testToUtf8Bytes()" + IOUtils.toUtf8Bytes(spy));
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.IOUtils#toUtf8String(byte[])}.
     */
    @Test
    @Ignore
    public void testToUtf8String() {
        fail("Not yet implemented"); // TODO
    }

}
