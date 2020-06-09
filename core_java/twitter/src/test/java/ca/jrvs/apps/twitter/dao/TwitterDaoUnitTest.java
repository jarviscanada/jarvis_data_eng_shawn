package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  Tweet expectedTweet;

  @Before
  public void setup() throws Exception {
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

    try {
      expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    } catch (IOException e) {
      throw new IOException("not convert");
    }
  }

  @Test
  public void postTweet() {
    //test failed request
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetUtil.buildTweet("test", 10.0, 1.0));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    //test happy path
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtil.buildTweet("Mockito test", 10.0, 1));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void findById() {
    String hashtag = "#abcd";
    String text = "some text" + hashtag + System.currentTimeMillis();
    Double lat = 10.0;
    Double lon = 1.0;
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.findById("12345");
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.findById("123abc");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteById() throws Exception {
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.deleteById("123abc");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.deleteById("123abc");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}