package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getRootPath() {
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getOutFile() {
    return outFile;
  }

  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    BasicConfigurator.configure();
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }

  /**
   * top level search workflow throws IOException
   */
  @Override
  public void process() throws IOException {
    List<String> matchedLines= new ArrayList<String>();
    for ( File file : ListFiles(getRootPath())) {
      for (String line : readLines(file)) {
        if(containsPattern(line)){
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  /**
   * @param rootDir input dir
   * @return file under the rootDir
   */
  @Override
  public List<File> ListFiles(String rootDir) {
    List<File> files = new ArrayList<File>();
    File file = new File(rootDir);
    if(file.exists()){
      if(file.isFile()){
        files.add(file);
        return files;
      }else if(file.isDirectory()){
        File[] contains = file.listFiles();
        if (contains != null){
          for (File f: contains){
            List<File> ff = ListFiles(f.getAbsolutePath());
            if(ff!= null){
              files.addAll(ff);
            }
          }
        }
      }
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
    BufferedReader reader;
    if(!inputFile.isFile())
      throw new IllegalArgumentException(inputFile+"is not a file");
    List<String> line = new ArrayList<String>();
    try{
      reader = new BufferedReader(new FileReader(inputFile));
      String newline =reader.readLine();
      while(newline!=null){
        line.add(newline);
        newline=reader.readLine();
      }
      reader.close();
    }catch(IOException e){
      logger.error(e.getMessage(),e);
    }
    return line;
  }

  /**
   *
   * @param line input string
   * @return boolean for finding matched pattern
   */
  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(), line);
  }

  /**
   *
   * @param lines input string to write to the outfile.
   * @throws IOException
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter writer = null;
    try {
      writer=new BufferedWriter(new FileWriter(new File(getOutFile())));
      for (String f : lines) {
        writer.write(f);
      }
    }catch(IOException e){
      logger.error(e.getMessage(),e);
    }finally {
      if(writer != null)
      writer.close();
    }
  }
}
