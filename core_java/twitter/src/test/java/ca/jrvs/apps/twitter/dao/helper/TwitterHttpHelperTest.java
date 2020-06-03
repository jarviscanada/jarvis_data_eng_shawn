package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private TwitterHttpHelper twitterHttpHelper;
  private URI uri;
  private HttpResponse response;

  @Before
  public void setUp() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
        ACCESS_TOKEN, TOKEN_SECRET);
  }

  @Test
  public void httpPost() throws URISyntaxException, IOException {
    String status = "time stamp: " + System.currentTimeMillis();
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    uri = new URI(
        "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status));
    response = twitterHttpHelper.httpPost(uri);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void httpGet() throws URISyntaxException, IOException{
    URI uri = new URI("https://api.twitter.com/1.1/statuses/show.json?id=1266921275768872960");
    response = twitterHttpHelper.httpGet(uri);
    if(response.getStatusLine().getStatusCode()==200){
      System.out.println(EntityUtils.toString(response.getEntity()));
    }
  }
}