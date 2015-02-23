package me.nithanim.cultures.formats.lib.modifiable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.cultures.formats.lib.internal.FileMetaImpl;
import me.nithanim.cultures.formats.lib.util.Buffer;
import me.nithanim.cultures.formats.lib.util.RandomAccessFileBuffer;
import me.nithanim.cultures.formats.lib.util.UncheckedIOException;

public class VirtualArchiveFile implements ArchiveFile {
    private final File virtualPath;
    private final File src;
    
    public VirtualArchiveFile(File virtualPath, File src) {
        this.virtualPath = virtualPath;
        this.src = src;
    }
    
    @Override
    public String getFileName() {
        return virtualPath.getName();
    }

    @Override
    public String getFullPath() {
        return virtualPath.toString();
    }

    @Override
    public String getPath() {
        return virtualPath.getParent() + "\\";
    }

    @Override
    public File getFile() {
        return virtualPath;
    }
    
    @Override
    public int getSize() {
        return (int)src.length();
    }

    @Override
    public Buffer getBuffer() {
        try {
            RandomAccessFile raf = new RandomAccessFile(src, "r");
            Buffer buffer = new RandomAccessFileBuffer(raf);
            return buffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public FileMetaImpl getFileMeta() {
        return null;
    }
}
