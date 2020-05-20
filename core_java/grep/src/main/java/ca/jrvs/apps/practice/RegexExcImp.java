package ca.jrvs.apps.practice;

import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

  /**
   * return true if filename extension is jpg or jpeg (case insensitive)
   *
   * @param filename
   * @return
   */
  public boolean matchJpeg(String filename) {
    return Pattern.matches("(.+\\.jpe?g)$",filename);
  }

  /**
   * return true if ip is valid
   *
   * @param ip
   * @return
   */
  public boolean matchIp(String ip) {
    return Pattern.matches("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b",ip);
  }

  /**
   * return true if line is empty (e.g empty, whitespace, tabs, etc..)
   *
   * @param line
   * @return
   */
  public boolean isEmptyLine(String line) {
    return Pattern.matches("[\\t\\n\\s]*", line);
  }
}
