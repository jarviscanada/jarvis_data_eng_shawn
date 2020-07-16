package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Trader firstTrader = new Trader();
  private Trader secondTrader = new Trader();
  private Account firstAccount = new Account();
  private Account secondAccount = new Account();

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
  }

  @After
  public void remove() {
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void testSave() {
    //new account
    Account newAccount = new Account();
    newAccount.setTraderId(2);
    newAccount.setAmount(1200.5);

    assertEquals(newAccount, accountDao.save(newAccount));

    //old account
    newAccount.setId(1);
    newAccount.setTraderId(2);
    newAccount.setAmount(5000.0);

    assertEquals(newAccount, accountDao.save(newAccount));
  }

  @Test
  public void testFindById() {
    //found
    assertEquals(Optional.of(firstAccount), accountDao.findById(1));
    //not found
    assertEquals(Optional.empty(), accountDao.findById(3));
  }

  @Test
  public void testExistsById() {
    //found
    assertTrue(accountDao.existsById(1));
    //not found
    assertFalse(accountDao.existsById(6));
  }

  @Test
  public void testFindAll() {
    Account[] expectedAccounts = new Account[]{firstAccount, secondAccount};
    Account[] actualAccounts = new Account[2];
    accountDao.findAll().toArray(actualAccounts);

    assertArrayEquals(expectedAccounts, actualAccounts);
  }

  @Test
  public void testFindAllById() {
    Account[] expectedAccounts = new Account[]{firstAccount, secondAccount};
    Account[] actualAccounts = new Account[2];
    Lists.newArrayList(accountDao.findAllById(Arrays.asList(1, 2))).toArray(actualAccounts);

    assertArrayEquals(expectedAccounts, actualAccounts);
  }

  @Test
  public void testCount() {
    assertEquals(2, accountDao.count());
  }

  @Test
  public void testDeleteById() {
    accountDao.deleteById(1);
    Account[] expectedAccounts = new Account[]{secondAccount};
    Account[] actualAccounts = new Account[1];
    accountDao.findAll().toArray(actualAccounts);

    assertArrayEquals(expectedAccounts, actualAccounts);
  }

  @Test
  public void testDeleteAll() {
    accountDao.deleteAll();

    assertEquals(0, accountDao.count());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteAllInIterableMethod() {
    accountDao.deleteAll(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowUnsupportedOperationsForDeleteIexQuoteMethod() {
    accountDao.delete(new Account());
  }

  @Test
  public void saveAll() {
    List<Account> accounts = new ArrayList<>();

    Account newAccount = new Account();
    newAccount.setId(2);
    newAccount.setTraderId(2);
    newAccount.setAmount(1200.5);
    Account anotherNewAccount = new Account();
    anotherNewAccount.setId(1);
    anotherNewAccount.setTraderId(1);
    anotherNewAccount.setAmount(1200.5);

    accounts.add(newAccount);
    accounts.add(anotherNewAccount);

    //all old accounts
    assertEquals(accounts, accountDao.saveAll(accounts));
  }
}