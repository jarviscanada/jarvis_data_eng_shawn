package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
      QuoteDao quoteDao, PositionDao positionDao) {
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Execute a market order
   * - validate the order (e.g. size and ticker)
   * - create a securityOrder (for security_order table)
   * - handle buy or sell order
   * - buy order : check account balance (calls helper method)
   * - sell order: check position for the ticker/symbol (calls helper method)
   * - update securityOrder.status
   * - save and return securityOrder
   *
   * @param orderDto market order
   * @return SecurityOrder from security_order table
   * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException                    for invalid input
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
    if (orderDto.getTicker() == null || !quoteDao.existsById(orderDto.getTicker())) {
      throw new IllegalArgumentException("Ticker does not exist :" + orderDto.getTicker());
    }
    if (orderDto.getSize() == 0) {
      throw new IllegalArgumentException("Order size cannot be zero");
    }
    Quote quote = quoteDao.findById(orderDto.getTicker()).get();
    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setAccountId(orderDto.getAccountId());
    securityOrder.setStatus("PENDING");
    securityOrder.setTicker(orderDto.getTicker());
    securityOrder.setSize(orderDto.getSize());
    if (orderDto.getSize() > 0) {
      securityOrder.setPrice(quote.getAskPrice());
      handleBuyMarketOrder(orderDto, securityOrder,
          accountDao.findById(orderDto.getAccountId()).get());
    } else {
      securityOrder.setPrice(quote.getBidPrice());
      handleSellMarketOrder(orderDto, securityOrder,
          accountDao.findById(orderDto.getAccountId()).get());
    }
    return securityOrderDao.save(securityOrder);
  }

  /**
   * Helper method that executes a buy order
   *
   * @param marketOrderDto user order
   * @param securityOrder  to be saved in database
   * @param account        account
   */
  protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account) {
    Double totalPrize = securityOrder.getPrice() * securityOrder.getSize();
    if (account.getAmount() >= totalPrize) {
      account.setAmount(account.getAmount() - totalPrize);
      accountDao.save(account);
      securityOrder.setStatus("FILLED");
      securityOrder
          .setNotes(securityOrder.getSize() + " security(s) bought at " + totalPrize + ".");
    } else {
      securityOrder.setStatus("CANCELLED");
      securityOrder.setNotes("Insufficient balance.");
    }
  }

  /**
   * Helper method that executes a sell order
   *
   * @param marketOrderDto user order
   * @param securityOrder  to be saved in database
   * @param account        account
   */
  protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account) {
    List<Position> positions = positionDao
        .findByIdAndTicker(securityOrder.getAccountId(), securityOrder.getTicker());
    if (positions != null && positions.size() > 0) {
      Position position = positions.get(0);
      if (position.getPosition() > securityOrder.getSize()) {
        Double totalPrize = securityOrder.getPrice() * securityOrder.getSize() * (-1);
        account.setAmount(account.getAmount() + totalPrize);
        accountDao.save(account);
        securityOrder.setStatus("FILLED");
        securityOrder
            .setNotes(securityOrder.getSize() + " security(s) sold at " + totalPrize + ".");
      } else {
        securityOrder.setStatus("CANCELLED");
        securityOrder.setNotes("Position size is smaller than order size.");
      }
    } else {
      securityOrder.setStatus("CANCELLED");
      securityOrder.setNotes("No open positions for ticker+" + securityOrder.getTicker() + ".");
    }
  }
}