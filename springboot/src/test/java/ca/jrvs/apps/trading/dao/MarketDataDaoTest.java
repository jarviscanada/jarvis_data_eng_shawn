package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
public class MarketDataDaoTest {

  @Autowired
  private MarketDataDao dao;

  @Test
  public void shouldFindIexQuoteByTicker() {
    String ticker = "GOOGL";
    IexQuote iexQuote = dao.findById(ticker).get();

    assertEquals(ticker, iexQuote.getSymbol());
  }

  @Test
  public void shouldfindListOfIexQuotesByTickers() {
    List<IexQuote> quoteList = dao.findAllById(Arrays.asList("GOOGL", "AMZN"));

    assertEquals(2, quoteList.size());
    assertEquals("GOOGL", quoteList.get(0).getSymbol());

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWhenInvalidTicker() {
    List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "ASDFGHJkl"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForSaveMethod() {
    dao.save(new IexQuote());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForSaveAllMethod() {
    dao.saveAll(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForExistsByIdMethod() {
    dao.existsById("someString");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForFindAllMethod() {
    dao.findAll();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForCountMethod() {
    dao.count();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteByIdMethod() {
    dao.deleteById("someString");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteIexQuoteMethod() {
    dao.delete(new IexQuote());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteAllMethod() {
    dao.deleteAll();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteAllInIterableMethod() {
    dao.deleteAll(null);
  }


}