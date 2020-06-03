package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

  /**
   * creates a new Tweet with text and coordinates
   *
   * @return created tweet
   */
  public static Tweet createTweet() {
    String status = "timestamp#abc " + System.currentTimeMillis();
    double[] longLat = {10, -1};
    Coordinates coordinates = new Coordinates(longLat, "Point");
    return new Tweet(status, coordinates);
  }

  /**
   * creates a new tweet which is slightly different from the one created in createTweet() method to
   * avoid duplicate tweets when calling createTweet() consecutively
   *
   * @return created tweet
   */
  public static Tweet createDifferentTweet() {
    String status = "timestamp#abc " + System.currentTimeMillis();
    double[] longLat = {10.0, -1.0};
    Coordinates coordinates = new Coordinates(longLat, "Point");
    return new Tweet(status, coordinates);
  }

}
