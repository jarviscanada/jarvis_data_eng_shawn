package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestConfig.class})
@Sql({"classpath:schema.sql"})

public class QuoteDaoIntTest {

  @Autowired
  private  QuoteDao quoteDao;
  private Quote savedQuote1;
  private Quote savedQuote2;

  @Before
  public void insertTwo() {
    savedQuote1 = new Quote();
    savedQuote1.setAskPrice(5d);
    savedQuote1.setAskSize(5);
    savedQuote1.setBidPrice(5.9d);
    savedQuote1.setBidSize(5);
    savedQuote1.setTicker("MMM");
    savedQuote1.setLastPrice(5.5d);
    quoteDao.save(savedQuote1);

    savedQuote2 = new Quote();
    savedQuote2.setAskPrice(4d);
    savedQuote2.setAskSize(4);
    savedQuote2.setBidPrice(4.9d);
    savedQuote2.setBidSize(4);
    savedQuote2.setTicker("AAPL");
    savedQuote2.setLastPrice(4.5d);
    quoteDao.save(savedQuote2);
  }


  @Test
  public void findById() {
    String ticker = savedQuote1.getTicker();
    Optional<Quote> quote = quoteDao.findById(ticker);
    assertEquals(quote.get().getAskPrice(), savedQuote1.getAskPrice());
    assertEquals(quote.get().getAskSize(), savedQuote1.getAskSize());
    assertEquals(quote.get().getBidPrice(), savedQuote1.getBidPrice());
    assertEquals(quote.get().getBidSize(), savedQuote1.getBidSize());
    assertEquals(quote.get().getTicker(), savedQuote1.getTicker());
    assertEquals(quote.get().getLastPrice(), savedQuote1.getLastPrice());
  }

  @Test
  public void existsById() {
    assertTrue(quoteDao.existsById(savedQuote1.getTicker()));
    assertFalse(quoteDao.existsById("asasas"));
  }

  @Test
  public void findAll() {
    List<Quote> quotes = (List<Quote>) quoteDao.findAll();
    assertEquals(quotes.get(0).getAskPrice(),savedQuote1.getAskPrice());
    assertEquals(quotes.get(0).getAskSize(), savedQuote1.getAskSize());
    assertEquals(quotes.get(0).getBidPrice(),savedQuote1.getBidPrice(),0.001);
    assertEquals(quotes.get(0).getBidSize(), savedQuote1.getBidSize());
    assertEquals(quotes.get(0).getTicker(), savedQuote1.getTicker());
    assertEquals(quotes.get(0).getLastPrice(), savedQuote1.getLastPrice());
  }

  @Test
  public void count(){
    assertEquals(2,quoteDao.count());
  }


  @After
  public void delete()  {
    quoteDao.deleteById(savedQuote1.getTicker());
    assertEquals(1, quoteDao.count());
    quoteDao.deleteById(savedQuote2.getTicker());
    assertEquals(0,quoteDao.count());
  }

}