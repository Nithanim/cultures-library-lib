package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;

public class DirMeta {
    private String name;
    private long level;

    public void read(ByteBuf buffer) {
        byte[] bytes = new byte[buffer.readInt()];
        buffer.readBytes(bytes);
        this.name = new String(bytes, Charset.forName("US-ASCII"));
        this.level = buffer.readUnsignedInt();
    }

    public String getName() {
        return name;
    }
    
    /**
     * Number of backslashes
     * @return 
     */
    public long getLevel() {
        return level;
    }
    
    @Override
    public String toString() {
        return level + ":" + name;
    }
}
