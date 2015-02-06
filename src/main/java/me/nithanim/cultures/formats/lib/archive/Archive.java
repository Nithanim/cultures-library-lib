package me.nithanim.cultures.formats.lib.archive;

import java.util.List;
import me.nithanim.cultures.formats.lib.ArchiveFile;

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
    List<? extends ArchiveFile> getFileList();
    
    ArchiveFile getFileByVirtualPath(String path);
}
