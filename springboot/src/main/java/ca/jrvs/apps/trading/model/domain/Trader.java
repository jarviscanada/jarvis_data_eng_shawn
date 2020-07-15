package ca.jrvs.apps.trading.model.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Trader implements Entity<Integer> {

  private Integer id;
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private String country;
  private String email;


  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public void setDob(String dob) {
    String[] dobSplit = dob.split("-");
    this.dob = LocalDate.of(Integer.valueOf(dobSplit[0]), Integer.valueOf(dobSplit[1]),
        Integer.valueOf(dobSplit[2]));
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trader trader = (Trader) o;
    return id.equals(trader.id) &&
        firstName.equals(trader.firstName) &&
        lastName.equals(trader.lastName) &&
        Objects.equals(dob, trader.dob) &&
        Objects.equals(country, trader.country) &&
        email.equals(trader.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, dob, country, email);
  }

  /**
   * Checks if any property of this instance is null
   *
   * @return true if any one of the property is set to null, else false
   */
  public boolean isNull() {
    return firstName == null || lastName == null || dob == null || country == null || email == null;
  }
}