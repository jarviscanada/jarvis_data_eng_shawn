package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        e.printStackTrace();
      }
    }
  }

  /**
   * @param rootDir input dir
   * @return file under the rootDir
   */
  @Override
  public List<File> ListFiles(String rootDir) {
    return super.ListFiles(rootDir);
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
    return super.readLines(inputFile);
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
