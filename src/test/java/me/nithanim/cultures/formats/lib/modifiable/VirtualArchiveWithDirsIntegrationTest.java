package me.nithanim.cultures.formats.lib.modifiable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import me.nithanim.cultures.formats.lib.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class VirtualArchiveWithDirsIntegrationTest {
    private File workingDir;
    
    public VirtualArchiveWithDirsIntegrationTest() {
    }
    
    @Before
    public void setUp() {
        workingDir = new File(getBaseFolder(), "writing/");
    }
    
    @After
    public void tearDown() {
        workingDir = null;
    }

    @Test
    public void testSaveFile() throws Exception {
        File dest = new File(workingDir, "test.lib");
        
        VirtualArchive a = new VirtualArchiveWithDirs();
        a.appendFile(new File("data\\test1.txt"), new File(workingDir, "testfile1.txt"));
        a.appendFile(new File("data\\test2.txt"), new File(workingDir, "testfile2.txt"));
        a.saveFile(dest);
        
        byte[] expected = {0x01, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x5C, 0x00, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x64, 0x61, 0x74, 0x61, 0x5C, 0x01, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00, 0x64, 0x61, 0x74, 0x61, 0x5C, 0x74, 0x65, 0x73, 0x74, 0x31, 0x2E, 0x74, 0x78, 0x74, 0x56, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00, 0x64, 0x61, 0x74, 0x61, 0x5C, 0x74, 0x65, 0x73, 0x74, 0x32, 0x2E, 0x74, 0x78, 0x74, 0x6A, 0x00, 0x00, 0x00, 0x2F, 0x00, 0x00, 0x00, 0x74, 0x68, 0x69, 0x73, 0x20, 0x69, 0x73, 0x20, 0x73, 0x6F, 0x6D, 0x65, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74, 0x74, 0x68, 0x69, 0x73, 0x20, 0x69, 0x73, 0x20, 0x61, 0x6E, 0x6F, 0x74, 0x68, 0x65, 0x72, 0x20, 0x66, 0x69, 0x6C, 0x65, 0x20, 0x74, 0x68, 0x61, 0x74, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x61, 0x69, 0x6E, 0x73, 0x20, 0x73, 0x6F, 0x6D, 0x65, 0x20, 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74};
        assertEquals(Unpooled.wrappedBuffer(expected), fileAsBuffer(dest));
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
