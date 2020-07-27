package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGreplambdaImp extends JavaGrepImp {

  public static void main(String[] args) {
    if(args.length !=3) {

      JavaGreplambdaImp javaGreplambdaImp = new JavaGreplambdaImp();
      javaGreplambdaImp.setRegex(args[0]);
      javaGreplambdaImp.setRootPath(args[1]);
      javaGreplambdaImp.setOutFile(args[2]);

      try {
        javaGreplambdaImp.process();
      }catch ( Exception e) {
        javaGreplambdaImp.logger.error(e.getMessage(),e);
      }
    }
  }

  /**
   * @param rootDir input dir
   * @return file under the rootDir
   */
  @Override
  public List<File> ListFiles(String rootDir) {
    List<File> files = new ArrayList<File>();
    try{
      Files.list(Paths.get(rootDir)).collect(Collectors.toList());
    }catch (IOException e){
      logger.error(e.getMessage(),e);
    }
    return files;
  }

  /**
   * Read a file and return all the lines
   * <p>
   * Explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given input is not a file
   */
  @Override
  public List<String> readLines(File inputFile) {
    if(!inputFile.isFile())
      throw new IllegalArgumentException("not a file");
    List<String> lines= new ArrayList<String>();
    try{
      lines=Files.lines(inputFile.toPath()).collect(Collectors.toList());
    }catch(IOException e){
      logger.error(e.getMessage(),e);
    }
    return lines;
  }

  /**
   * @param line input string
   * @return boolean for finding matched pattern
   */
  @Override
  public boolean containsPattern(String line) {
    return super.containsPattern(line);
  }

  /**
   * @param lines input string to write to the outfile.
   * @throws IOException
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    super.writeToFile(lines);
  }
}
