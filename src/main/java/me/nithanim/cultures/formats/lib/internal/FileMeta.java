package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;

public class FileMeta {
    private final String name;
    private final long pos;
    private final long length;
    
    public FileMeta(ByteBuf buffer) {
        byte[] bytes = new byte[buffer.readInt()];
        buffer.readBytes(bytes);
        this.name = new String(bytes, Charset.forName("US-ASCII"));
        this.pos = buffer.readUnsignedInt();
        this.length = buffer.readUnsignedInt();
    }
    
    public String getName() {
        return name;
    }
    
    public long getPos() {
        return pos;
    }
    
    public long getLength() {
        return length;
    }
    
    @Override
    public String toString() {
        return pos + ":" + length + ":" + name;
    }
}
