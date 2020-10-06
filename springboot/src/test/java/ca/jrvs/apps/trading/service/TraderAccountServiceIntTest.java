package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

  private TraderAccountView traderAccountView;
  @Autowired
  private TraderAccountService traderAccountService;
  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setup() {
    Trader savedTrader = new Trader();
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(1989, 12, 31));
    savedTrader.setEmail("Jarvis_2020@gmail.com");
    savedTrader.setFirstName("Zongpeng");
    savedTrader.setLastName("Yang");
    traderAccountView = traderAccountService.createTraderAndAccount(savedTrader);
    traderAccountService.deposit(traderAccountView.getTrader().getId(), 1000d);
  }

  @Test
  public void withdraw() {
    traderAccountService.withdraw(traderAccountView.getTrader().getId(), 500d);
    try {
      traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      Assert.fail();
    }
    traderAccountService.withdraw(traderAccountView.getTrader().getId(), 500d);
  }

  @Test
  public void quote() {
    Quote savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("SDG");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setAccountId(traderAccountView.getAccount().getId());
    securityOrder.setPrice(100d);
    securityOrder.setSize(10);
    securityOrder.setStatus("FILLED");
    securityOrder.setTicker(savedQuote.getTicker());
    securityOrder.setNotes("N/a");
    securityOrder.setId(traderAccountService.getSecurityOrderDao().save(securityOrder).getId());
    try {
      traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      Assert.fail();
    }
    securityOrder.setSize(-10);
    securityOrder.setId(null);
    traderAccountService.getSecurityOrderDao().save(securityOrder);
    traderAccountService.withdraw(traderAccountView.getTrader().getId(), 1000d);
  }

  @After
  public void delete() {
    traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
  }
}