package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  public static final String COORD_SEP = ":";
  public static final String COMMA = ",";
  private  Service service;

  @Autowired
  public TwitterController(Service service){this.service=service;}

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    if(args.length !=3){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"text to post\", \"longitude:latitude\"");
    }

    String tweet_txt= args[1];
    String coord = args[2];
    String[] coordArray = coord.split(COORD_SEP);
    if(StringUtils.isEmpty(tweet_txt)){
      throw new IllegalArgumentException("incorrect text");
    }
    double lon;
    double lat;
    try{
      lon=Double.parseDouble(coordArray[0]);
      lat=Double.parseDouble(coordArray[1]);
    }catch(Exception e){
      throw new IllegalArgumentException("invalid coordinates");
    }
    Tweet postTweet = TweetUtil.buildTweet(tweet_txt,lon,lat);
    return service.postTweet(postTweet);
  }


  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    if(args.length!=2){
      throw new IllegalArgumentException("not valid tweet");
    }
    String id=args[1];
    return service.showTweet(id);
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("invalid tweet format");
    }
    String[] ids = args[1].split(COMMA);
    return service.deleteTweets(ids);
  }
}
