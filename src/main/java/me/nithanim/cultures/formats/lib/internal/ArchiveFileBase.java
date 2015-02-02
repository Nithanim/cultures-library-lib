package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;

public abstract class ArchiveFileBase implements ArchiveFile {
    protected final ByteBuf buf;
    
    protected int unknown;
    protected FileMetaImpl[] fileMetas;

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
    public FileMetaImpl[] getFileMetas() {
        return fileMetas;
    }
    
    protected void readFileMetas(ByteBuf buf, FileMetaImpl[] fileMetas) {
        for(int i = 0; i < fileMetas.length; i++) {
            FileMetaImpl fileMeta = new FileMetaImpl(buf);
            fileMetas[i] = fileMeta;
        }
    }
}
