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
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      QuoteDao quoteDao, PositionDao positionDao){
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Execute a market order
   * - validate the order (size and ticker)
   * - Create a securityOrder (for security_order table)
   * - Handle buy or sell order
   * - save and return security order
   * @param orderDto
   * @return SecurityOrder
   * @throws org.springframework.dao.DataAccessException if unable to get data from dao
   * @throws IllegalArgumentException for invalid input
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto){
    Integer securityOrderId;
    String ticker = orderDto.getTicker().toUpperCase();
    Integer accountId = orderDto.getAccountId();

    Quote quote = quoteDao.findById(ticker).get();
    Account account = accountDao.findById(accountId).get();

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setSize(orderDto.getSize());
    securityOrder.setStatus("CREATED");
    securityOrder.setTicker(orderDto.getTicker());
    securityOrder.setAccountId(accountId);

    if (orderDto.getSize() > 0){
      if (orderDto.getSize() > quote.getAskSize()){
        throw new IllegalArgumentException("Size for this order is too large");
      }
      securityOrder.setPrice(quote.getAskPrice());
      handleBuyMarketOrder(orderDto, securityOrder, account);
      quote.setAskSize(quote.getAskSize() - orderDto.getSize());
    } else if (orderDto.getSize() < 0){
      if (orderDto.getSize() > quote.getBidSize()){
        throw new IllegalArgumentException("Size for this order is too large");
      }
      securityOrder.setPrice(quote.getBidPrice());
      handleSellMarketOrder(orderDto, securityOrder, account);
      quote.setBidSize(quote.getBidSize() - orderDto.getSize());
    } else {
      throw new IllegalArgumentException("Size of the order should not be 0.");
    }
    securityOrder.setStatus("FILLED");
    return securityOrder;
  }

  /**
   * Execute a buy order
   * - check account balance
   * @param marketOrderDto
   * @param securityOrder
   * @param account
   * @return securityOrder id generated
   */
  protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account){
    Double cost = securityOrder.getPrice() * securityOrder.getSize();
    if (cost > account.getAmount()){
      throw new IllegalArgumentException("Insufficient fund in the target account");
    }
    account.setAmount(account.getAmount() - cost);
    securityOrder.setStatus("FILLED");
    accountDao.save(account);
    securityOrder.setId(securityOrderDao.save(securityOrder).getId());
  }

  /**
   * Execute a sell order
   * - check position owned for the ticker
   * @param marketOrderDto
   * @param securityOrder
   * @param account
   * @return securityOrder id generated
   */
  protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account){
    Position position = null;
    List<Position> positions = positionDao.findRowByColumnId(account.getId(), "account_id");
    if (positions == null){
      throw new IllegalArgumentException("The order size is larger than the security position.");
    }
    boolean exist = false;
    for (Position p : positions){
      if (p.getTicker() == marketOrderDto.getTicker().toUpperCase()){
        exist = true;
        if (p.getPosition() < marketOrderDto.getSize()){
          throw new IllegalArgumentException("The order size is larger than the security position.");
        }
      }
    }
    if (!exist){
      throw new IllegalArgumentException("You do not own position for security with ticker: " + marketOrderDto.getTicker());
    }
    Double sale = securityOrder.getPrice() * securityOrder.getSize() * -1; //since the size is negative
    account.setAmount(account.getAmount() + sale);
    securityOrder.setStatus("FILLED");
    accountDao.save(account);
    securityOrder.setId(securityOrderDao.save(securityOrder).getId());
  }
}
