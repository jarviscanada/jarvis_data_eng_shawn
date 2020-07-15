package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final String TABLE_NAME = "position";
  private static final String ID_COLUMN_NAME = "account_id";
  private static final String TICKER_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate((dataSource));
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public String getTableName() {
    return TABLE_NAME;
  }

  public String getIdColumnName() {
    return ID_COLUMN_NAME;
  }

  public String getTickerColumnName() {
    return TICKER_COLUMN_NAME;
  }

  Class<Position> getEntityClass() {
    return Position.class;
  }

  /**
   * Checks if position of an account exists in position table
   *
   * @param accountId to be searched
   * @return true if found, false otherwise
   */
  public boolean existsById(Integer accountId) {
    if (accountId == null) {
      return false;
    }
    String countSql =
        "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
    long count = getJdbcTemplate().queryForObject(countSql, Long.class, accountId);
    return count != 0L;
  }

  /**
   * Checks if position of a ticker exists in position table
   *
   * @param ticker to be searched
   * @return true if found, false otherwise
   */
  public boolean existsByTicker(String ticker) {
    if (ticker == null) {
      return false;
    }
    String countSql =
        "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + getTickerColumnName() + " = ?";
    long count = getJdbcTemplate().queryForObject(countSql, Long.class, ticker);
    return count != 0L;
  }

  /**
   * Finds all positions of provided account and ticker in position table
   *
   * @param accountId account id to be found
   * @param ticker    ticker to be found
   * @return list of found positions
   */
  public List<Position> findByIdAndTicker(Integer accountId, String ticker) {
    return getJdbcTemplate()
        .query("SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ? AND "
                + getTickerColumnName() + " = ?",
            BeanPropertyRowMapper.newInstance(getEntityClass()), accountId, ticker);
  }

  /**
   * Finds all positions in position table
   *
   * @return list of all positions in position table
   */
  public List<Position> findAll() {
    return getJdbcTemplate()
        .query("SELECT * FROM " + getTableName(),
            BeanPropertyRowMapper.newInstance(getEntityClass()));
  }

  /**
   * Finds all positions of provided accounts in position table
   *
   * @param accountIds list of account ids to be found
   * @return list of found positions
   */
  public List<Position> findAllById(List<Integer> accountIds) {
    List<Position> allPositions = new ArrayList<>();
    for (Integer id : accountIds) {
      List<Position> positionsOfCurrentId = getJdbcTemplate()
          .query("SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?",
              BeanPropertyRowMapper.newInstance(getEntityClass()), id);
      allPositions.addAll(positionsOfCurrentId);
    }
    return allPositions;
  }

  /**
   * Finds all positions of provided tickers in position table
   *
   * @param tickers list of tickers to be found
   * @return list of found positions
   */
  public List<Position> findAllByTickers(List<String> tickers) {
    List<Position> allPositions = new ArrayList<>();
    for (String ticker : tickers) {
      List<Position> positionOfCurrentTicker = getJdbcTemplate()
          .query("SELECT * FROM " + getTableName() + " WHERE " + getTickerColumnName() + " = ?",
              BeanPropertyRowMapper.newInstance(getEntityClass()), ticker);
      allPositions.addAll(positionOfCurrentTicker);
    }
    return allPositions;
  }

  /**
   * Counts total number of positions in position table
   *
   * @return count of positions in position table
   */
  public long count() {
    String countSql = "SELECT COUNT(*) FROM " + getTableName();
    return getJdbcTemplate().queryForObject(countSql, Long.class);
  }
}