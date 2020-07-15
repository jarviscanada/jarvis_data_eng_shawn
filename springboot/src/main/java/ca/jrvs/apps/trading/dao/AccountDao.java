package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

  private static final String TABLE_NAME = "account";
  private static final String ID_COLUMN_NAME = "id";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate((dataSource));
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN_NAME);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN_NAME;
  }

  @Override
  Class<Account> getEntityClass() {
    return Account.class;
  }

  /**
   * Helper method that updates existing account
   *
   * @param account new account
   * @return row number of updated account
   */
  public int updateOne(Account account) {
    String updateSql = "UPDATE account SET trader_id=?, amount=? WHERE id=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(account));
  }

  /**
   * Helper method to generate update values (Object[]) from Account object for update SQL
   * statement
   *
   * @param account account to generate values from
   * @return Object[] containing the values of account
   */
  private Object[] makeUpdateValues(Account account) {
    Object[] values = new Object[]{account.getTraderId(), account.getAmount(), account.getId()};
    return values;
  }

  /**
   * Updates all the provided accounts in the account table
   *
   * @param accounts list of accounts
   * @return updated list of accounts
   */
  @Override
  public <S extends Account> Iterable<S> saveAll(Iterable<S> accounts) {
    String updateSql = "UPDATE account SET trader_id=?, amount=? WHERE id=?";
    List<Object[]> batch = new ArrayList<>();
    accounts.forEach(account -> {
      if (!existsById(account.getId())) {
        throw new ResourceNotFoundException("Account not found:" + account.getId());
      }
      batch.add(makeUpdateValues(account));
    });
    int[] rows = jdbcTemplate.batchUpdate(updateSql, batch);
    int totalRow = Arrays.stream(rows).sum();
    if (totalRow != batch.size()) {
      throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
    }
    return (Iterable<S>) findAll();
  }
}
