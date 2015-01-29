# cultures-library-lib
This library extracts .lib and .c2m of the game series "cultures" by Funatics.

Here is an example to extract all files:

```java
Archive a = new ArchiveWithDirsImpl();
a.bindTo(new File("\\path\\to\\eg\\8th Wonder of the World\\DataX\\Libs\\data0001.lib"));
for(ArchiveFile af : a.getFileList()) {
    //extract the whole file to this directory preserving the internal path
    af.extractToWithOriginalDirecotry(new File("\\a\\destination\\"));
}
```

This library is released under the Apache License Version 2.0.
Thanks to [netty](http://netty.io/) for their aweseome buffer library.
Special thanks to [Siguza](http://siguza.net/) who released [his findings on the format](http://classic.cultrix.org/specs/c2m_lib.html).