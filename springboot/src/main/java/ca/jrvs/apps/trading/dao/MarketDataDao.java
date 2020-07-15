package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Is responsible for getting quotes from IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  /**
   * Executes a get request and returns http entity/body as a string using EntityUtils.toString
   *
   * @param url resource URL
   * @return http response or Optional.empty for 404 response
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP fails or status code is
   *                                                               unexpected
   */
  private Optional<String> executeHttpGet(String url) {
    HttpGet request = new HttpGet(url);
    HttpResponse response;

    try {
      response = getHttpClient().execute(request);
    } catch (IOException e) {
      throw new DataRetrievalFailureException("HTTP request execution failed : " + e);
    }

    int status = response.getStatusLine().getStatusCode();

    if (status == HttpStatus.SC_NOT_FOUND) {
      return Optional.empty();
    } else if (status == HttpStatus.SC_BAD_REQUEST) {
      throw new IllegalArgumentException("Invalid ticker.");
    } else if (status != HttpStatus.SC_OK) {
      throw new DataRetrievalFailureException("Unexpected status code : " + status);
    } else if (response.getEntity() != null) {
      try {
        String quoteString = EntityUtils.toString(response.getEntity());
        return Optional.of(quoteString);
      } catch (IOException e) {
        throw new RuntimeException("Failed to convert JSON to String : " + e);
      }
    } else {
      return Optional.empty();
    }
  }

  /**
   * Builds HTTP client
   *
   * @return a closeable HTTP client
   */
  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        //prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true).build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Gets an IexQuote (helper method for findAllById)
   *
   * @param ticker
   * @return an IexQuote if found, else return Optional.empty
   * @throws IllegalArgumentException      if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));
    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      return Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Gets quotes from IEX
   *
   * @param tickers list of tickers
   * @return a list of IexQuote objects
   * @throws IllegalArgumentException      if any ticker is invalid or ticker is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    List<IexQuote> quotes = new LinkedList<>();
    IexQuote quote;

    //create URL
    StringBuilder symbols = new StringBuilder();
    for (String ticker : tickers) {
      symbols.append(ticker + ",");
    }
    symbols.deleteCharAt(symbols.length() - 1);
    String url = String.format(IEX_BATCH_URL, symbols.toString());

    //execute request and check if all tickers were valid
    Optional<String> response = executeHttpGet(url);
    String responseEntity;
    if (response.isPresent()) {
      responseEntity = response.get();
      for (String ticker : tickers) {
        if (!responseEntity.contains(ticker)) {
          throw new IllegalArgumentException("Invalid ticker : " + ticker);
        }
      }

      //create POJOs
      JSONObject jsonResponse = new JSONObject(responseEntity);
      Iterator<String> iterator = jsonResponse.keys();
      while (iterator.hasNext()) {
        String symbol = iterator.next();
        JSONObject jsonQuote = jsonResponse.getJSONObject(symbol);
        String quoteString = jsonQuote.get("quote").toString();
        try {
          quote = parseJson(quoteString, IexQuote.class);
          quotes.add(quote);
        } catch (IOException e) {
          throw new RuntimeException("Failed to create IexQuote object : " + e);
        }
      }
      return quotes;
    } else {
      throw new IllegalArgumentException("Invalid ticker(s)");
    }
  }

  /**
   * Parses JSON string to an object
   *
   * @param <T>   Type
   * @param json  JSON string
   * @param clazz object class
   * @return Object of specified class
   * @throws IOException
   */
  private <T> T parseJson(String json, Class clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return (T) m.readValue(json, clazz);
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

}