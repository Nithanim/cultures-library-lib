package me.nithanim.cultures.formats.lib.modifiable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.longbuffer.Buffer;
import me.nithanim.longbuffer.RandomAccessFileBuffer;

public class LibWriter {
    private static final Charset CHARSET = Charset.forName("US-ASCII");
    
    private final Set<ArchiveDirectory> directories;
    private final List<ArchiveFileWithTableLink> files;
    
    private Buffer destFileBuffer;
    private int dirTablePos = 3 * 4;
    private int fileTablePos = -1;
    private int contentPos = -1;
    
    public LibWriter(Set<ArchiveDirectory> directory, List<ArchiveFile> files) {
        this.directories = directory;
        this.files = new LinkedList<ArchiveFileWithTableLink>();
        for(ArchiveFile af : files) {
            this.files.add(new ArchiveFileWithTableLink(af));
        }
    }
    
    void writeToFile(File dest) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dest, "rw");
        destFileBuffer = new RandomAccessFileBuffer(raf).order(ByteOrder.LITTLE_ENDIAN);
        
        try {
            writeBeginning();
            writeDirectoryTable();
            writeFileTable();
            writeFileContents();
            raf.getChannel().force(false);
        } finally {
            destFileBuffer.dispose();
        }
    }
    
    private void writeBeginning() throws IOException {
        Buffer buffer = getSizedBuffer(0, 3*4);
        buffer.writeInt(1);
        buffer.writeInt(directories.size());
        buffer.writeInt(files.size());
    }
    
    private void writeDirectoryTable() throws IOException {
        int lastAddr = dirTablePos;
        for(ArchiveDirectory ad : directories) {
            int size = 4 + ad.getName().length() + 4;
            Buffer buffer = getSizedBuffer(lastAddr, size);
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
    
    private Buffer getSizedBuffer(long index, long length) throws IOException {
        return destFileBuffer.slice(index, length);
    }
}
