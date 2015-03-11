package me.nithanim.cultures.formats.lib.internal;

import me.nithanim.longbuffer.Buffer;

public class ArchiveFileDirs extends ArchiveFileBase {
    protected DirMeta[] dirMetas;
    
    public ArchiveFileDirs(Buffer buffer) {
        super(buffer);
    }

    public DirMeta[] getDirMetas() {
        return dirMetas;
    }
    
    public int getDirCount() {
        return dirMetas.length;
    }

    @Override
    public void readMetas() {
        buffer.readerIndex(0);
        this.unknown = buffer.readInt();
        int dirCount = buffer.readInt();
        int fileCount = buffer.readInt();
        
        dirMetas = new DirMeta[dirCount];
        readDirMetas(buffer, dirMetas);
        
        fileMetas = new FileMetaImpl[fileCount];
        readFileMetas(buffer, fileMetas);
    }
    
    protected void readDirMetas(Buffer buffer, DirMeta[] dirMetas) {
        for(int i = 0; i < dirMetas.length; i++) {
            DirMeta dirMeta = new DirMeta(buffer);
            dirMetas[i] = dirMeta;
        }
    }
}
