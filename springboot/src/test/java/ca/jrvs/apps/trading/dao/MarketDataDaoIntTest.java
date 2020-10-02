package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTicker() throws IOException {
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
        Assert.assertEquals(2, quoteList.size());
        Assert.assertEquals("AAPL", quoteList.get(0).getSymbol());
        try {
            dao.findAllById(Arrays.asList("AAPL", "FB2"));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (DataRetrievalFailureException e) {
            Assert.fail();
        }

    }

    @Test
    public void findByTicker() {
        String ticker = "AAPL";
        IexQuote iexQuote = dao.findById(ticker).get();
        Assert.assertEquals(ticker, iexQuote.getSymbol());
    }

    @Test
    public void badHttpResponse(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKENNN"));
        MarketDataDao marketDataDao = new MarketDataDao(cm, marketDataConfig);
        try {
            marketDataDao.findAllById(Arrays.asList("AAPL", "FB"));
        } catch (DataRetrievalFailureException e){
            assertTrue(true);
        }
    }

    @Test
    public void errorHandling(){
        try{
            dao.save(new IexQuote());
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.saveAll(new ArrayList<IexQuote>());
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.existsById("AAPL");
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.findAll();
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.count();
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.deleteById("AAPL");
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.deleteAll();
        } catch (UnsupportedOperationException e){
            assertTrue(true);
        }
        try{
            dao.findAllById(new ArrayList<String>());
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }
}
