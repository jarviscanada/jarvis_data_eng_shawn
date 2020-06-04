package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Before
  public void setUp() throws Exception {
    String tweetJsonStr = "{\n" +
        "  \"created_at\" : \"Thu Jun 04 01:46:18 +0000 2020\",\n" +
        "  \"id\" : 1268358385075961862,\n" +
        "  \"id_str\" : \"1268358385075961862\",\n" +
        "  \"text\" : \"timestamp20159115163212\",\n" +
        "  \"entities\" : {\n" +
        "    \"hashtags\" : [ ],\n" +
        "    \"user_mentions\" : [ ]\n" +
        "  },\n" +
        "  \"coordinates\" : {\n" +
        "    \"coordinates\" : [ 10.0, 1.0 ],\n" +
        "    \"type\" : \"Point\"\n" +
        "  },\n" +
        "  \"retweet_count\" : 0,\n" +
        "  \"retweeted\" : false,\n" +
        "  \"entites\" : {\n" +
        "    \"hashtags\" : [ ],\n" +
        "    \"user_mentions\" : [ ]\n" +
        "  },\n" +
        "  \"favourite_count\" : 0\n" +
        "}";
    try{
      Tweet expectTweet = JsonUtil.toObjectFromJson(tweetJsonStr,Tweet.class);
    }catch (IOException e){
      throw new IllegalArgumentException(e);
    }
  }

  @Test
  public void postTweet() {
    when(dao.create(any())).thenReturn(new Tweet());
    try {
      service.postTweet(TweetUtil.buildTweet("test", 10.0, 1.0));
    } catch (RuntimeException e) {
      assertTrue(true);
    }
    Tweet tweet = service.postTweet(TweetUtil.buildTweet("timestamp20159115163212", 10.0, 1));
    assertNotNull(tweet);
  }

  @Test
  public void showTweet() {
    when(dao.findById(any())).thenReturn(new Tweet());
    try{
      service.showTweet("id is not valid");
      fail();
    }catch(RuntimeException e){
      assertTrue(true);
    }
    Tweet tweet = service.showTweet("1268358385075961862");
    assertNotNull(tweet);
  }

  @Test
  public void deleteTweets() {
    when(dao.deleteById(isNotNull())).thenReturn(new Tweet());
    try {
      String[] ids = {"this is a wrong tweet id", "1268358385075961862"};
      service.deleteTweets(ids);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    String[] ids = {"1268358385075961862", "1268597451696549900"};
    List<Tweet> list = service.deleteTweets(ids);
    for (Tweet tweet : list) {
      assertNotNull(tweet);
    }
  }
}