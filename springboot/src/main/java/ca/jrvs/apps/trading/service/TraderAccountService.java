package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount
   * - validate user input (all fields must be non-empty)
   * - create a trader
   * - create an account
   * - create, setup, and return a new traderAccountView
   *
   * Assumption: to simplify the logic, each trader has only one account where traderId=accountId
   *
   * @param trader cannot be null. All fields except for ID(auto-generated) should be non-null
   * @return traderAccountView
   * @throws IllegalArgumentException if a trader has null fields or id is not null.
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    if ((trader.getId() != null) || trader.isNull()) {
      throw new IllegalArgumentException("All fields except ID should be non-null.");
    }
    traderDao.save(trader);
    Account traderAccount = accountDao.save(new Account(trader.getId(), 0.0));
    TraderAccountView traderAccountView = new TraderAccountView(traderAccount, trader);
    return traderAccountView;
  }

  /**
   * A trader can be deleted iff it has no open position and 0 cash balance
   * - validate traderID
   * - get trader account by traderId and check account balance
   * - get position by accountId and check positions
   * - delete all securityOrders, account, trader (in this order)
   *
   * @param traderId must not be null
   * @throws IllegalArgumentException if traderId is null or not found or unable to delete
   */
  public void deleteTraderById(Integer traderId) {
    if (!traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("TraderID not found : " + traderId);
    }
    Account traderAccount = accountDao.findById(traderId).get();
    List<Position> traderPositions = positionDao.findAllById(Arrays.asList(traderId));
    if (traderAccount.getAmount() == 0.0 && isPositionClosed(traderPositions)) {
      securityOrderDao.deleteAllByAccountId(traderAccount.getId());
      accountDao.deleteById(traderAccount.getId());
      traderDao.deleteById(traderId);
    } else {
      throw new IllegalArgumentException("Could not delete Trader Account : " + traderId);
    }
  }

  /**
   * Helper method to check if all positions are closed
   *
   * @param positions list of positions to be checked
   * @return true if all positions are 0, else false
   */
  private boolean isPositionClosed(List<Position> positions) {
    for (Position position : positions) {
      if (position.getPosition() != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Deposit a fund to an account by traderId
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountId
   *
   * @param traderId must not be null
   * @param fund     must be greater than 0.0
   * @return updated Account
   * @throws IllegalArgumentException if traderId is null or not found, and fund is less or equal to
   *                                  0.0
   */
  public Account deposit(Integer traderId, Double fund) {
    if (traderDao.existsById(traderId) && fund > 0.0) {
      Account traderAccount = accountDao.findById(traderId).get();
      Double amount = traderAccount.getAmount();
      amount += fund;
      traderAccount.setAmount(amount);
      return accountDao.save(traderAccount);
    } else {
      throw new IllegalArgumentException(
          "TraderID not found: " + traderId + " OR provided fund<=0.0");
    }
  }

  /**
   * Withdraw a fund to an account by traderId
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountById
   *
   * @param traderId trader ID
   * @param fund     amount can't be 0.0
   * @return updated Account
   * @throws IllegalArgumentException if traderId is null or not found, fund is less or equal to 0,
   *                                  and insufficient fund
   */
  public Account withdraw(Integer traderId, Double fund) {
    if (traderDao.existsById(traderId) && fund > 0.0) {
      Account traderAccount = accountDao.findById(traderId).get();
      Double amount = traderAccount.getAmount();
      if (amount - fund < 0.0) {
        throw new IllegalArgumentException(
            "Withdraw amount " + fund + " is larger than balance " + amount);
      }
      amount -= fund;
      traderAccount.setAmount(amount);
      return accountDao.save(traderAccount);
    } else {
      throw new IllegalArgumentException(
          "TraderID not found: " + traderId + " OR provided fund<=0.0");
    }
  }
}