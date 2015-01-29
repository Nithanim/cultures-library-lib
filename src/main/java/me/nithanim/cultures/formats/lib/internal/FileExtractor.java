package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileExtractor {
    public static void extract(ByteBuf src, File dest) throws IOException {
        extract(src, 0, src.capacity(), dest);
    }
    
    public static void extract(ByteBuf src, int offset, int length, File dest) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dest, "rw");
        FileChannel ch = raf.getChannel();
        try {
            MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_WRITE, 0, length);
            ByteBuf destBuf = Unpooled.wrappedBuffer(mbb).order(ByteOrder.LITTLE_ENDIAN);
            destBuf.writerIndex(0);
            destBuf.writeBytes(src, offset, length);
        } finally {
            ch.close();
        }
    }
    
    public static void extract(ByteBuf src, int offset, int length, ByteBuf dest) {
        dest.writeBytes(src, offset, length);
    }
    
    private FileExtractor() {
    }
}
