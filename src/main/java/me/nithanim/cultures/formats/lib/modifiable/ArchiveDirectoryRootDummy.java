package me.nithanim.cultures.formats.lib.modifiable;

import me.nithanim.cultures.formats.lib.ArchiveDirectory;
import me.nithanim.cultures.formats.lib.internal.DirMeta;

public class ArchiveDirectoryRootDummy implements ArchiveDirectory {

    @Override
    public String getName() {
        return "\\";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public DirMeta getDirMeta() {
        return null;
    }
}
