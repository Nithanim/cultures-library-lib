package me.nithanim.cultures.formats.lib.modifiable;

import me.nithanim.cultures.formats.lib.internal.FileMeta;

@Deprecated
public class WriteableFileMeta implements FileMeta {
    private int length;
    private String name;
    private int pos;
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }
    
    @Override
    public long getLength() {
        return length;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getPos() {
        return pos;
    }
}
