package me.nithanim.cultures.formats.lib.internal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import me.nithanim.longbuffer.Buffer;
import me.nithanim.longbuffer.RandomAccessFileBuffer;

public class FileExtractor {
    public static void extract(Buffer src, File dest) throws IOException {
        extract(src, 0, src.capacity(), dest);
    }
    
    public static void extract(Buffer src, long offset, long length, File destFile) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(destFile, "rw");
        try {
            Buffer dest = new RandomAccessFileBuffer(raf).order(ByteOrder.LITTLE_ENDIAN);
            extract(src, offset, length, dest);
        } finally {
            raf.close();
        }
    }
    
    public static void extract(Buffer src, long offset, long length, Buffer dest) {
        dest.writerIndex(0);
        dest.writeBytes(src, offset, length);
    }
    
    private FileExtractor() {
    }
}
