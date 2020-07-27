package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  private Quote quote;

  @Before
  public void setUp() {
    quoteDao.deleteAll();
    quote = new Quote();
    quote.setAskPrice(5d);
    quote.setAskSize(5);
    quote.setBidPrice(5.9d);
    quote.setBidSize(5);
    quote.setTicker("MMM");
    quote.setLastPrice(5.1d);
    quoteDao.save(quote);
  }

  @Test
  public void savedQuotes() {
    List<String> tickers = new ArrayList<String>(){{add("MMM"); add("AAPL");}};
    List<Quote> savedQuotes = quoteService.savedQuotes(tickers);
    assertNotNull(savedQuotes);
    assertEquals(2, savedQuotes.size());
  }

  @Test
  public void saveQuote() {
    Quote quote = new Quote();
    quote.setAskPrice(2d);
    quote.setAskSize(1);
    quote.setBidPrice(1d);
    quote.setBidSize(1);
    quote.setTicker("JNJ");
    quote.setLastPrice(1.5d);
    Quote savedQuote  = quoteService.saveQuote(quote);

    assertNotNull(savedQuote);
    assertEquals(quote.getAskPrice(), savedQuote.getAskPrice());
    assertEquals(quote.getAskSize(), savedQuote.getAskSize());
    assertEquals(quote.getBidPrice(), savedQuote.getBidPrice(),0.001);
    assertEquals(quote.getBidSize(), savedQuote.getBidSize());
    assertEquals(quote.getTicker(), savedQuote.getTicker());
    assertEquals(quote.getLastPrice(), savedQuote.getLastPrice());
  }

  @Test
  public void updateMarketData() {
    quoteService.updateMarketData();
    assertEquals(1,quoteDao.count());
    assertTrue(quoteDao.existsById(quote.getTicker()));
  }

  @Test
  public void findAllQuotes() {
    List<Quote> quotes = quoteService.findAllQuotes();
    assertNotNull(quotes);
    assertEquals(1,quotes.size());
  }

  @Test
  public void findIexQuoteByTicker() {
    String ticker = "MMM";
    IexQuote iex = quoteService.findIexQuoteByTicker(ticker);
    assertNotNull(iex);
    assertEquals(iex.getSymbol(), ticker);

    String invalidTicker = "ASASASAS";
    try {
      quoteService.findIexQuoteByTicker(invalidTicker);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }
}