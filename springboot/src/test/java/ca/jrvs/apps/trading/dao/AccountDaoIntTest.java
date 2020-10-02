package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
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
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Account account;
  private Trader savedTrader;

  @Before
  public void insertOne(){
    savedTrader = new Trader();
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(1989,12,31));
    savedTrader.setEmail("Jarvis_2020@gmail.com");
    savedTrader.setFirstName("Zongpeng");
    savedTrader.setLastName("Yang");
    savedTrader.setId(traderDao.save(savedTrader).getId());
    account = new Account();
    account.setTrader_id(savedTrader.getId());
    account.setAmount(1000d);
    account.setId(accountDao.save(account).getId());
  }

  @Test
  public void findAllById(){
    List<Account> accounts = Lists.newArrayList(
        accountDao.findAllById(
            Arrays.asList(account.getId())
        )
    );
    assertEquals(1, accounts.size());
    assertEquals(account.getAmount(), accounts.get(0).getAmount());
    assertEquals(account.getTrader_id(), accounts.get(0).getTrader_id());
  }

  @Test
  public void update(){
    account.setAmount(500d);
    Account accountOut = accountDao.save(account);
    assertEquals(account.getAmount(), accountOut.getAmount());
  }

  @After
  public void deleteOne(){
    accountDao.deleteById(account.getId());
    traderDao.deleteById(savedTrader.getId());
  }
}