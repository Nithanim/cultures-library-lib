package me.nithanim.cultures.formats.lib.internal;

import me.nithanim.longbuffer.Buffer;

public abstract class ArchiveFileBase implements ArchiveFile {
    protected final Buffer buffer;
    
    protected int unknown;
    protected FileMetaImpl[] fileMetas;

    public ArchiveFileBase(Buffer buffer) {
        this.buffer = buffer;
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
    
    protected void readFileMetas(Buffer buf, FileMetaImpl[] fileMetas) {
        for(int i = 0; i < fileMetas.length; i++) {
            FileMetaImpl fileMeta = new FileMetaImpl(buf);
            fileMetas[i] = fileMeta;
        }
    }
}
