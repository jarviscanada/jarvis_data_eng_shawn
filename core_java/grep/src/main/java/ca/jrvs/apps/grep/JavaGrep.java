package ca.jrvs.apps.grep;


import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * top level search workflow
   *throws IOException
   */
  void process() throws IOException;

  /**
   *
   * @param rootDir input dir
   * @return file under the rootDir
   */
  List<File> ListFiles(String rootDir);

  /**
   * Read a file and return all the lines
   *
   * Explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given input is not a file
   */
  List<String> readLines(File inputFile);

  boolean containsPattern(String line);

  void writeToFile(List<String> lines) throws  IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);

}
