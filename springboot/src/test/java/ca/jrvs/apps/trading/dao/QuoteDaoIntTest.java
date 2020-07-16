package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class QuoteDaoIntTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

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
    quoteDao.deleteById(firstSavedQuote.getId());
    quoteDao.deleteById(secondSavedQuote.getId());
  }

  @Test
  public void testSave() {
    //new ticker
    Quote newQuote = new Quote();
    newQuote.setId("yhoo");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);

    assertEquals(newQuote, quoteDao.save(newQuote));

    //old ticker
    newQuote.setId("aapl");
    newQuote.setAskPrice(11d);
    newQuote.setAskSize(11L);
    newQuote.setBidPrice(11.2d);
    newQuote.setBidSize(11L);
    newQuote.setLastPrice(11.1d);

    assertEquals(newQuote, quoteDao.save(newQuote));
  }

  @Test
  public void testSaveAll() {
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
    quoteDao.saveAll(quotes);

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
    assertEquals(quotes, quoteDao.saveAll(quotes));
  }

  @Test
  public void testFindById() {
    //found
    assertEquals(Optional.of(firstSavedQuote), quoteDao.findById("aapl"));
    //not found
    assertEquals(Optional.empty(), quoteDao.findById("googl"));
  }

  @Test
  public void testExistsById() {
    //found
    assertTrue(quoteDao.existsById("aapl"));
    //not found
    assertFalse(quoteDao.existsById("googl"));
  }

  @Test
  public void testFindAll() {
    Quote[] expectedQuotes = new Quote[]{firstSavedQuote, secondSavedQuote};
    Quote[] actualQuotes = new Quote[2];
    quoteDao.findAll().toArray(actualQuotes);

    assertArrayEquals(expectedQuotes, actualQuotes);
  }

  @Test
  public void testCount() {
    assertEquals(2, quoteDao.count());
  }

  @Test
  public void testDeleteById() {
    quoteDao.deleteById("aapl");
    Quote[] expectedQuotes = new Quote[]{secondSavedQuote};
    Quote[] actualQuotes = new Quote[1];
    quoteDao.findAll().toArray(actualQuotes);

    assertArrayEquals(expectedQuotes, actualQuotes);
  }

  @Test
  public void testDeleteAll() {
    quoteDao.deleteAll();

    assertEquals(0, quoteDao.count());
  }

}
