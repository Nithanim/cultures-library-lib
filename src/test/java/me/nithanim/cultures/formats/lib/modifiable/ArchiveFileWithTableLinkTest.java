package me.nithanim.cultures.formats.lib.modifiable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.cultures.formats.lib.ReadableArchiveFileImpl;
import me.nithanim.cultures.formats.lib.internal.FileMeta;
import me.nithanim.cultures.formats.lib.internal.FileMetaImpl;
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
        ByteBuf expectedBuffer = Unpooled.buffer(l, l).order(ByteOrder.LITTLE_ENDIAN);
        expectedBuffer.writeInt(name.length()).writeBytes(nameAsBytes);
        expectedBuffer.writeInt(-1).writeInt(content.length);
        ByteBuf destBuffer = Unpooled.buffer(l, l).order(ByteOrder.LITTLE_ENDIAN);
        
        awt.writeTable(destBuffer);
        assertArrayEquals(expectedBuffer.array(), destBuffer.array());
    }

    @Test
    public void testWriteContents() {
        int l = 4 + name.length() + 4 + 4;
        ByteBuf tableEntry = Unpooled.buffer(l, l).order(ByteOrder.LITTLE_ENDIAN);
        awt.writeTable(tableEntry);
        byte[] targetContent = new byte[content.length];
        
        //content written correctly
        awt.writeContents(Unpooled.wrappedBuffer(targetContent).writerIndex(0), contentPos);
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
        return new ReadableArchiveFileImpl(fileMeta, Unpooled.wrappedBuffer(content));
    }

    /**
     * Test of getArchiveFile method, of class ArchiveFileWithTableLink.
     */
    @Test
    public void testGetArchiveFile() {
        System.out.println("getArchiveFile");
        ArchiveFileWithTableLink instance = null;
        ArchiveFile expResult = null;
        ArchiveFile result = instance.getArchiveFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
