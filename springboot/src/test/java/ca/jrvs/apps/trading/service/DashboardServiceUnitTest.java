package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceUnitTest {

  @Mock
  private AccountDao accountDao;
  @Mock
  private TraderDao traderDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;

  @InjectMocks
  private DashboardService dashboardService;


  @Before
  public void setup(){
    Quote quoteExample = new Quote();
    quoteExample.setBidSize(20);
    quoteExample.setAskSize(20);
    quoteExample.setBidPrice(100d);
    quoteExample.setAskPrice(120d);
    quoteExample.setTicker("JRVS");
    quoteExample.setLastPrice(119d);
    when(quoteDao.findById("JRVS")).thenReturn(Optional.of(quoteExample));
    Quote quoteExample2 = new Quote();
    quoteExample2.setBidSize(22);
    quoteExample2.setAskSize(22);
    quoteExample2.setBidPrice(103d);
    quoteExample2.setAskPrice(124d);
    quoteExample2.setTicker("FB");
    quoteExample2.setLastPrice(156d);
    when(quoteDao.findById("FB")).thenReturn(Optional.of(quoteExample2));

    Account accountExample = new Account();
    accountExample.setAmount(1000d);
    accountExample.setId(10);
    accountExample.setTrader_id(5);
    List<Account> accounts = new ArrayList<>();
    accounts.add(accountExample);
    when(accountDao.findRowByColumnId(any(),any())).thenReturn(accounts);

    List<Position> positionList = new ArrayList<>();
    Position positionExample1 = new Position();
    positionExample1.setPosition(9);
    positionExample1.setTicker("JRVS");
    positionExample1.setAccountId(10);
    positionList.add(positionExample1);
    Position positionExample2 = new Position();
    positionExample2.setPosition(11);
    positionExample2.setTicker("FB");
    positionExample2.setAccountId(10);
    positionList.add(positionExample2);
    when(positionDao.findRowByColumnId(any(),any())).thenReturn(positionList);

    Trader traderExample = new Trader();
    traderExample.setCountry("Canada");
    traderExample.setDob(new Date(1989,12,31));
    traderExample.setEmail("Jarvis_2020@gmail.com");
    traderExample.setFirstName("Zongpeng");
    traderExample.setLastName("Yang");
    traderExample.setId(5);
    when(traderDao.findById(any())).thenReturn(Optional.of(traderExample));
  }

  @Test
  public void getTraderAccount() {
    TraderAccountView traderAccountView = dashboardService.getTraderAccount(5);
    assertEquals(traderAccountView.getAccount().getAmount(), (Double) 1000d);
    assertEquals(traderAccountView.getAccount().getId(), (Integer) 10);
    assertEquals(traderAccountView.getTrader().getCountry(), "Canada");
  }

  @Test
  public void getProfileViewByTraderId() {
    PortfolioView portfolioView = dashboardService.getProfileViewByTraderId(5);
    Quote quote1 = portfolioView.getSecurityRowList().get(0).getQuote();
    Quote quote2 = portfolioView.getSecurityRowList().get(1).getQuote();
    assertEquals("JRVS", quote1.getTicker());
    assertEquals("FB", quote2.getTicker());
    Position position1 = portfolioView.getSecurityRowList().get(0).getPosition();
    Position position2 = portfolioView.getSecurityRowList().get(1).getPosition();
    assertEquals((Integer) 9, position1.getPosition());
    assertEquals((Integer) 11, position2.getPosition());

  }
}