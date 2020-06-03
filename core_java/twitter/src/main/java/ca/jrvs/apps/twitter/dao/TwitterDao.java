package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com/1.1/statuses/";
  private static final String CREATE_PATH = "update.json?status=";
  private static final String READ_PATH = "show.json?id=";
  private static final String DELETE_PATH = "destroy/";
  //URI symbols
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet entity) {
    URI uri;
    try {
      uri = getCreateURI(entity);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Wrong input for tweet : " + e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    return jsonParser(response);
  }

  /**
   * Create URI for posting a tweet
   *
   * @param entity
   * @return HTTP post URI
   * @throws URISyntaxException
   */
  private URI getCreateURI(Tweet entity) throws URISyntaxException {
    String status = entity.getText();
    double[] coordinates = entity.getCoordinates().getLongLat();
    StringBuilder stringBuilder = new StringBuilder(API_BASE_URI);
    stringBuilder.append(CREATE_PATH);
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    stringBuilder.append(percentEscaper.escape(status))
        .append(AMPERSAND)
        .append("long")
        .append(EQUAL)
        .append(percentEscaper.escape(String.valueOf(coordinates[0])))
        .append(AMPERSAND)
        .append("lat")
        .append(EQUAL)
        .append(percentEscaper.escape(String.valueOf(coordinates[1])));
    System.out.println(stringBuilder.toString());
    return new URI(stringBuilder.toString());
  }


  /**
   * Find an entity(Tweet) by its id
   *
   * @param s entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(String s) {
    URI uri;
    try {
      uri = getReadUri(s);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Tweet ID not found : " + e);
    }

    HttpResponse response = httpHelper.httpGet(uri);
    return jsonParser(response);
  }

  /**
   * Create URI for retrieving a tweet given its id
   *
   * @param id
   * @return HTTP get URI
   * @throws URISyntaxException
   */
  private URI getReadUri(String id) throws URISyntaxException {
    StringBuilder stringBuilder = new StringBuilder(API_BASE_URI)
        .append(READ_PATH)
        .append(id);
    return new URI(stringBuilder.toString());
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param s of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(String s) {
    URI uri;
    try {
      uri = getDeleteUri(s);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Tweet ID not found : " + e);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return jsonParser(response);
  }

  /**
   * Create a URI for deleting a tweet
   *
   * @param id
   * @return HTTP post URI
   * @throws URISyntaxException
   */
  private URI getDeleteUri(String id) throws URISyntaxException {
    StringBuilder stringBuilder = new StringBuilder(API_BASE_URI)
        .append(DELETE_PATH)
        .append(id)
        .append(".json");
    return new URI(stringBuilder.toString());
  }

  /**
   * Parses Http response (JSON) into a tweet object using the JsonParser class if the response has
   * status code 200
   *
   * @param response
   * @return parsed tweet
   */
  protected Tweet jsonParser(HttpResponse response) {
    Tweet tweet = null;
    int status = response.getStatusLine().getStatusCode();
    if (status != HttpStatus.SC_OK) {
      throw new RuntimeException("Unexpected status code : " + status);
    } else if (response.getEntity() != null) {
      String tweetStr;
      try {
        tweetStr = EntityUtils.toString(response.getEntity());
      } catch (IOException e) {
        throw new RuntimeException("Failed to convert JSON into string : " + e);
      }
      try {
        return JsonParser.toObjectFromJson(tweetStr, Tweet.class);
      } catch (IOException e) {
        throw new RuntimeException("Failed to create Tweet object : " + e);
      }
    } else {
      throw new RuntimeException("Response body empty");
    }
  }
}
