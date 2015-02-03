package me.nithanim.cultures.formats.lib.modifiable;

import java.io.File;
import java.io.IOException;
import me.nithanim.cultures.formats.lib.archive.Archive;

public interface VirtualArchive extends Archive {
    /**
     * Appends a directory recursively. The virtual path for each file
     * starts where the path of src "ends".
     * @param src 
     */
    void appendDirectory(File src);
    
    /**
     * Appends a single file to the archive.
     * 
     * @param virtualPath Path that the file should have inside the archive
     * @param src The file to be appended
     */
    void appendFile(File virtualPath, File src);
    
    /**
     * Saves the file to the specified destination. File will be overwritten!
     * 
     * @param dest Path to the file
     * @throws IOException 
     */
    void saveFile(File dest) throws IOException;
}
