# SimpleBash
It's my simplified implementation of bash.

## What it can do

**Here is a list of supported commands**
```
cat <filename> prints lines from <filename>
cd <dirname> changes directory to <dirname>
cp <src>, <dest> copies <src> file to <dest>
exit (no args) stops bash
help (no args) prints list of available commands and their description
ls (no args) prints list of files in current directory
mkdir <dirname> creates directory with <dirname> in current directory
tail <filename> [-n <number of lines to print>] prints last N lines of file
touch <filename> creates <filename> in current directory
 ```
**Also it supports follow operands**

```
&& - execute the command that follows the && only if the first command is successful
|| - execute the command that follows the || only if the first command is failed
> - redirects output of the first command to file
```
 
### Setup
``` bash
cd SimpleBash && mvn install
cd target && java -jar SimpleBash-1.0-SNAPSHOT.jar
```

