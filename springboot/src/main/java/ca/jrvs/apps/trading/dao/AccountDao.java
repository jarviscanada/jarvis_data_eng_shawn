package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Entity;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {
  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";
  private final String TRADER_ID_COLUMN = "trader_id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource){
    setJdbcTemplate(new JdbcTemplate(dataSource));
    setSimpleJdbcInsert(new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN));
  }

  private void setSimpleJdbcInsert(SimpleJdbcInsert usingGeneratedKeyColumns) {
    this.simpleJdbcInsert = simpleJdbcInsert;
  }

  private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return null;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return null;
  }

  @Override
  public String getTableName() {
    return null;
  }

  @Override
  public String getIdColumnName() {
    return null;
  }

  @Override
  Class<Account> getEntityClass() {
    return null;
  }

  @Override
  public int updateOne(Account entity) {
    return 0;
  }
}
