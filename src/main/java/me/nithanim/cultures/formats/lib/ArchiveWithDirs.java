package me.nithanim.cultures.formats.lib;

import java.util.List;

public interface ArchiveWithDirs extends Archive {
    int getDirCount();
    List<ArchiveDirectory> getDirectories();
}
