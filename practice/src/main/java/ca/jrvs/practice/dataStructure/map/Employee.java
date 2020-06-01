package ca.jrvs.practice.dataStructure.map;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Employee {

  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee() {

  }

  /**
   *
   * @param id
   * @param name
   * @param age
   * @param salary
   */
  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    Map<Employee, List<String>> empStrMap = new HashMap<>();
    Employee amy = new Employee(1, "amy", 25, 45000);
    List<String> amyPreviousCompanys = Arrays.asList("TD", "RBC", "CIBC");
    empStrMap.put(amy, amyPreviousCompanys);

    Employee bob = new Employee(2, "bob", 25, 40000);
    List<String> bobPreviousCompanys = Arrays.asList("A&W", "Superstore");
    empStrMap.put(bob, bobPreviousCompanys);

    System.out.println("bob hash code: " + bob.hashCode());
    System.out.println("bob value: " + empStrMap.get(bob).toString());
  }

  /**
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Employee)) {
      return false;
    }
    Employee employee = (Employee) o;
    return getId() == employee.getId() &&
        getAge() == employee.getAge() &&
        getSalary() == employee.getSalary() &&
        Objects.equals(getName(), employee.getName());
  }

  /**
   *
   * @return
   */
  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAge(), getSalary());
  }

}