package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Mock
  AccountDao accountDao;
  @Mock
  SecurityOrderDao securityOrderDao;
  @Mock
  QuoteDao quoteDao;
  @Mock
  PositionDao positionDao;

  @InjectMocks
  public OrderService orderService;

  private MarketOrderDto marketOrderDto;
  private Account account;
  private Quote quote;
  private Position position;
  @Before
  public void setUp() throws Exception {
    marketOrderDto = new MarketOrderDto();
    marketOrderDto.setTicker("AAPL");
    marketOrderDto.setAccountId(2);

    account = new Account();
    account.setId(2);
    account.setAmount(0d);

    quote = new Quote();
    quote.setTicker("AAPL");
    quote.setBidPrice(100d);
    quote.setBidSize(50);
    quote.setAskPrice(120d);
    quote.setAskSize(70);

    position = new Position();
    position.setPostion(0);
    position.setTicker("AAPL");
    position.setId(2);

    when(quoteDao.findById(anyString())).thenReturn(Optional.of(quote));
    when(accountDao.findById(anyInt())).thenReturn(Optional.of(account));
    when(positionDao.getByAccountIdAndTicker(anyInt(), anyString())).thenReturn(Optional.of(position));

  }

  @Test
  public void executeMarketOrder() {
    // Check for invalid order size
    marketOrderDto.setSize(0);
    try {
      orderService.executeMarketOrder(marketOrderDto);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Check for not enough Ammout
    marketOrderDto.setSize(10);
    SecurityOrder order = orderService.executeMarketOrder(marketOrderDto);
    assertEquals("Cancelled", order.getStatus());

    // Check buy order
    marketOrderDto.setSize(20);
    account.setAmount(2500d);
    order = orderService.executeMarketOrder(marketOrderDto);
    assertEquals("Filled", order.getStatus());


    // Check for position to sell
    marketOrderDto.setSize(-50);
    position.setPostion(10);
    order = orderService.executeMarketOrder(marketOrderDto);
    assertEquals("Cancelled", order.getStatus());

    // Check for sell order
    marketOrderDto.setSize(-100);
    position.setPostion(210);
    order = orderService.executeMarketOrder(marketOrderDto);
    assertEquals("Filled", order.getStatus());
  }
}