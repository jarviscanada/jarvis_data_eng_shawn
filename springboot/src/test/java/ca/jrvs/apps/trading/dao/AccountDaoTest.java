package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
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
public class AccountDaoTest {
  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountDao accountDao;

  private Account account;
  private Trader trader;

  @Before
  public void setUp(){
    traderDao.deleteAll();
    trader.setFirst_name("John");
    trader.setLast_name("Smith");
    trader.setCountry("China");
    trader.setDob(new Date());
    trader.setEmail("johnsmith@mail.com");
    traderDao.save(trader);

    accountDao.deleteAll();
    account.setAmount(100.01);
    account.setTrader_id(trader.getId());
    accountDao.save(account);
  }

  @After
  public void delete(){
    accountDao.deleteById(account.getId());
    traderDao.deleteById(trader.getId());
  }

  @Test
  public void findAll() {
    Iterable<Account> accounts = accountDao.findAll();
    int count = 0;
    for (Account account: accounts) {
      count++;
    }
    assertEquals(1, count);
  }

  @Test
  public void count(){
    assertEquals(1,accountDao.count());
  }

  @Test
  public void updateOne() {
    Double amount = 100.01;
    Integer traderId = 2;
    account.setAmount(amount);
    account.setId(traderId);

    int changes = accountDao.updateOne(account);

    assertEquals(1,changes);
  }

  @Test
  public void existById() {
    List<Account> accounts = Lists.newArrayList(accountDao.findAll());
    assertTrue(accountDao.existsById(account.getId()));
  }

  @Test
  public void findAccountByTraderID() {
    Account savedAccount = accountDao.findAccountByTraderID(account.getId()).get();
    assertEquals(account.getId(),savedAccount.getId());
    assertEquals(account.getAmount(),savedAccount.getAmount());
  }

  @Test
  public void deleteAll() {
    accountDao.deleteAll();
    assertEquals(0, accountDao.count());
  }

}