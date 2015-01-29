package me.nithanim.cultures.formats.lib.internal;

interface ArchiveFile {
    void readMetas();
    
    int getUnknown();
    int getFileCount();
    FileMeta[] getFileMetas();
}
