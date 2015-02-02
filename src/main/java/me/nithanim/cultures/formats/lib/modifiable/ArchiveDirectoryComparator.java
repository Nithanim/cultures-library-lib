package me.nithanim.cultures.formats.lib.modifiable;

import java.util.Comparator;
import me.nithanim.cultures.formats.lib.ArchiveDirectory;

public class ArchiveDirectoryComparator implements Comparator<ArchiveDirectory> {
    @Override
    public int compare(ArchiveDirectory o1, ArchiveDirectory o2) {
        if(o1.getLevel() < o2.getLevel()) {
            return o1.getLevel() - o2.getLevel();
        } else if(o1.getLevel() == o2.getLevel()) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        } else {
            return o1.getLevel() - o2.getLevel();
        }
    }

}
