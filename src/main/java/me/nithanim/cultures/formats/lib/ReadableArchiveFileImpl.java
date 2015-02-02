package me.nithanim.cultures.formats.lib;

import io.netty.buffer.ByteBuf;
import java.io.File;
import java.io.IOException;
import me.nithanim.cultures.formats.lib.internal.FileExtractor;
import me.nithanim.cultures.formats.lib.internal.FileMeta;

public class ReadableArchiveFileImpl implements ReadableArchiveFile {
    private final FileMeta fileMeta;
    private final ByteBuf buf;
    
    private File file;
    
    public ReadableArchiveFileImpl(FileMeta fileMeta, ByteBuf buf) {
        this.fileMeta = fileMeta;
        this.buf = buf;
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
        extractTo0(dest);
    }

    @Override
    public void extractToWithOriginalDirectory(File dest) throws IOException {
        File target = new File(dest, getFullPath());
        target.getParentFile().mkdirs();
        extractTo0(target);
    }
    
    private void extractTo0(File dest) throws IOException {
        FileExtractor.extract(getBuffer(), dest);
    }
    
    private void ensureFile() {
        if(file == null) {
            file = new File(fileMeta.getName());
        }
    }

    @Override
    public ByteBuf getBuffer() {
        return buf.slice((int)fileMeta.getPos(), (int)fileMeta.getLength());
    }

    @Override
    public FileMeta getFileMeta() {
        return fileMeta;
    }

    @Override
    public int getSize() {
        return (int)fileMeta.getLength();
    }
}
