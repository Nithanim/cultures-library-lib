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
Thanks to [netty](http://netty.io/) for their aweseome buffer library.
Special thanks to [Siguza](http://siguza.net/) who released [his findings on the format](http://classic.cultrix.org/specs/c2m_lib.html).