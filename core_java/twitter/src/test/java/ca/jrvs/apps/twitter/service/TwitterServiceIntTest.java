package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwitterServiceIntTest {

  private static TwitterService twitterService;
  private List<String> listOfIds = new ArrayList<String>();

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("CONSUMER_KEY");
    String consumerSecret = System.getenv("CONSUMER_SECRET");
    String accessToken = System.getenv("ACCESS_TOKEN");
    String tokenSecret = System.getenv("TOKEN_SECRET");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

    TwitterDao dao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(dao);
  }

  @Test
  public void postTweet() {
    String text;
    for (int i = 0; i < 3; i++) {
      text = "#abc with timestamp" + System.currentTimeMillis();
      Tweet newTweet = TweetUtil.buildTweet(text, 10, 1);
      Tweet createdTweet = twitterService.postTweet(newTweet);
      assertEquals(createdTweet.getText(), newTweet.getText());
      listOfIds.add(createdTweet.getIdStr());
    }
  }

  @Test
  public void showTweet() {
    for (String id : listOfIds) {
      Tweet showedTweet = twitterService.showTweet(id);
      assertNotNull(showedTweet);
      assertEquals(showedTweet.getIdStr(), id);
    }
  }

  @Test
  public void deleteTweets() {
    List<Tweet> deletedTweets = twitterService.deleteTweets(listOfIds.toArray(new String[0]));
    for (int i = 0; i < listOfIds.size(); i++) {
      assertEquals(deletedTweets.get(i).getIdStr(), listOfIds.get(i));
    }
  }
}