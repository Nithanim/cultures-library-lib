package me.nithanim.cultures.formats.lib.modifiable;

import java.io.File;
import java.io.IOException;
import me.nithanim.cultures.formats.lib.archive.Archive;

public interface VirtualArchive extends Archive {
    void appendFile(File virtualPath, File src);
    void saveFile(File dest) throws IOException;
}
