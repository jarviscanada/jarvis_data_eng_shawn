package ca.jrvs.apps.trading.model.domain;

import java.util.Objects;

public class MarketOrderDto {

  private Integer accountId;
  private Integer size;
  private String ticker;

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarketOrderDto that = (MarketOrderDto) o;
    return Objects.equals(accountId, that.accountId) &&
        Objects.equals(size, that.size) &&
        Objects.equals(ticker, that.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, size, ticker);
  }
}