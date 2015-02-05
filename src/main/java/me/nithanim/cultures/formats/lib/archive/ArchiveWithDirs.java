package me.nithanim.cultures.formats.lib.archive;

import java.util.List;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;

public interface ArchiveWithDirs extends Archive {
    int getDirCount();
    List<ArchiveDirectory> getDirectories();
}
