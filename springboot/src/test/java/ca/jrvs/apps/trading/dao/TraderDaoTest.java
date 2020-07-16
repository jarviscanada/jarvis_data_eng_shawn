package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.time.LocalDate;
import java.util.Arrays;
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
@SpringBootTest(classes = TestConfig.class)
@Sql({"classpath:schema.sql"})
public class TraderDaoTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  @Autowired
  private TraderDao traderDao;
  private Trader firstTrader = new Trader();
  private Trader secondTrader = new Trader();

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
  }

  @After
  public void remove() {
    traderDao.deleteById(firstTrader.getId());
    traderDao.deleteById(secondTrader.getId());
  }

  @Test
  public void testSave() {
    //new trader
    Trader newTrader = new Trader();
    newTrader.setFirstName("Jane");
    newTrader.setLastName("Smith");
    newTrader.setCountry("Canada");
    newTrader.setEmail("jane.smith@gmail.com");
    newTrader.setDob(LocalDate.of(2000, 12, 12));

    assertEquals(newTrader, traderDao.save(newTrader));

    //old trader
    newTrader.setFirstName("Jane");
    newTrader.setLastName("Smith");
    newTrader.setCountry("Canada");
    newTrader.setEmail("jane.smith@gmail.com");
    newTrader.setDob(LocalDate.of(2000, 9, 9));

    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Not implemented");
    traderDao.save(newTrader);
  }

  @Test
  public void testFindById() {
    //found
    assertEquals(Optional.of(firstTrader), traderDao.findById(1));
    //not found
    assertEquals(Optional.empty(), traderDao.findById(3));
  }

  @Test
  public void testExistsById() {
    //found
    assertTrue(traderDao.existsById(1));
    //not found
    assertFalse(traderDao.existsById(3));
  }

  @Test
  public void testFindAll() {
    Trader[] expectedTraders = new Trader[]{firstTrader, secondTrader};
    Trader[] actualTraders = new Trader[2];
    traderDao.findAll().toArray(actualTraders);

    assertArrayEquals(expectedTraders, actualTraders);
  }

  @Test
  public void testFindAllById() {
    Trader[] expectedTraders = new Trader[]{firstTrader, secondTrader};
    Trader[] actualTraders = new Trader[2];
    Lists.newArrayList(traderDao.findAllById(Arrays.asList(1, 2))).toArray(actualTraders);

    assertArrayEquals(expectedTraders, actualTraders);
  }

  @Test
  public void testCount() {
    assertEquals(2, traderDao.count());
  }

  @Test
  public void testDeleteById() {
    traderDao.deleteById(1);
    Trader[] expectedTrader = new Trader[]{secondTrader};
    Trader[] actualTrader = new Trader[1];
    traderDao.findAll().toArray(actualTrader);

    assertArrayEquals(expectedTrader, actualTrader);
  }

  @Test
  public void testDeleteAll() {
    traderDao.deleteAll();

    assertEquals(0, traderDao.count());
  }

  @Test
  public void saveAll() {
    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Not implemented");
    traderDao.saveAll(null);

  }
}