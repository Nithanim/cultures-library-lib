package me.nithanim.cultures.formats.lib;

import me.nithanim.cultures.formats.lib.internal.DirMeta;

public interface ArchiveDirectory {
    /**
     * Returns the path name
     * 
     * @return path name
     */
    String getName();
    
    /**
     * Returns the depth of the directory structure
     * it represents. In other words it returns the amount
     * '/' in the path string.
     * 
     * @return Depth of path
     */
    int getLevel();
    
    /**
     * INTERNAL!
     */
    DirMeta getDirMeta();
}
