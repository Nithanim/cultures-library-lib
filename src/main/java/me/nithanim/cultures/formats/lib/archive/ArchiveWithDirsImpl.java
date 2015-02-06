package me.nithanim.cultures.formats.lib.archive;

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
import me.nithanim.cultures.formats.lib.ArchiveDirectory;
import me.nithanim.cultures.formats.lib.ArchiveDirectoryImpl;
import me.nithanim.cultures.formats.lib.ReadableArchiveFile;
import me.nithanim.cultures.formats.lib.ReadableArchiveFileImpl;
import me.nithanim.cultures.formats.lib.internal.ArchiveFileDirs;
import me.nithanim.cultures.formats.lib.internal.DirMeta;
import me.nithanim.cultures.formats.lib.internal.FileMetaImpl;
import me.nithanim.cultures.formats.lib.util.Disposable;
import me.nithanim.cultures.formats.lib.util.Disposer;

public class ArchiveWithDirsImpl implements ArchiveWithDirs, ReadableArchive, Disposable {
    private File boundTo;
    
    private RandomAccessFile randomAccessFile;
    protected ArchiveFileDirs internal;
    private ByteBuf buffer;
    
    private List<ArchiveDirectory> archiveDirectories;
    private List<ReadableArchiveFile> archiveFiles;
    
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
    public List<ReadableArchiveFile> getFileList() {
        if(archiveFiles == null) {
            archiveFiles = Collections.unmodifiableList(convertDirMetaToReadableArchiveFileList(internal.getFileMetas()));
        }
        return archiveFiles;
    }

    @Override
    public ReadableArchiveFile getFileByVirtualPath(String path) {
        for(ReadableArchiveFile file : getFileList()) {
            if(file.getFullPath().equals(path)) {
                return file;
            }
        }
        return null;
    }
    
    @Override
    public void bindTo(File file) throws IOException {
        if(isBound()) {
            throw new IllegalStateException("Archive is already bound!");
        }
        try {
            boundTo = file;
            randomAccessFile = new RandomAccessFile(file, "r");
            reload();
        } catch(IOException e) {
            unbind();
            throw e;
        }
    }
    
    private void reload() throws IOException {
        MappedByteBuffer mbb = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, randomAccessFile.length());
        buffer = Unpooled.unmodifiableBuffer(Unpooled.wrappedBuffer(mbb)).order(ByteOrder.LITTLE_ENDIAN);
        internal = new ArchiveFileDirs(buffer);
        internal.readMetas();
    }

    @Override
    public void unbind() {
        dispose();
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
    
    private List<ReadableArchiveFile> convertDirMetaToReadableArchiveFileList(FileMetaImpl[] fileMetas) {
        ArrayList<ReadableArchiveFile> files = new ArrayList<ReadableArchiveFile>(fileMetas.length);
        for (FileMetaImpl fileMeta : fileMetas) {
            files.add(convertFileMetaToArchiveFile(fileMeta));
        }
        return files;
    }
    
    private ReadableArchiveFile convertFileMetaToArchiveFile(FileMetaImpl fileMeta) {
        return new ReadableArchiveFileImpl(fileMeta, buffer);
    }

    @Override
    public File getBoundFile() {
        return boundTo;
    }
    
    @Override
    public boolean isBound() {
        return getBoundFile() != null;
    }

    @Override
    public void dispose() {
        internal = null;
        if(buffer != null) {
            buffer.release();
            buffer = null;
        }
        
        if(archiveDirectories != null) {
            Disposer.dispose(archiveDirectories);
            archiveDirectories = null;
        }
        
        if(archiveFiles != null) {
            Disposer.dispose(archiveFiles);
            archiveFiles = null;
        }
        
        try {
            randomAccessFile.close();
        } catch(Exception e) {
        }
        randomAccessFile = null;
        boundTo = null;
    }
}
