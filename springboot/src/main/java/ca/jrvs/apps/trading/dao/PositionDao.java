package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao extends JdbcCrudDao<Position> {

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";
  private final String TICKER_COLUMN = "ticker";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PositionDao(DataSource dataSource){
    setJdbcTemplate(new JdbcTemplate(dataSource));
    setSimpleJdbcInsert(new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN));
  }

  public static Logger getLogger() {
    return logger;
  }

  public String getTABLE_NAME() {
    return TABLE_NAME;
  }

  public String getID_COLUMN() {
    return ID_COLUMN;
  }

  public String getTICKER_COLUMN() {
    return TICKER_COLUMN;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
    this.simpleJdbcInsert = simpleJdbcInsert;
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
    return ID_COLUMN;
  }

  @Override
  Class<Position> getEntityClass() {
    return Position.class;
  }

  @Override
  public int updateOne(Position entity) {
    return 0;
  }
   public Optional<Position> getByAccountIdAndTicker(Integer accountId, String ticker){
    Optional<Position> entity = Optional.empty();
    String selectSql = "SELECT * FROM "+ getTABLE_NAME() + "WHERE" +
   }
}
