package ca.jrvs.apps.trading.controller;


import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value= "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {

  private QuoteService quoteService;

  @Autowired
  public QuoteController(QuoteService quoteService){this.quoteService=quoteService;}

  @ApiOperation(value="Show iexQuote", notes = "Show iexQuote for a given ticker/symbol")
  @ApiResponses(value = {@ApiResponse(code = 404, message= "ticker is not found")})
  @GetMapping(path = "/iex/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public IexQuote getQuote(@PathVariable String ticker){
    try{
      return quoteService.findIexQuoteByTicker(ticker);
    }catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  //@ApiOperation(value="update quote table using iex data", notes = "updater all quotes in the quote table, use IEX trading api as market data source")
  @PutMapping(path = "/iexMarketData")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void updateMarketData(){
    try{
      quoteService.updateMarketData();
    }catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value="Add a new ticker in the dailylist(quote table)", notes = "add a new ticker/symbol to the quote table, so trader can trader this  security")
  @PostMapping(path = "/tickerID/{tickerId}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Quote createQuote(@PathVariable String tickerId){
    try {
      return quoteService.saveQuote(tickerId);
    } catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value="update a given quote in the quote table", notes = "manually updater a quote in the quote table using iex market data")
  @PutMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote putQuote(@RequestBody Quote quote){
    try {
      return quoteService.saveQuote(quote);
    } catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value="show the dailylsit", notes = "show the dailylist for this trading system")
  @GetMapping(path = "/dailyList")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> getDailyList(){
    try {
      return quoteService.findAllQuotes();
    } catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
