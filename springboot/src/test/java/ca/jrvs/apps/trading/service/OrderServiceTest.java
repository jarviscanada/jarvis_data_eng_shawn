package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  @Mock
  private AccountDao accountDao;
  @Mock
  private SecurityOrderDao securityOrderDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;
  @InjectMocks
  private OrderService orderService;

  @Test
  public void executeMarketOrder() {
    MarketOrderDto marketOrderDto = new MarketOrderDto();
    marketOrderDto.setAccountId(1);
    marketOrderDto.setTicker("AAPL");
    marketOrderDto.setSize(2);

    Quote quote = new Quote();
    quote.setId("AAPL");
    quote.setAskPrice(35.0);
    quote.setBidPrice(30.0);
    quote.setLastPrice(33.0);
    quote.setTicker("AAPL");
    quote.setAskSize(3L);
    quote.setBidSize(4L);

    Account account = new Account();
    account.setId(1);
    account.setAmount(100.0);
    account.setTraderId(1);

    when(quoteDao.existsById(any())).thenReturn(true);
    when(quoteDao.findById(any())).thenReturn(java.util.Optional.of(quote));
    when(accountDao.findById(any())).thenReturn(java.util.Optional.of(account));

    SecurityOrder expected = new SecurityOrder();
    expected.setId(1);
    expected.setAccountId(1);
    expected.setStatus("FILLED");
    expected.setPrice(quote.getAskPrice());
    expected.setSize(2);
    expected.setTicker("AAPL");
    when(securityOrderDao.save(any()))
        .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    SecurityOrder actual = orderService.executeMarketOrder(marketOrderDto);
    assertEquals(expected.getStatus(), actual.getStatus());
    assertEquals(expected.getPrice(), actual.getPrice());
    assertEquals(expected.getSize(), actual.getSize());

    marketOrderDto.setSize(-2);
    Position position = new Position();
    position.setAccountId(1);
    position.setTicker("AAPL");
    position.setPosition(4);
    List<Position> positions = new ArrayList<>();
    positions.add(position);
    when(positionDao.findByIdAndTicker(any(), any())).thenReturn(positions);
    expected.setPrice(quote.getBidPrice());
    expected.setSize(-2);
    actual = orderService.executeMarketOrder(marketOrderDto);
    assertEquals(expected.getStatus(), actual.getStatus());
    assertEquals(expected.getPrice(), actual.getPrice());
    assertEquals(expected.getSize(), actual.getSize());
  }
}