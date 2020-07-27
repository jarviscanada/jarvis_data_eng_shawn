package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class QuoteService {
  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;


  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao){
    this.quoteDao=quoteDao;
    this.marketDataDao=marketDataDao;
  }

public void updateMarketData(){
  List<Quote> quotes = findAllQuotes();
  IexQuote iexQuote = new IexQuote();
  Quote tempQuote = new Quote();
  for (Quote quote : quotes){
    String ticker = quote.getTicker();
    iexQuote = marketDataDao.findById(ticker).get();
    tempQuote = buildQuoteFromIexQuote(iexQuote);
    quoteDao.save(tempQuote);
  }

}

  /**
   *
   * @param iexQuote
   * @return quote
   */
  private Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setTicker(iexQuote.getSymbol());
    if(iexQuote.getLatestPrice()==null){
      quote.setLastPrice(null);
    }else {
      quote.setLastPrice(Double.parseDouble(iexQuote.getLatestPrice()));
    }
    quote.setAskPrice(Double.parseDouble(iexQuote.getIexAskPrice()));
    quote.setAskSize(Integer.parseInt(iexQuote.getIexAskSize()));
    quote.setBidPrice(Double.parseDouble(iexQuote.getIexBidPrice()));
    quote.setBidSize(Integer.parseInt(iexQuote.getIexBidSize()));
    return quote;
  }


  /**
   * find all quotes from the quote table
   * @return list of quotes
   */
  public List<Quote> findAllQuotes(){
    return (List<Quote>) quoteDao.findAll();
  }


  /**
   *
   * @param ticker id
   * @return IexQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker){
    return marketDataDao.findById(ticker).orElseThrow(()-> new IllegalArgumentException(ticker +"is invalid ticker"));
  }

  /**
   *
   * @param tickers
   * @return
   */
  public List<Quote> savedQuotes(List<String> tickers){
    Quote quote;
    List<Quote> savedQuote = new ArrayList<>();
    List<IexQuote> iexQuotes= marketDataDao.findAllById(tickers);
    for(IexQuote iexQuote: iexQuotes){
      quote = buildQuoteFromIexQuote(iexQuote);
      savedQuote.add(quoteDao.save(quote));
    }
    return savedQuote;
  }

  /**
   *
   * @param ticker
   * @return
   */
  public Quote saveQuote(String ticker){
    IexQuote iexQuote = new IexQuote();
    iexQuote = marketDataDao.findById(ticker).get();
    Quote quote = buildQuoteFromIexQuote(iexQuote);
    return quoteDao.save(quote);
  }

  public Quote saveQuote(Quote quote){
    return quoteDao.save(quote);
  }

  /**
   * Persists quotes to database
   *
   * @param quotes a list of quotes
   * @return updated quotes
   */
  public List<Quote> saveAllQuotes(List<Quote> quotes) {
    quoteDao.saveAll(quotes);
    return quotes;
  }
}

