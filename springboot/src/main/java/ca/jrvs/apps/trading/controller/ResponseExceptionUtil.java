package ca.jrvs.apps.trading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ResponseExceptionUtil {
  private static  final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

  public static ResponseStatusException getResponseStatusException(Exception e){
    if (e instanceof IllegalArgumentException){
      logger.debug("invalid input",e);
      return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }else{
      logger.error("Internal Error",e);
      return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"internal error:please contact admin");
    }
  }

}
