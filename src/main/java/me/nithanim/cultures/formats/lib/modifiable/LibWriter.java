package me.nithanim.cultures.formats.lib.modifiable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;
import me.nithanim.cultures.formats.lib.ArchiveFile;

public class LibWriter {
    private static final Charset CHARSET = Charset.forName("US-ASCII");
    
    private final Set<ArchiveDirectory> directories;
    private final List<ArchiveFileWithTableLink> files;
    
    private FileChannel fileChannel;
    private int dirTablePos = 3 * 4;
    private int fileTablePos = -1;
    private int contentPos = -1;
    
    public LibWriter(Set<ArchiveDirectory> direcoties, List<ArchiveFile> files) {
        this.directories = direcoties;
        this.files = new LinkedList<ArchiveFileWithTableLink>();
        for(ArchiveFile af : files) {
            this.files.add(new ArchiveFileWithTableLink(af));
        }
    }
    
    void writeToFile(File dest) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dest, "rw");
        fileChannel = raf.getChannel();
        
        try {
            writeBeginning();
            writeDirectoryTable();
            writeFileTable();
            writeFileContents();
            fileChannel.force(false);
        } finally {
            fileChannel.close();
        }
    }
    
    private void writeBeginning() throws IOException {
        ByteBuf buffer = getSizedBuffer(0, 3*4);
        buffer.writeInt(1);
        buffer.writeInt(directories.size());
        buffer.writeInt(files.size());
    }
    
    private void writeDirectoryTable() throws IOException {
        int lastAddr = dirTablePos;
        for(ArchiveDirectory ad : directories) {
            int size = 4 + ad.getName().length() + 4;
            ByteBuf buffer = getSizedBuffer(lastAddr, size);
            buffer.writeInt(ad.getName().length());
            buffer.writeBytes(ad.getName().getBytes(CHARSET));
            buffer.writeInt(ad.getLevel());
            lastAddr += size;
        }
        fileTablePos = lastAddr;
    }
    
    private void writeFileTable() throws IOException {
        int lastAddr = fileTablePos;
        for(ArchiveFileWithTableLink file : files) {
            int size = file.getTableSize();
            file.writeTable(getSizedBuffer(lastAddr, size));
            lastAddr += size;
        }
        contentPos = lastAddr;
    }
    
    private void writeFileContents() throws IOException {
        int lastAddr = contentPos;
        for(ArchiveFileWithTableLink file : files) {
            int size = file.getContentSize();
            file.writeContents(getSizedBuffer(lastAddr, size), lastAddr);
            lastAddr += size;
        }
    }
    
    private ByteBuf getSizedBuffer(int pos, int length) throws IOException {
        MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, length);
        ByteBuf buffer = Unpooled.wrappedBuffer(mbb).order(ByteOrder.LITTLE_ENDIAN);
        buffer.writerIndex(0); //unsure why needed
        return buffer;
    }
    
}
