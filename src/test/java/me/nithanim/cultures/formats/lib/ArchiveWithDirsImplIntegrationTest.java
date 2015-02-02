package me.nithanim.cultures.formats.lib;

import me.nithanim.cultures.formats.lib.archive.ArchiveWithDirsImpl;
import me.nithanim.cultures.formats.lib.archive.ReadableArchive;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ArchiveWithDirsImplIntegrationTest {
    File workingDir;
    
    @Before
    public void setUp() {
        workingDir = new File(getBaseFolder(), "reading/");
    }
    
    @After
    public void tearDown() {
        workingDir = null;
    }
    
    @Test
    public void testExtracting() throws IOException {
        ReadableArchive a = new ArchiveWithDirsImpl();
        a.bindTo(new File(workingDir, "test.lib"));
        for (ReadableArchiveFile file : a.getFileList()) {
            file.extractToWithOriginalDirectory(workingDir);
        }
        
        byte[] expectedFile1 = {0x74, 0x68, 0x69, 0x73, 0x20, 0x69, 0x73, 0x20, 0x73, 0x6F, 0x6D, 0x65, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74};
        byte[] expectedFile2 = {0x74, 0x68, 0x69, 0x73, 0x20, 0x69, 0x73, 0x20, 0x61, 0x6E, 0x6F, 0x74, 0x68, 0x65, 0x72, 0x20, 0x66, 0x69, 0x6C, 0x65, 0x20, 0x74, 0x68, 0x61, 0x74, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x61, 0x69, 0x6E, 0x73, 0x20, 0x73, 0x6F, 0x6D, 0x65, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74};
        assertEquals(fileAsBuffer(new File(workingDir, "data/test1.txt")), Unpooled.wrappedBuffer(expectedFile1));
        assertEquals(fileAsBuffer(new File(workingDir, "data/test2.txt")), Unpooled.wrappedBuffer(expectedFile2));
    }
    
    private ByteBuf fileAsBuffer(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel ch = raf.getChannel();
        try {
            MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
            return Unpooled.copiedBuffer(mbb);
        } finally {
            ch.close();
        }
    }
    
    private File getResourceAsFile(String file) {
        URL url = getClass().getResource(file);
        try {
          return new File(url.toURI());
        } catch(URISyntaxException e) {
          return new File(url.getPath());
        }
    }
    
    private File getBaseFolder() {
        return getResourceAsFile("/");
    }
}
