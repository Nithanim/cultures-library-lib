package me.nithanim.cultures.formats.lib.internal;

import java.nio.charset.Charset;
import me.nithanim.longbuffer.Buffer;

public class FileMetaImpl implements FileMeta {
    private final String name;
    private final long pos;
    private final long length;
    
    public FileMetaImpl(Buffer buffer) {
        byte[] bytes = new byte[buffer.readInt()];
        buffer.readBytes(bytes);
        this.name = new String(bytes, Charset.forName("US-ASCII"));
        this.pos = buffer.readUnsignedInt();
        this.length = buffer.readUnsignedInt();
    }

    public FileMetaImpl(String name, int pos, int length) {
        this.name = name;
        this.pos = pos;
        this.length = length;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public long getPos() {
        return pos;
    }
    
    @Override
    public long getLength() {
        return length;
    }
    
    @Override
    public String toString() {
        return pos + ":" + length + ":" + name;
    }
}
