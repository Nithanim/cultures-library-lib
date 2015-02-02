package me.nithanim.cultures.formats.lib;

import io.netty.buffer.ByteBuf;
import java.io.File;
import me.nithanim.cultures.formats.lib.internal.FileMeta;

public interface ArchiveFile {
    /**
     * Returns the file name only
     * 
     * @return The file name
     */
    String getFileName();
    
    /**
     * Returns the full path (dir + name)
     * 
     * @return The complete path
     */
    String getFullPath();
    
    /**
     * Returns the directory in which the file is located only
     * 
     * @return The direcotry only
     */
    String getPath();
    
    /**
     * Same as {@link #getFullPath()} but as {@link File}
     * 
     * @return The full path as {@link File}
     */
    File getFile();
    
    /**
     * Exposes the internal buffer that holds the contents of the file
     * 
     * @return 
     */
    ByteBuf getBuffer();
    
    int getSize();
    
    /**
     * INTERNAL!<br/>
     */
    FileMeta getFileMeta();
}
