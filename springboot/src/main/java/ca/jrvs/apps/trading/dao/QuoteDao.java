package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation
   * might have changed the entity instance completely.
   *
   * @param quote must not be {@literal null}.
   * @return the saved entity will never be {@literal null}.
   */
  @Override
  public Quote save(Quote quote) {
    if (existsById(quote.getTicker())) {
      int updateRowNum = updateTicker(quote);
      if (updateRowNum != 1) {
        throw new DataRetrievalFailureException("fail to update quote");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int rowNum = simpleJdbcInsert.execute(parameterSource);
    if (rowNum != 1) {
      throw new IncorrectResultSizeDataAccessException("fail to insert", 1, rowNum);
    }
  }

  /**
   * Saves all given entities.
   *
   * @param quotes must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
    List<S> batch = new ArrayList<>();
    for (Quote q : quotes) {
      batch.add((S) save(q));
    }
    return batch;
  }


  /**
   * update quote information
   *
   * @param quote
   */
  private int updateTicker(Quote quote) {
    String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, "
        + "ask_price=?, ask_size=? WHERE ticker=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(quote));
  }

  /**
   *
   * @param quote
   * @return
   */
  private Object[] makeUpdateValues(Quote quote) {
    return new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
        quote.getAskPrice(),
        quote.getAskSize(), quote.getTicker()};
  }

  /**
   * Retrieves an entity by its id.
   *
   * @param ticker must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public Optional<Quote> findById(String ticker) {
    String selectSql = "SELECT * FROM" + TABLE_NAME + "WHERE ticker = '" + ticker + "'";
    List<Quote> quotes = jdbcTemplate
        .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    if (quotes.size() == 1) {
      Quote quoteOut = quotes.get(0);
      return Optional.ofNullable(quoteOut);
    } else {
      throw new DataAccessResourceFailureException("can not get the data");
    }
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param ticker must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public boolean existsById(String ticker) {
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE ticker = '" + ticker + "'";
    List<Quote> quotes = jdbcTemplate
        .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    if (quotes.size() == 1) {
      Quote outQuote = quotes.get(0);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Override
  public List<Quote> findAll() {
    String selectSql = "SELECT * FROM " + TABLE_NAME;
    List<Quote> quotes = jdbcTemplate
        .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    return quotes;
  }

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param strings
   * @return
   */
  @Override
  public Iterable<Quote> findAllById(Iterable<String> strings) {
    throw new UnsupportedOperationException("not implemented");
  }

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  @Override
  public long count() {
    long count = 0;
    String sql = "SELECT COUNT(*) FROM" + TABLE_NAME;
    return jdbcTemplate.queryForObject(sql, Long.class);
  }

  /**
   * Deletes the entity with the given id.
   *
   * @param ticker must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   */
  @Override
  public void deleteById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("not valid ticker");
    } else {
      String deletSql = "DELETE FROM" + TABLE_NAME + ID_COLUMN_NAME + "=?";
      jdbcTemplate.update(deletSql, ticker);
    }

  }

  /**
   * Deletes a given entity.
   *
   * @param entity
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public void delete(Quote entity) {
    throw new UnsupportedOperationException("not implemented");
  }

  /**
   * Deletes the given entities.
   *
   * @param entities
   * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
   */
  @Override
  public void deleteAll(Iterable<? extends Quote> entities) {
    throw new UnsupportedOperationException("not implemented");
  }

  /**
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {
    String deleteSql = "DELETE FROM" + TABLE_NAME;
    jdbcTemplate.update(deleteSql);
  }
}