package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
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
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

  private static final String TABLE_NAME = "security_order";
  private static final String ID_COLUMN_NAME = "id";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
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
  Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
  }

  /**
   * Helper method that updates existing security order
   *
   * @param securityOrder new security order
   * @return row number of updated security order
   */
  public int updateOne(SecurityOrder securityOrder) {
    String updateSql = "UPDATE security_order SET account_id=?, status=?, ticker=?, size=?, "
        + "price=?, notes=? WHERE id=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(securityOrder));
  }

  /**
   * Helper method to generate update values (Object[]) from SecurityOrder object for update SQL
   * statement
   *
   * @param securityOrder to generate values from
   * @return Object[] containing the values of securityOrder
   */
  private Object[] makeUpdateValues(SecurityOrder securityOrder) {
    Object[] values = new Object[]{securityOrder.getAccountId(), securityOrder.getStatus(),
        securityOrder.getTicker(), securityOrder.getSize(), securityOrder.getPrice(),
        securityOrder.getNotes(), securityOrder.getId()};
    return values;
  }

  /**
   * Updates all the provided securityOrders in the security_order table
   *
   * @param securityOrders list of securityOrders
   * @return updated list of security orders
   */
  @Override
  public <S extends SecurityOrder> Iterable<S> saveAll(Iterable<S> securityOrders) {
    String updateSql = "UPDATE security_order SET account_id=?, status=?, ticker=?, size=?, "
        + "price=?, notes=? WHERE id=?";
    List<Object[]> batch = new ArrayList<>();
    securityOrders.forEach(securityOrder -> {
      if (!existsById(securityOrder.getId())) {
        throw new ResourceNotFoundException("Security order not found:" + securityOrder.getId());
      }
      batch.add(makeUpdateValues(securityOrder));
    });
    int[] rows = jdbcTemplate.batchUpdate(updateSql, batch);
    int totalRow = Arrays.stream(rows).sum();
    if (totalRow != batch.size()) {
      throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
    }
    return (Iterable<S>) findAll();
  }

  /**
   * Deletes all security orders by their account Id
   *
   * @param accountId of security orders to be deleted
   */
  public void deleteAllByAccountId(Integer accountId) {
    String deleteSql = "DELETE FROM " + getTableName() + " WHERE account_id = ?";
    getJdbcTemplate().update(deleteSql, accountId);
  }

}