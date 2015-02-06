package me.nithanim.cultures.formats.lib.modifiable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;
import me.nithanim.cultures.formats.lib.ArchiveDirectoryImpl;
import me.nithanim.cultures.formats.lib.ArchiveFile;
import me.nithanim.cultures.formats.lib.internal.DirMeta;
import me.nithanim.cultures.formats.lib.util.Disposable;

public class VirtualArchiveWithDirs implements VirtualArchive, Disposable {
    private final List<VirtualArchiveFile> filesToBeAdded = new LinkedList<VirtualArchiveFile>();
    
    @Override
    public void appendFile(File virtualPath, File src) {
        if(!src.exists()) {
            throw new IllegalArgumentException(src + " does not exist!");
        }
        filesToBeAdded.add(new VirtualArchiveFile(virtualPath, src));
    }
    
    @Override
    public void appendDirectory(File src) {
        if(!src.isDirectory()) {
            throw new IllegalArgumentException(src + " must be a directory!");
        } else if(!src.exists()) {
            throw new IllegalArgumentException(src + " does not exist!");
        }
        appendDirectory0(src.getAbsolutePath().length() + 1, src); //+1 to cut off "\" at beginning of virtual path
    }
    
    private void appendDirectory0(int virtualPathOffset, File src) {
        for(File f : src.listFiles()) {
            if(f.isDirectory()) {
                appendDirectory0(virtualPathOffset, f);
            } else {
                appendFile(new File(f.getAbsolutePath().substring(virtualPathOffset)), f);
            }
        }
    }
    
    @Override
    public void saveFile(File dest) throws IOException {
        Set<ArchiveDirectory> directories = getAllDirectoriesToWrite();
        List<ArchiveFile> files = getAllFilesToWrite();
        
        LibWriter libWriter = new LibWriter(directories, files);
        libWriter.writeToFile(dest);
    }
    
    private Set<ArchiveDirectory> getAllDirectoriesToWrite() {
        Set<ArchiveDirectory> dirs = new TreeSet<ArchiveDirectory>(new ArchiveDirectoryComparator());
        //add root
        dirs.add(new ArchiveDirectoryRootDummy());
        
        //add dirs
        for (VirtualArchiveFile fileToBeAdded : filesToBeAdded) {
            DirMeta dirMeta = new DirMeta(fileToBeAdded.getPath());
            ArchiveDirectory archiveDir = new ArchiveDirectoryImpl(dirMeta);
            dirs.add(archiveDir);
        }
        return dirs;
    }
    
    private List<ArchiveFile> getAllFilesToWrite() {
        List files = new LinkedList<ArchiveFile>();
        files.addAll(filesToBeAdded);
        return files;
    }
    
    @Override
    public List<VirtualArchiveFile> getFileList() {
        return Collections.unmodifiableList(filesToBeAdded);
    }

    @Override
    public ArchiveFile getFileByVirtualPath(String path) {
        for(VirtualArchiveFile file : filesToBeAdded) {
            if(file.getFullPath().equals(path)) {
                return file;
            }
        }
        return null;
    }
    
    @Override
    public int getUnknown() {
        return 1; //since it seems like a constant
    }

    @Override
    public int getFileCount() {
        return filesToBeAdded.size();
    }

    @Override
    public void dispose() {
        
    }
}
