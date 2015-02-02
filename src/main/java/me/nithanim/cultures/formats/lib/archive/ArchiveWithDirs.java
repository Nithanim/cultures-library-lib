package me.nithanim.cultures.formats.lib.archive;

import me.nithanim.cultures.formats.lib.archive.Archive;
import java.util.List;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;

public interface ArchiveWithDirs extends Archive {
    int getDirCount();
    List<ArchiveDirectory> getDirectories();
}
