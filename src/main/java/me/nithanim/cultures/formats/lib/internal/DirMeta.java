package me.nithanim.cultures.formats.lib.internal;

import java.nio.charset.Charset;
import me.nithanim.cultures.formats.lib.util.Buffer;

public class DirMeta {
    private String name;
    private long level;

    public DirMeta(Buffer buffer) {
        byte[] bytes = new byte[buffer.readInt()];
        buffer.readBytes(bytes);
        this.name = new String(bytes, Charset.forName("US-ASCII"));
        this.level = buffer.readUnsignedInt();
    }

    public DirMeta(String dir) {
        if(dir == null || dir.length()==0) {
            throw new IllegalArgumentException("Got invalid dir!");
        }
        if(dir.length() == 1 && dir.charAt(0) != '\\') {
            throw new IllegalArgumentException("RootDir must be backslash only!");
        }
        if(dir.charAt(dir.length()-1) != '\\') {
            throw new IllegalArgumentException("RootDir must end with a backslash!");
        }
        
        name = dir;
        level = (dir.length() == 1) ? (0) : (countBackslashes(dir));
    }
    
    private int countBackslashes(String s) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '\\') {
                count++;
            }
        }
        return count;
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
