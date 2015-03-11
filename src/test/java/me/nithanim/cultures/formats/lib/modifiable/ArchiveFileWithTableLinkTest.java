package me.nithanim.cultures.formats.lib.modifiable;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.cultures.formats.lib.ReadableArchiveFileImpl;
import me.nithanim.cultures.formats.lib.internal.FileMeta;
import me.nithanim.cultures.formats.lib.internal.FileMetaImpl;
import me.nithanim.longbuffer.Buffer;
import me.nithanim.longbuffer.ByteArrayBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArchiveFileWithTableLinkTest {
    String name;
    private byte[] nameAsBytes;
    int contentPos;
    byte[] content;
    ArchiveFile archive;
    ArchiveFileWithTableLink awt;
    
    public ArchiveFileWithTableLinkTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        name = "data\\test.txt";
        nameAsBytes = name.getBytes(Charset.forName("US-ASCII"));
        contentPos = 0;
        content = new byte[25];
        Arrays.fill(content, (byte)0xFF);
        archive = getDummyArchiveFile();
        awt = new ArchiveFileWithTableLink(archive);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWriteTable() {
        int l = 4 + name.length() + 4 + 4;
        Buffer expectedBuffer = new ByteArrayBuffer(l).order(ByteOrder.LITTLE_ENDIAN);
        expectedBuffer.writeInt(name.length()).writeBytes(nameAsBytes);
        expectedBuffer.writeInt(-1).writeInt(content.length);
        byte[] byteArray = new byte[l];
        Buffer destBuffer = new ByteArrayBuffer(byteArray).order(ByteOrder.LITTLE_ENDIAN);
        
        awt.writeTable(destBuffer);
        assertArrayEquals(expectedBuffer.array(), byteArray);
    }

    @Test
    public void testWriteContents() {
        int l = 4 + name.length() + 4 + 4;
        byte[] byteArray = new byte[l];
        Buffer tableEntry = new ByteArrayBuffer(byteArray).order(ByteOrder.LITTLE_ENDIAN);
        awt.writeTable(tableEntry);
        byte[] targetContent = new byte[content.length];
        
        //content written correctly
        awt.writeContents(new ByteArrayBuffer(targetContent), contentPos);
        assertArrayEquals(content, targetContent);
        
        //table pos update correctly
        assertEquals(contentPos, tableEntry.getInt(tableEntry.capacity() - 4 - 4));
    }

    @Test
    public void testGetTableSize() {
        int expected = 4 + name.length() + 4 + 4;
        assertEquals(expected, awt.getTableSize());
    }

    @Test
    public void testGetContentSize() {
        int expected = content.length;
        assertEquals(expected, awt.getContentSize());
    }

    private ArchiveFile getDummyArchiveFile() {
        FileMeta fileMeta = new FileMetaImpl(name, contentPos, content.length);
        return new ReadableArchiveFileImpl(fileMeta, new ByteArrayBuffer(content));
    }
}
