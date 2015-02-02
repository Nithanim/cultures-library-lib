package me.nithanim.cultures.formats.lib;

import java.io.File;
import java.io.IOException;

public interface ReadableArchiveFile extends ArchiveFile {
    /**
     * Extracts this file into the specified FILE.
     * 
     * @param dest The target file
     * @throws IOException 
     */
    void extractTo(File dest) throws IOException;
    
    /**
     * Extracts this file into the specified target DIRECTORY. In this
     * directory the virtual one will be created where the files
     * is stored inside the .lib/.c2m. 
     * Every non-existent directory will be created
     * 
     * @param dest Destination that will be used as "root" dir for extraction
     * @throws IOException 
     */
    void extractToWithOriginalDirectory(File dest) throws IOException;
}
