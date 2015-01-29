package me.nithanim.cultures.formats.lib;

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
}
