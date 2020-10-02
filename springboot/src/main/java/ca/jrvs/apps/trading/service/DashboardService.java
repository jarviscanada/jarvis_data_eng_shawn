package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityRow;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DashboardService {

  private TraderDao traderDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao quoteDao){
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.quoteDao = quoteDao;
    this.traderDao = traderDao;
  }

  public TraderAccountView getTraderAccount(Integer traderId){
    Trader trader = traderDao.findById(traderId).get();
    Account account = findAccountByTraderId(traderId);
    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setAccount(account);
    traderAccountView.setTrader(trader);
    return traderAccountView;
  }

  public PortfolioView getProfileViewByTraderId(Integer traderId){
    Account account = findAccountByTraderId(traderId);
    Trader trader = traderDao.findById(traderId).get();
    List<Position> positions = positionDao.findRowByColumnId(account.getId(), "account_id");
    if (positions.size() == 0){
      return new PortfolioView();
    }
    List<SecurityRow> securityRows = new ArrayList<>();
    for (Position position : positions){
      SecurityRow securityRow = new SecurityRow();
      securityRow.setTicker(position.getTicker());
      securityRow.setPosition(position);
      securityRow.setQuote(quoteDao.findById(position.getTicker()).get());
      securityRows.add(securityRow);
    }
    PortfolioView portfolioView = new PortfolioView();
    portfolioView.setSecurityRowList(securityRows);
    return portfolioView;
  }

  private Account findAccountByTraderId(Integer traderId){
    List<Account> accounts = accountDao.findRowByColumnId(traderId, "trader_id");
    if (accounts.size() == 0){
      throw new IllegalArgumentException("Invalid trader id");
    }
    return accounts.get(0);
  }
}
