package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   * Helper method to map an iexQuote to a quote entity
   * Note: 'iexQuote.getLatestPrice()==null' if the stock market is closed
   *
   * @param iexQuote to be mapped
   * @return mapped Quote
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setTicker(iexQuote.getSymbol());
    quote.setLastPrice(iexQuote.getLatestPrice());
    quote.setBidPrice(iexQuote.getIexBidPrice());
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setAskPrice(iexQuote.getIexAskPrice());
    quote.setAskSize(iexQuote.getIexAskSize());
    return quote;
  }

  /**
   * Update quote table against IEX source
   * - get all quotes from the db
   * - foreach ticker get iexQuote
   * - convert iexQuote to quote entity
   * - persist quote to db
   *
   * @return updated quotes
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
   * @throws IllegalArgumentException                           for invalid input
   */
  public List<Quote> updateMarketData() {
    List<Quote> quotes = findAllQuotes();
    for (Quote quote : quotes) {
      IexQuote iexQuote = findIexQuoteByTicker(quote.getTicker());
      quote = buildQuoteFromIexQuote(iexQuote);
    }
    saveAllQuotes(quotes);
    return quotes;
  }

  /**
   * Find all quotes from the quote table
   *
   * @return a list of quotes
   */
  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }

  /**
   * Persists quotes to database
   *
   * @param quotes a list of quotes
   * @return updated quotes
   */
  public List<Quote> saveAllQuotes(List<Quote> quotes) {
    quoteDao.saveAll(quotes);
    return quotes;
  }

  /**
   * Finds an IexQuote
   *
   * @param ticker ticker whose quote is to be found
   * @return quote from IEX for ticker
   * @throws IllegalArgumentException if ticker is not found in IEX Cloud
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    Optional<IexQuote> iexquote;
    try {
      iexquote = marketDataDao.findById(ticker.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    if (iexquote.isPresent()) {
      return iexquote.get();
    } else {
      throw new IllegalArgumentException("Inavalid ticker : " + ticker);
    }
  }

  /**
   * Updates/adds provided quote to quote table
   *
   * @param quote provided quote
   * @return updated/added quote
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  /**
   * Adds a quote of new ticker to the quote table
   *
   * @param tickerId new ticker
   * @return added quote
   */
  public Quote saveQuote(String tickerId) {
    Quote newQuote = buildQuoteFromIexQuote(findIexQuoteByTicker(tickerId));
    return saveQuote(newQuote);
  }
}