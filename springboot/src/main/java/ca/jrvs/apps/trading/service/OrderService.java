package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.Application;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,QuoteDao quoteDao, PositionDao positionDao){
    this.accountDao=accountDao;
    this.positionDao=positionDao;
    this.securityOrderDao=securityOrderDao;
    this.quoteDao=quoteDao;
  }

  /**
   * Execute a market order
   *
   * validate the order (e.g. size and ticker)
   * create a securityOrder ( for security_order table )
   * handle buy and sell order
   * nuy order : check account balance
   * sell order : check position for the ticker/ symbol
   * save and return
   * @param orderDto market order
   * @return SecurityOrder from security_order table
   * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid input
   */

  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto){
    orderDto.setTicker(orderDto.getTicker().toUpperCase());
    checkTicker(orderDto.getTicker());

    Quote quote=quoteDao.findById(orderDto.getTicker()).get();
    Account account = accountDao.findById(orderDto.getAccountId()).get();
    SecurityOrder order = new SecurityOrder();
    order.setAccountId(orderDto.getAccountId());
    order.setStatus("CREATE");
    order.setSize(orderDto.getSize());
    order.setTicker(orderDto.getTicker());

    if(orderDto.getSize()>0){
      order.setPrice(quote.getAskPrice());
      handleBuyMarketOrder(order,account);
    }else if(orderDto.getSize()<0){
      order.setPrice((quote.getBidPrice()));
      handleSellMarketOrder(orderDto,order,account);
    }else{
      throw new IllegalArgumentException("size is 0");
    }
    securityOrderDao.save(order);
    return order;
  }

  private void handleSellMarketOrder(MarketOrderDto orderDto, SecurityOrder order, Account account) {
    Optional<Position> optionalPosition = positionDao.getByAccountIdAndTicker(orderDto.getAccountId(),orderDto.getTicker());

    if(!optionalPosition.isPresent()){
      throw new IllegalArgumentException("no position size is able to sell");
    }

    Position position = optionalPosition.get();
    if(position.getPosition()<orderDto.getSize()*-1){
      order.setStatus("Cancelled");
      order.setNotes("size is not enough to complete the order");
    }else{
      Double totoalPrice = order.getPrice()*-orderDto.getSize();
      account.setAmount(account.getAmount()+totoalPrice);
      accountDao.save(account);
      order.setStatus("Filled");
    }
  }

  private void handleBuyMarketOrder(SecurityOrder order, Account account) {
    Double totalPrice =order.getPrice()*order.getSize();
    if(account.getAmount()-totalPrice>=0){
      account.setAmount(account.getAmount()-totalPrice);
      accountDao.save(account);
      order.setStatus("Filled");
    }else{
      order.setStatus("Cancelled");
      order.setNotes("Insufficient balance in the account");
    }
  }

  private void checkTicker(String ticker) {
    if (ticker == null || !ticker.matches("[A-Za-z]+")) {
      throw new IllegalArgumentException("Incorrect Ticker format");
    }
  }
}
