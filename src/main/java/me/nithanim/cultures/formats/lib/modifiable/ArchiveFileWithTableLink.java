package me.nithanim.cultures.formats.lib.modifiable;

import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;
import me.nithanim.cultures.formats.lib.ArchiveFile;

/**
 * A wrapper class that can write the table entry at the beginning
 * of the archive and the content itself. It also updates the
 * content starting position in the table entry when actually writing
 * the content.
 */
class ArchiveFileWithTableLink {
    private static final Charset CHARSET = Charset.forName("US-ASCII");
    
    private final ArchiveFile archiveFile;
    private ByteBuf tableEntryBuffer;

    public ArchiveFileWithTableLink(ArchiveFile archiveFile) {
        this.archiveFile = archiveFile;
    }

    public void writeTable(ByteBuf buffer) {
        this.tableEntryBuffer = buffer;
        buffer.writeInt(archiveFile.getFullPath().length());
        buffer.writeBytes(archiveFile.getFullPath().getBytes(CHARSET));
        buffer.writeInt(-1); //we don't know the pos yet
        buffer.writeInt(archiveFile.getSize());
    }

    public void writeContents(ByteBuf buffer, int contentAddr) {
        int contentPosFieldInTableEntry = tableEntryBuffer.capacity() - 4 - 4;
        tableEntryBuffer.setInt(contentPosFieldInTableEntry, contentAddr);
        buffer.writeBytes(archiveFile.getBuffer());
    }

    public int getTableSize() {
        return 4 + archiveFile.getFullPath().length() + 4 + 4;
    }

    public int getContentSize() {
        return archiveFile.getSize();
    }

    public ArchiveFile getArchiveFile() {
        return archiveFile;
    }
}
