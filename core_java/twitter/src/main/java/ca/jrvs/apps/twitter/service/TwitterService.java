package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    Tweet twee = (Tweet) dao.create(tweet);
    return twee;
  }

  private void validatePostTweet(Tweet tweet) {
    if (tweet == null) {
      throw new NullPointerException("no tweet");
    }
    if (tweet.getText().length() > 140) {
      throw new IllegalArgumentException("out of bound 140");
    }

    Double lon = tweet.getCoordinates().getCoordinates()[0];
    Double lat = tweet.getCoordinates().getCoordinates()[1];
    if (lon > 180 || lon < -180 || lat > 90 || lat < -90) {
      throw new RuntimeException("out of coordinates");
    }
  }

  /**
   * Search a tweet by ID
   *
   * @param id     tweet id
   *
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id) {
    validateId(id);
    Tweet twee = (Tweet) dao.findById(id);
    return twee;
  }

  /**
   * validate id of the post
   * @param id
   */
  private void validateId(String id) {
    if (!id.matches("^\\d+$")) {
      throw new RuntimeException("Invalid Tweet Id");
    }
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deleted = new ArrayList<>();

    for (String id : ids) {
      validateId(id);
      deleted.add((Tweet) dao.deleteById(id));
    }

    return deleted;
  }
}
