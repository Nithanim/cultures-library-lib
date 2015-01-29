package me.nithanim.cultures.formats.lib;

import java.io.File;
import java.io.IOException;

public interface ArchiveFile {
    String getFileName();
    String getFullPath();
    String getPath();
    File getFile();
    
    
    void extractTo(File dest) throws IOException;
    void extractToWithOriginalDirecotry(File dest) throws IOException;
}
