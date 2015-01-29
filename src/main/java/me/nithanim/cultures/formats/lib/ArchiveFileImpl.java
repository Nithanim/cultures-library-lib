package me.nithanim.cultures.formats.lib;

import io.netty.buffer.ByteBuf;
import java.io.File;
import java.io.IOException;
import me.nithanim.cultures.formats.lib.internal.FileExtractor;
import me.nithanim.cultures.formats.lib.internal.FileMeta;

public class ArchiveFileImpl implements ArchiveFile {
    private final FileMeta fileMeta;
    private final ByteBuf buf;
    
    private File file;
    
    ArchiveFileImpl(FileMeta fileMeta, ByteBuf buf) {
        this.fileMeta = fileMeta;
        this.buf = buf.slice((int)fileMeta.getPos(), (int)fileMeta.getLength());
    }
    
    @Override
    public String getFileName() {
        ensureFile();
        return file.getName();
    }
    
    @Override
    public String getFullPath() {
        return fileMeta.getName();
    }
    
    @Override
    public String getPath() {
        ensureFile();
        return file.getParent();
    }

    @Override
    public File getFile() {
        return file;
    }
    
    @Override
    public void extractTo(File dest) throws IOException {
        FileExtractor.extract(buf, dest);
    }

    @Override
    public void extractToWithOriginalDirecotry(File dest) throws IOException {
        File target = new File(dest, getFullPath());
        target.getParentFile().mkdirs();
        FileExtractor.extract(buf, target);
    }
    
    private void ensureFile() {
        if(file == null) {
            file = new File(fileMeta.getName());
        }
    }
}
