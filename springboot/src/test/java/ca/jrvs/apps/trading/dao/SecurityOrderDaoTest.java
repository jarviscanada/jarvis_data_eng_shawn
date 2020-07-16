package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder firstSecurityOrder = new SecurityOrder();
  private SecurityOrder secondSecurityOrder = new SecurityOrder();
  private Trader firstTrader = new Trader();
  private Trader secondTrader = new Trader();
  private Account firstAccount = new Account();
  private Account secondAccount = new Account();
  private Quote firstSavedQuote = new Quote();
  private Quote secondSavedQuote = new Quote();

  @Before
  public void insert() {
    firstTrader.setFirstName("John");
    firstTrader.setLastName("Doe");
    firstTrader.setCountry("Canada");
    firstTrader.setEmail("john.doe@gmail.com");
    firstTrader.setDob(LocalDate.of(2000, 12, 12));
    traderDao.save(firstTrader);
    secondTrader.setFirstName("Jane");
    secondTrader.setLastName("Smith");
    secondTrader.setCountry("Canada");
    secondTrader.setEmail("jane.smith@gmail.com");
    secondTrader.setDob(LocalDate.of(2000, 1, 1));
    traderDao.save(secondTrader);

    firstAccount.setTraderId(1);
    firstAccount.setAmount(1000.0);
    accountDao.save(firstAccount);
    secondAccount.setTraderId(2);
    secondAccount.setAmount(1100.32);
    accountDao.save(secondAccount);

    firstSavedQuote.setAskPrice(10d);
    firstSavedQuote.setAskSize(10L);
    firstSavedQuote.setBidPrice(10.2d);
    firstSavedQuote.setBidSize(10L);
    firstSavedQuote.setId("aapl");
    firstSavedQuote.setLastPrice(10.1d);
    quoteDao.save(firstSavedQuote);
    secondSavedQuote.setAskPrice(11d);
    secondSavedQuote.setAskSize(11L);
    secondSavedQuote.setBidPrice(11.2d);
    secondSavedQuote.setBidSize(11L);
    secondSavedQuote.setId("amzn");
    secondSavedQuote.setLastPrice(11.1d);
    quoteDao.save(secondSavedQuote);

    firstSecurityOrder.setAccountId(1);
    firstSecurityOrder.setTicker("aapl");
    firstSecurityOrder.setStatus("FILLED");
    firstSecurityOrder.setSize(4);
    firstSecurityOrder.setPrice(35.23);
    firstSecurityOrder.setNotes("Some notes");
    securityOrderDao.save(firstSecurityOrder);
    secondSecurityOrder.setAccountId(2);
    secondSecurityOrder.setTicker("amzn");
    secondSecurityOrder.setStatus("FILLED");
    secondSecurityOrder.setSize(6);
    secondSecurityOrder.setPrice(46.23);
    secondSecurityOrder.setNotes("Some other notes");
    securityOrderDao.save(secondSecurityOrder);
  }

  @After
  public void remove() {
    securityOrderDao.deleteById(firstSecurityOrder.getId());
    securityOrderDao.deleteById(secondSecurityOrder.getId());
  }

  @Test
  public void save() {
    //new security order
    SecurityOrder newSecurityOrder = new SecurityOrder();
    newSecurityOrder.setAccountId(2);
    newSecurityOrder.setTicker("aapl");
    newSecurityOrder.setStatus("FILLED");
    newSecurityOrder.setSize(6);
    newSecurityOrder.setPrice(46.23);
    newSecurityOrder.setNotes("Some other notes");

    assertEquals(newSecurityOrder, securityOrderDao.save(newSecurityOrder));

    //old account
    newSecurityOrder.setId(1);
    newSecurityOrder.setAccountId(2);
    newSecurityOrder.setTicker("aapl");
    newSecurityOrder.setStatus("FILLED");
    newSecurityOrder.setSize(6);
    newSecurityOrder.setPrice(46.23);
    newSecurityOrder.setNotes("Some other notes");

    assertEquals(newSecurityOrder, securityOrderDao.save(newSecurityOrder));
  }

  @Test
  public void findById() {
    //found
    assertEquals(Optional.of(firstSecurityOrder), securityOrderDao.findById(1));
    //not found
    assertEquals(Optional.empty(), securityOrderDao.findById(3));
  }

  @Test
  public void existsById() {
    //found
    assertTrue(securityOrderDao.existsById(1));
    //not found
    assertFalse(securityOrderDao.existsById(6));
  }

  @Test
  public void findAll() {
    SecurityOrder[] expectedSecurityOrders = new SecurityOrder[]{firstSecurityOrder,
        secondSecurityOrder};
    SecurityOrder[] actualSecurityOrders = new SecurityOrder[2];
    securityOrderDao.findAll().toArray(actualSecurityOrders);

    assertArrayEquals(expectedSecurityOrders, actualSecurityOrders);
  }

  @Test
  public void findAllById() {
    SecurityOrder[] expectedSecurityOrders = new SecurityOrder[]{firstSecurityOrder,
        secondSecurityOrder};
    SecurityOrder[] actualSecurityOrders = new SecurityOrder[2];
    Lists.newArrayList(securityOrderDao.findAllById(Arrays.asList(1, 2)))
        .toArray(actualSecurityOrders);

    assertArrayEquals(expectedSecurityOrders, actualSecurityOrders);
  }

  @Test
  public void count() {
    assertEquals(2, securityOrderDao.count());
  }

  @Test
  public void deleteById() {
    securityOrderDao.deleteById(1);
    SecurityOrder[] expectedSecurityOrders = new SecurityOrder[]{secondSecurityOrder};
    SecurityOrder[] actualSecurityOrders = new SecurityOrder[1];
    securityOrderDao.findAll().toArray(actualSecurityOrders);

    assertArrayEquals(expectedSecurityOrders, actualSecurityOrders);
  }

  @Test
  public void deleteAll() {
    securityOrderDao.deleteAll();

    assertEquals(0, securityOrderDao.count());
  }

  @Test
  public void deleteAllByAccountId() {
    securityOrderDao.deleteAllByAccountId(1);
    SecurityOrder[] expectedSecurityOrders = new SecurityOrder[]{secondSecurityOrder};
    SecurityOrder[] actualSecurityOrders = new SecurityOrder[1];
    securityOrderDao.findAll().toArray(actualSecurityOrders);

    assertArrayEquals(expectedSecurityOrders, actualSecurityOrders);
  }

  @Test
  public void saveAll() {
    List<SecurityOrder> securityOrders = new ArrayList<>();

    SecurityOrder newSecurityOrder = new SecurityOrder();
    newSecurityOrder.setAccountId(2);
    newSecurityOrder.setTicker("aapl");
    newSecurityOrder.setStatus("FILLED");
    newSecurityOrder.setSize(6);
    newSecurityOrder.setPrice(46.23);
    newSecurityOrder.setNotes("Some other notes");
    SecurityOrder anotherNewSecurityOrder = new SecurityOrder();
    anotherNewSecurityOrder.setAccountId(2);
    anotherNewSecurityOrder.setTicker("aapl");
    anotherNewSecurityOrder.setStatus("FILLED");
    anotherNewSecurityOrder.setSize(6);
    anotherNewSecurityOrder.setPrice(46.23);
    anotherNewSecurityOrder.setNotes("Some other notes");

    //all new security orders
    securityOrders.add(newSecurityOrder);
    securityOrders.add(anotherNewSecurityOrder);

    expectedException.expect(ResourceNotFoundException.class);
    expectedException.expectMessage("Security order not found");
    securityOrderDao.saveAll(securityOrders);

    newSecurityOrder.setId(1);
    anotherNewSecurityOrder.setId(2);

    //all old security orders
    assertEquals(securityOrders, securityOrderDao.saveAll(securityOrders));
  }
}