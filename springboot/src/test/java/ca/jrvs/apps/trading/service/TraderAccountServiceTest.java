package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
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
import org.springframework.web.servlet.tags.TransformTag;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceTest {

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountDao accountDao;

  private TraderAccountView traderAccountView;


  @Before
  public void setUp() {
    Trader trader = new Trader();
    trader.setEmail("test@mail.com");
    trader.setFirst_name("first");
    trader.setLast_name("last");
    trader.setDob(new Date());
    trader.setCountry("Canada");
    traderAccountView = traderAccountService.createTraderAndAccount(trader);
  }

  @After
  public void tearDown(){
    if(traderAccountView.getAccount().getAmount()!=0){
      Account account = traderAccountService.withdraw(traderAccountView.getTrader().getId(),traderAccountView.getAccount().getAmount());
      traderAccountView.setAccount(account);
    }

    traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
  }

  @Test
  public void createTraderAndAccount() {
    Trader trader = new Trader();
    try{
      traderAccountService.createTraderAndAccount(trader);
      fail();
    }catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }
    trader.setEmail("test1@mail.com");
    trader.setFirst_name("first1");
    trader.setLast_name("last1");
    trader.setDob(new Date());
    trader.setCountry("Canada");

    TraderAccountView view= traderAccountService.createTraderAndAccount(trader);

    assertEquals(trader.getEmail(),view.getTrader().getEmail());
    assertEquals(trader.getFirst_name(),view.getTrader().getFirst_name());
    assertEquals(trader.getLast_name(),view.getTrader().getLast_name());
    assertEquals(trader.getDob(),view.getTrader().getDob());
    assertEquals(trader.getCountry(),view.getTrader().getCountry());
  }

  @Test
  public void deleteTraderById() {
    try{
      traderAccountService.deleteTraderById(null);
      fail();
    }catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    traderAccountService.deposit(traderAccountView.getTrader().getId(),101.1d);

    try{
      traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    traderAccountService.withdraw(traderAccountView.getTrader().getId(),101.1d);
  }

  private void checkDepositAccount() {
    Trader trader = new Trader();
    trader.setEmail("test2@mail.com");
    trader.setFirst_name("first2");
    trader.setLast_name("last2");
    trader.setDob(new Date());
    trader.setCountry("Canada");
    trader = traderDao.save(trader);

    try {
      traderAccountService.deposit(trader.getId(), 102.1);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deposit() {
    try{
      traderAccountService.deposit(traderAccountView.getTrader().getId(),102.1);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    checkDepositAccount();
    Account account=traderAccountService.deposit(traderAccountView.getTrader().getId(),100d);
    traderAccountView.setAccount(account);

    assertEquals(new Double(100),account.getAmount());
  }

  private void checkInsufficient() {
    try {
      traderAccountService.withdraw(traderAccountView.getTrader().getId(), 110.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  private void checkWithdrawAccount() {
    Trader trader = new Trader();
    trader.setEmail("test2@mail.com");
    trader.setFirst_name("first2");
    trader.setLast_name("last2");
    trader.setDob(new Date());
    trader.setCountry("Canada");
    trader = traderDao.save(trader);

    try {
      traderAccountService.withdraw(trader.getId(), 10.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void withdraw() {
    try{
      traderAccountService.withdraw(null,10.0);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    try{
      traderAccountService.withdraw(traderAccountView.getTrader().getId(),-10.0);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    checkWithdrawAccount();
    Account account=traderAccountService.deposit(traderAccountView.getTrader().getId(),100d);
    traderAccountView.setAccount(account);
    checkInsufficient();
    account = traderAccountService.withdraw(traderAccountView.getTrader().getId(), 80d);
    traderAccountView.setAccount(account);

    assertEquals(new Double(20), account.getAmount());
  }

  @Test
  public void DeleteTraderById(){
    try{
      traderAccountService.deleteTraderById(null);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    traderAccountService.deposit(traderAccountView.getTrader().getId(),101.1);

    try{
      traderAccountService.deleteTraderById(traderAccountView.getTrader().getId());
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    traderAccountService.withdraw(traderAccountView.getTrader().getId(),101.1);
  }
}