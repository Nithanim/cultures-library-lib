package me.nithanim.cultures.formats.lib;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Archive {
    int getUnknown();
    
    /**
     * Returns the amount of files that are stored in this
     * archive.
     * 
     * @return Amount of files stored.
     */
    int getFileCount();
    
    /**
     * Returns a list of files in this archive in the same
     * order as they are stored.
     * 
     * @return List of files in the same order as they are stored
     */
    List<ArchiveFile> getFileList();
    
    /**
     * Bind this instance of an archive to a specific file in 
     * the files system and perdorms initial reading.
     * 
     * @param file The file to load.
     * @throws IOException 
     */
    void bindTo(File file) throws IOException;
    
    /**
     * Releases all information of a spefific file if this
     * instance was previously bound to a file.
     * Does nothing if it has never been bound.
     * 
     * @throws IOException 
     */
    void unbind() throws IOException;
}
