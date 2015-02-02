package me.nithanim.cultures.formats.lib.archive;

import java.io.File;
import java.io.IOException;
import java.util.List;
import me.nithanim.cultures.formats.lib.ReadableArchiveFile;

public interface ReadableArchive extends Archive {
    @Override
    List<ReadableArchiveFile> getFileList();
    
    /**
     * Bind this instance of an archive to a specific file in 
     * the files system and performs initial reading.
     * 
     * @param file The file to load.
     * @throws IOException 
     */
    void bindTo(File file) throws IOException;
    
    /**
     * Releases all information of a spefific file if this
     * instance was previously bound to a file.
     * Does nothing if it has never been bound.
     */
    void unbind();
    
    File getBoundFile();
    
    boolean isBound();
}
