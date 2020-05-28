# Introduction
This Grep application dose a simple fetch text operation. It searches for a text pattern recursively in a given directory, and output matched lines to a file. 
The app takes three arguments: regex rootPath outFile, and return Lines that contain those expressions are written into a new file. 
This grep application is implemented in Java 8 with Stream and Lambda API.

# Usage
The app takes three arguments: **regex rootPath outFile**  
**regex:** a special text string for describing a search pattern  
**rootPath:** root directory path where the search operation excuted.  
**outFile:** output file name where the output from the grep is returned  

# Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

# Performance Issue
All lines of a file are stored in a List before they are used for matching patterns with the regular expression. when the file gets larger size, even using `BufferedReader` ,` BufferedReader`
the performance of it still get reduced because of the the interface limits the performance as some methods return List<> rather than Stream.

# Improvement
1. Instead of Using single streams, using parallel streams for faster performance. 
2. Instead of only grepping the data, the data can be processed and collected for further data manipulation.
