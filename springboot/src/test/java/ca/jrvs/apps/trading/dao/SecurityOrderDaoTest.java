package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"classpath:schema.sql"})
@SpringBootTest(classes = {TestConfig.class})
public class SecurityOrderDaoTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder securityOrder;

  private Account account;

  private Trader trader;

  private Quote quote;

  @Before
  public void insertOne() {
    quote = new Quote();
    quote.setTicker("AAPL");
    quote.setAskPrice(26.3);
    quote.setAskSize(52);
    quote.setBidPrice(55.6);
    quote.setBidSize(12);
    quote.setLastPrice(26.6);
    quoteDao.save(quote);

    traderDao.deleteAll();
    trader = new Trader();
    trader.setFirst_name("firstName");
    trader.setLast_name("lastName");
    trader.setCountry("Canada");
    trader.setDob(new Date());
    trader.setEmail("trader@mail.com");
    traderDao.save(trader);

    accountDao.deleteAll();
    account = new Account();
    account.setAmount(100.01);
    account.setTrader_id(trader.getId());
    accountDao.save(account);

    securityOrderDao.deleteAll();
    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(account.getId());
    securityOrder.setNotes("abcd");
    securityOrder.setPrice(22.33);
    securityOrder.setSize(2);
    securityOrder.setPrice(33.22);
    securityOrder.setStatus("accepted");
    securityOrder.setTicker(quote.getTicker());
    securityOrderDao.save(securityOrder);
  }

  @Test
  public void updateOne() {
    Double price = 20.90;
    Integer size = 6;
    securityOrder.setPrice(price);
    securityOrder.setSize(size);

    int changeNum = securityOrderDao.updateOne(securityOrder);

    assertEquals(1, changeNum);
  }

  @Test
  public void findAllById() {
    List<Integer> ids= Arrays.asList(securityOrder.getId());
    Iterable<SecurityOrder> savedSecurityOrders =securityOrderDao.findAllById(ids);
    List<SecurityOrder> orders = Lists.newArrayList(savedSecurityOrders);
    assertEquals(1, orders.size());
    assertEquals(securityOrder.getPrice(), orders.get(0).getPrice());
    assertEquals(securityOrder.getSize(), orders.get(0).getSize());

  }

  @Test
  public void saveAll() {
    List<SecurityOrder> orders = new ArrayList<SecurityOrder>();
    SecurityOrder securityOrder1 = new SecurityOrder();
    securityOrder1.setSize(23);
    securityOrder1.setPrice(26.3);
    securityOrder1.setTicker(quote.getTicker());
    securityOrder1.setNotes("This is my note");
    securityOrder1.setAccountId(account.getId());
    securityOrder1.setStatus("Pending");

    SecurityOrder securityOrder2 = new SecurityOrder();
    securityOrder2.setSize(12);
    securityOrder2.setPrice(28.4);
    securityOrder2.setTicker(quote.getTicker());
    securityOrder2.setNotes("This is my note");
    securityOrder2.setAccountId(account.getId());
    securityOrder2.setStatus("Completed");

    orders.add(securityOrder1);
    orders.add(securityOrder2);
    Iterable<SecurityOrder> savedOrders = securityOrderDao.saveAll(orders);

    assertNotNull(savedOrders);
    assertEquals(3, securityOrderDao.count());

    securityOrderDao.deleteById(securityOrder1.getId());
    securityOrderDao.deleteById(securityOrder2.getId());
  }

  @Test
  public void findById() {
    SecurityOrder savedSecurityOrder = securityOrderDao.findById(securityOrder.getId()).get();
    assertEquals(securityOrder.getPrice(), savedSecurityOrder.getPrice());
    assertEquals(securityOrder.getSize(), savedSecurityOrder.getSize());
    assertEquals(securityOrder.getNotes(), savedSecurityOrder.getNotes());
  }

  @Test
  public void existsById() {
    assertTrue(securityOrderDao.existsById(securityOrder.getId()));
  }

  @Test
  public void findAll() {
    Iterable<SecurityOrder> orders = securityOrderDao.findAll();
    int count = 0;
    for (SecurityOrder securityOrder: orders) {
      count++;
    }
    assertEquals(1, count);
  }

  @Test
  public void count() {
    assertEquals(1, securityOrderDao.count());
  }


  @Test
  public void deleteAll() {
    securityOrderDao.deleteAll();
    assertEquals(0, securityOrderDao.count());
  }

  @After
  public void deleteOne() {
    securityOrderDao.deleteById(securityOrder.getId());
    accountDao.deleteById(account.getId());
    traderDao.deleteById(trader.getId());
    quoteDao.deleteById(quote.getTicker());
  }
}