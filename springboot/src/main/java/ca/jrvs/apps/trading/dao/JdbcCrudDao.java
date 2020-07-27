package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract public JdbcTemplate getJdbcTemplate();

  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  abstract public String getTableName();

  abstract public String getIdColumnName();

  abstract Class<T> getEntityClass();


  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation
   * might have changed the entity instance completely.
   *
   * @param entity must not be {@literal null}.
   * @return the saved entity will never be {@literal null}.
   */
  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      int updateRowNumber = updateOne(entity);
      if (updateRowNumber != 1) {
        throw new DataRetrievalFailureException("unable to update");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());
  }

  abstract public int updateOne(T entity);

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    for (S entity : entities) {
      save(entity);
    }
    return entities;
  }

  /**
   * Retrieves an entity by its id.
   *
   * @param integer must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public Optional<T> findById(Integer integer) {
    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    try {
      entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(selectSql,
          BeanPropertyRowMapper.newInstance(getEntityClass()), integer));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("can not find the id" + integer, e);
    }
    return entity;
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param integer must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public boolean existsById(Integer integer) {
    String selectSql =
        "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = " + integer;
    List<T> entities = getJdbcTemplate()
        .query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()));
    if (entities.size() == 1) {
      T outQuote = entities.get(0);
      return true;
    } else {
      return false;
    }
  }


  public List<T> findByColumn(String column, Object val) {
    List<T> entities = null;
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + column + "=?";
    try {
      entities = getJdbcTemplate()
          .query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()), val);
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trader id: " + val, e);
    }
    return entities;
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Override
  public Iterable<T> findAll() {
    return null;
  }


  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param integers
   * @return
   */
  @Override
  public Iterable<T> findAllById(Iterable<Integer> integers) {
    List<T> entities = new ArrayList<>();
    for (int id : integers) {
      entities.add(findById(id).orElseThrow(IllegalArgumentException::new));
    }
    return entities;
  }

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  @Override
  public long count() {
    long count = 0;
    String sql = "SELECT COUNT(*) FROM " + getTableName();
    return getJdbcTemplate().queryForObject(sql, Long.class);
  }

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   */
  @Override
  public void deleteById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("id can not be null");
    }
    String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    getJdbcTemplate().update(deleteSql, id);
  }

  public void deleteByColumn(String column, Object val) {
    if (column.isEmpty() || val == null) {
      throw new IllegalArgumentException("Column and Value can't be null");
    }
    String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + column + "=?";
    getJdbcTemplate().update(deleteSql, val);
  }

  /**
   * Deletes a given entity.
   *
   * @param entity
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public void delete(T entity) {
    throw new UnsupportedOperationException("Not implemented...");
  }

  /**
   * Deletes the given entities.
   *
   * @param entities
   * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
   */
  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    throw new UnsupportedOperationException("Not implemented...");
  }

  /**
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {
    String deleteSql = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(deleteSql);
  }
}
