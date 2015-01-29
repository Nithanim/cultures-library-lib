package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;

public abstract class ArchiveFileBase implements ArchiveFile {
    protected final ByteBuf buf;
    
    protected int unknown;
    protected FileMeta[] fileMetas;

    public ArchiveFileBase(ByteBuf buf) {
        this.buf = buf;
    }
    
    @Override
    public int getUnknown() {
        return unknown;
    }

    @Override
    public int getFileCount() {
        return fileMetas.length;
    }

    @Override
    public FileMeta[] getFileMetas() {
        return fileMetas;
    }
    
    protected void readFileMetas(ByteBuf buf, FileMeta[] fileMetas) {
        for(int i = 0; i < fileMetas.length; i++) {
            FileMeta fileMeta = new FileMeta(buf);
            fileMetas[i] = fileMeta;
        }
    }
}
