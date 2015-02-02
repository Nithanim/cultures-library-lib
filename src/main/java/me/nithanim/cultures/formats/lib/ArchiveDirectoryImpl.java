package me.nithanim.cultures.formats.lib;

import me.nithanim.cultures.formats.lib.internal.DirMeta;

public class ArchiveDirectoryImpl implements ArchiveDirectory {
    private final DirMeta dirMeta;

    public ArchiveDirectoryImpl(DirMeta dirMeta) {
        this.dirMeta = dirMeta;
    }
    
    @Override
    public String getName() {
        return dirMeta.getName();
    }
    
    @Override
    public int getLevel() {
        return (int)dirMeta.getLevel();
    }

    @Override
    public DirMeta getDirMeta() {
        return dirMeta;
    }
}
