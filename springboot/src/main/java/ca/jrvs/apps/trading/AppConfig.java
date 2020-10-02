package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.maven.shared.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig(){
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/v1");
    marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
    return marketDataConfig;
  }

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }

  @Bean
  public DataSource dataSource() {

    String jdbcUrl;
    String user;
    String password;

    if (!StringUtils.isEmpty(System.getenv("RDS_HOSTNAME"))) {
      // system env will not be logged in real development - security issue.
      logger.info("RDS_HOSTNAME:" + System.getenv("RDS_HOSTNAME"));
      logger.info("RDS_USERNAME:" + System.getenv("RDS_USERNAME"));
      logger.info("RDS_PASSWORD:" + System.getenv("RDS_PASSWORD"));
      jdbcUrl =
          "jdbc:postgresql://" + System.getenv("RDS_HOSTNAME") + ":" + System.getenv("RDS_PORT")
              + "/"
              + "";
      user = System.getenv("RDS_USERNAME");
      password = System.getenv("RDS_PASSWORD");
    } else {
      jdbcUrl = System.getenv("PSQL_URL");
      user = System.getenv("PSQL_USER");
      password = System.getenv("PSQL_PASSWORD");
    }

    logger.debug("JDBC:" + jdbcUrl);

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(jdbcUrl);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(password);
    return basicDataSource;
  }
}
