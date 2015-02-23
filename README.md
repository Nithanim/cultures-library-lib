# cultures-library-lib
This library is able to extract and write .lib and .c2m of the game series "cultures" by Funatics.

## Extract
```java
ReadableArchive a = new ArchiveWithDirsImpl();
a.bindTo(new File("\\path\\to\\eg\\8th Wonder of the World\\DataX\\Libs\\data0001.lib"));
File dest = new File("\\a\\destination\\");
for (ReadableArchiveFile file : a.getFileList()) {
    //extract the whole .lib to this directory preserving the internal path
    file.extractToWithOriginalDirectory(dest);
}
```

## Write
```java
File dest = new File(workingDir, "test.lib");
VirtualArchive a = new VirtualArchiveWithDirs();
a.appendFile(new File("data\\test1.txt"), new File(workingDir, "testfile1.txt"));
a.appendFile(new File("data\\test2.txt"), new File(workingDir, "testfile2.txt"));
a.saveFile(dest);
```

## License
This library is released under the Apache License Version 2.0.

Thanks to [netty](http://netty.io/) for their aweseome buffer library, which is not directly used any more in this project.
An own implementation of Buffer is now used (that is compatible with the method names and arguments) but uses long for indexes instead to be able to use it in combination with RandomAccessFile.
This library would have been possible with netty!

Special thanks to [Siguza](http://siguza.net/) who released [his findings on the format](http://classic.cultrix.org/specs/c2m_lib.html).