package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends JdbcCrudDao<Quote, String> {//CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate((dataSource));
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
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
  Class<Quote> getEntityClass() {
    return Quote.class;
  }

  /**
   * If quote exists in database, update its values, else add new quote
   *
   * @param quote new quote
   * @return saved quote
   * @throws org.springframework.dao.DataAccessException for unexpected SQL result or SQL execution
   *                                                     failure
   */
  @Override
  public Quote save(Quote quote) {
    if (existsById(quote.getId())) {
      int updatedRowNo = updateOne(quote);
      if (updatedRowNo != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      this.addOne(quote);
    }
    return quote;
  }

  /**
   * helper method that saves new quote
   *
   * @param quote new quote
   */
  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if (row != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
    }
  }

  /**
   * Helper method that updates existing quote
   *
   * @param quote new quote
   * @return row number of updated quote
   */
  public int updateOne(Quote quote) {
    String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, "
        + "ask_size=? WHERE ticker=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(quote));
  }

  /**
   * helper method to generate update values (Object[]) from Quote object for update SQL statement
   *
   * @param quote quote to generate values from
   * @return Object[] containing the values of Quote
   */
  private Object[] makeUpdateValues(Quote quote) {
    Object[] values = new Object[6];
    values[0] = quote.getLastPrice();
    values[1] = quote.getBidPrice();
    values[2] = quote.getBidSize();
    values[3] = quote.getAskPrice();
    values[4] = quote.getAskSize();
    values[5] = quote.getTicker();
    return values;
  }

  /**
   * Updates all the provided quotes in the quote table
   *
   * @param <S>
   * @param quotes list of quotes
   * @return quotes list if succeeds in updating
   * @throws ResourceNotFoundException if quote ticker not found in quote table
   */
  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
    String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, "
        + "ask_size=? WHERE ticker=?";
    List<Object[]> batch = new ArrayList<>();
    quotes.forEach(quote -> {
      if (!existsById(quote.getTicker())) {
        throw new ResourceNotFoundException("Ticker not found:" + quote.getTicker());
      }
      Object[] values = makeUpdateValues(quote);
      batch.add(values);
    });
    int[] rows = jdbcTemplate.batchUpdate(updateSql, batch);
    int totalRow = Arrays.stream(rows).sum();
    if (totalRow != batch.size()) {
      throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
    }
    return (Iterable<S>) findAll();
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> strings) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Deletes quote of provided ticker
   *
   * @param ticker to be deleted
   */
  @Override
  public void deleteById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("Ticker name can't be null");
    }
    String deleteSql = "DELETE FROM quote WHERE ticker=?";
    jdbcTemplate.update(deleteSql, ticker);

  }

}
