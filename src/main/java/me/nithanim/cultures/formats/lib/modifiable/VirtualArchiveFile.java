package me.nithanim.cultures.formats.lib.modifiable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.cultures.formats.lib.internal.FileMetaImpl;
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
    public ByteBuf getBuffer() {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(src, "r");
            FileChannel ch = raf.getChannel();
            MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
            return Unpooled.wrappedBuffer(mbb).order(ByteOrder.LITTLE_ENDIAN);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public FileMetaImpl getFileMeta() {
        return null;
    }
}
