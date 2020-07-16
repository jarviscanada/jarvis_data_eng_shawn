package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  private Quote firstSavedQuote = new Quote();
  private Quote secondSavedQuote = new Quote();

  @Before
  public void insert() {
    firstSavedQuote.setAskPrice(10d);
    firstSavedQuote.setAskSize(10L);
    firstSavedQuote.setBidPrice(10.2d);
    firstSavedQuote.setBidSize(10L);
    firstSavedQuote.setId("aapl");
    firstSavedQuote.setLastPrice(10.1d);
    quoteDao.save(firstSavedQuote);

    secondSavedQuote.setAskPrice(11d);
    secondSavedQuote.setAskSize(11L);
    secondSavedQuote.setBidPrice(11.2d);
    secondSavedQuote.setBidSize(11L);
    secondSavedQuote.setId("amzn");
    secondSavedQuote.setLastPrice(11.1d);
    quoteDao.save(secondSavedQuote);
  }

  @After
  public void remove() {
    quoteDao.deleteAll();
  }

  @Test
  public void testUpdateMarketData() {
    //successful update
    List<Quote> updatedQuotes = quoteService.updateMarketData();

    assertNotEquals(quoteDao.findAll().toArray(), updatedQuotes.toArray());
    assertEquals(quoteDao.count(), updatedQuotes.size());
    assertEquals(firstSavedQuote.getTicker(), updatedQuotes.get(0).getTicker());
    assertEquals(secondSavedQuote.getTicker(), updatedQuotes.get(1).getTicker());

    //unsuccessful update due to wrong input
    Quote wrongTicker = new Quote();
    wrongTicker.setAskPrice(11d);
    wrongTicker.setAskSize(11L);
    wrongTicker.setBidPrice(11.2d);
    wrongTicker.setBidSize(11L);
    wrongTicker.setId("asdfghjkl");
    wrongTicker.setLastPrice(11.1d);

    quoteDao.save(wrongTicker);

    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Invalid ticker");
    quoteService.updateMarketData();
  }

  @Test
  public void testFindAllQuotes() {
    assertArrayEquals(quoteDao.findAll().toArray(), quoteService.findAllQuotes().toArray());
  }

  @Test
  public void testSaveAllQuotes() {
    List<Quote> quotes = new ArrayList<>();
    Quote newQuote = new Quote();
    newQuote.setId("yhoo");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);
    Quote anotherNewQuote = new Quote();
    anotherNewQuote.setId("googl");
    anotherNewQuote.setAskPrice(11d);
    anotherNewQuote.setAskSize(11L);
    anotherNewQuote.setBidPrice(11.2d);
    anotherNewQuote.setBidSize(11L);
    anotherNewQuote.setLastPrice(11.1d);

    //all new quotes
    quotes.add(newQuote);
    quotes.add(anotherNewQuote);

    expectedException.expect(ResourceNotFoundException.class);
    expectedException.expectMessage("Ticker not found");
    quoteService.saveAllQuotes(quotes);

    newQuote.setId("aapl");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);
    anotherNewQuote.setId("amzn");
    anotherNewQuote.setAskPrice(11d);
    anotherNewQuote.setAskSize(11L);
    anotherNewQuote.setBidPrice(11.2d);
    anotherNewQuote.setBidSize(11L);
    anotherNewQuote.setLastPrice(11.1d);

    //all old quotes
    assertEquals(quotes, quoteService.saveAllQuotes(quotes));
  }

  @Test
  public void testSaveQuoteGivenQuote() {
    //new quote (add)
    Quote newQuote = new Quote();
    newQuote.setId("yhoo");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);

    assertEquals(newQuote, quoteService.saveQuote(newQuote));
    assertEquals(3, quoteDao.count());

    //old quote (update)
    newQuote.setId("aapl");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);

    assertNotEquals(firstSavedQuote, quoteService.saveQuote(newQuote));
    assertEquals(3, quoteDao.count());
  }

  @Test
  public void testSaveQuoteGivenTicker() {
    assertEquals("GOOGL", quoteService.saveQuote("googl").getTicker());
    assertEquals(3, quoteDao.count());
  }

  @Test
  public void testFindIexQuoteByTicker() {
    //symbol found in IEX
    assertEquals("GOOGL", quoteService.findIexQuoteByTicker("googl").getSymbol());

    //symbol not found in IEX
    expectedException.expect(IllegalArgumentException.class);
    assertEquals("YHOO", quoteService.findIexQuoteByTicker("yhoo").getSymbol());
  }
}