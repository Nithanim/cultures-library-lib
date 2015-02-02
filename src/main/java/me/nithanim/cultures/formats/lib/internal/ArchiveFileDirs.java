package me.nithanim.cultures.formats.lib.internal;

import io.netty.buffer.ByteBuf;

public class ArchiveFileDirs extends ArchiveFileBase {
    protected DirMeta[] dirMetas;
    
    public ArchiveFileDirs(ByteBuf buf) {
        super(buf);
    }

    public DirMeta[] getDirMetas() {
        return dirMetas;
    }
    
    public int getDirCount() {
        return dirMetas.length;
    }

    @Override
    public void readMetas() {
        buf.readerIndex(0);
        this.unknown = buf.readInt();
        int dirCount = buf.readInt();
        int fileCount = buf.readInt();
        
        dirMetas = new DirMeta[dirCount];
        readDirMetas(buf, dirMetas);
        
        fileMetas = new FileMetaImpl[fileCount];
        readFileMetas(buf, fileMetas);
    }
    
    protected void readDirMetas(ByteBuf buf, DirMeta[] dirMetas) {
        for(int i = 0; i < dirMetas.length; i++) {
            DirMeta dirMeta = new DirMeta(buf);
            dirMetas[i] = dirMeta;
        }
    }
}
