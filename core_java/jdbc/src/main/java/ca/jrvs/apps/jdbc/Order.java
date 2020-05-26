package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Order implements DataTransferObject {
  private long id;
  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;
  private Date creationDate;
  private BigDecimal totalDue;
  private String status;
  private String salepersonFirstName;
  private String salepersonLastName;
  private String salepersonEmail;
  private List<OrderLine> orderLines;



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public BigDecimal getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(BigDecimal totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSalepersonFirstName() {
    return salepersonFirstName;
  }

  public void setSalepersonFirstName(String salepersonFirstName) {
    this.salepersonFirstName = salepersonFirstName;
  }

  public String getSalepersonLastName() {
    return salepersonLastName;
  }

  public void setSalepersonLastName(String salepersonLastName) {
    this.salepersonLastName = salepersonLastName;
  }

  public String getSalepersonEmail() {
    return salepersonEmail;
  }

  public void setSalepersonEmail(String salepersonEmail) {
    this.salepersonEmail = salepersonEmail;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerFirstName='" + customerFirstName + '\'' +
        ", customerLastName='" + customerLastName + '\'' +
        ", customerEmail='" + customerEmail + '\'' +
        ", creationDate=" + creationDate +
        ", totalDue=" + totalDue +
        ", status='" + status + '\'' +
        ", salepersonFirstName='" + salepersonFirstName + '\'' +
        ", salepersonLastName='" + salepersonLastName + '\'' +
        ", salepersonEmail='" + salepersonEmail + '\'' +
        ", orderLines=" + orderLines +
        '}';
  }
}
