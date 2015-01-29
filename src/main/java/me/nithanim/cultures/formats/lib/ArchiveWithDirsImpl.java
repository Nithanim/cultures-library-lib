package me.nithanim.cultures.formats.lib;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.nithanim.cultures.formats.lib.internal.ArchiveFileDirs;
import me.nithanim.cultures.formats.lib.internal.DirMeta;
import me.nithanim.cultures.formats.lib.internal.FileMeta;

public class ArchiveWithDirsImpl implements ArchiveWithDirs {
    private FileChannel fileChannel;
    private ArchiveFileDirs internal;
    private ByteBuf buffer;
    
    private List<ArchiveDirectory> archiveDirectories;
    private List<ArchiveFile> archiveFiles;
    
    @Override
    public int getUnknown() {
        return internal.getUnknown();
    }
    
    @Override
    public int getDirCount() {
        return getDirectories().size();
    }
    
    @Override
    public List<ArchiveDirectory> getDirectories() {
        if(archiveDirectories == null) {
            archiveDirectories = Collections.unmodifiableList(convertDirMetaToArchiveDirectoryList(internal.getDirMetas()));
        }
        return archiveDirectories;
    }
    
    @Override
    public int getFileCount() {
        return getFileList().size();
    }
    
    @Override
    public List<ArchiveFile> getFileList() {
        if(archiveFiles == null) {
            archiveFiles = Collections.unmodifiableList(convertDirMetaToArchiveFileList(internal.getFileMetas()));
        }
        return archiveFiles;
    }
    
    @Override
    public void bindTo(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        fileChannel = raf.getChannel();
        reload();
    }
    
    private void reload() throws IOException {
        MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        buffer = Unpooled.wrappedBuffer(mbb).order(ByteOrder.LITTLE_ENDIAN);
        internal = new ArchiveFileDirs(buffer);
        internal.readMetas();
    }

    @Override
    public void unbind() throws IOException{
        fileChannel.close();
        fileChannel = null;
    }
    
    private List<ArchiveDirectory> convertDirMetaToArchiveDirectoryList(DirMeta[] dirMetas) {
        ArrayList<ArchiveDirectory> newArchiveDirectories = new ArrayList<ArchiveDirectory>(dirMetas.length);
        for (DirMeta dirMeta : dirMetas) {
            newArchiveDirectories.add(convertDirMetaToArchiveDirectory(dirMeta));
        }
        return newArchiveDirectories;
    }
    
    private ArchiveDirectory convertDirMetaToArchiveDirectory(DirMeta dirMeta) {
        return new ArchiveDirectoryImpl(dirMeta);
    }
    
    private List<ArchiveFile> convertDirMetaToArchiveFileList(FileMeta[] fileMetas) {
        ArrayList<ArchiveFile> directories = new ArrayList<ArchiveFile>(fileMetas.length);
        for (FileMeta fileMeta : fileMetas) {
            directories.add(convertFileMetaToArchiveFile(fileMeta));
        }
        return directories;
    }
    
    private ArchiveFile convertFileMetaToArchiveFile(FileMeta fileMeta) {
        return new ArchiveFileImpl(fileMeta, buffer);
    }
}
