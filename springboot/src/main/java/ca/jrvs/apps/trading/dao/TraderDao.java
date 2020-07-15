package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader, Integer> {

  private static final String TABLE_NAME = "trader";
  private static final String ID_COLUMN_NAME = "id";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public TraderDao(DataSource dataSource) {
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
  Class<Trader> getEntityClass() {
    return Trader.class;
  }

  /**
   * Update all the provided entities in the entity table
   *
   * @param iterable list of entities
   * @return updated list of entities
   */
  @Override
  public Iterable saveAll(Iterable iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}