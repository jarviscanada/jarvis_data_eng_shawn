package ca.jrvs.apps.jdbc;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JDBCExecuter {

  private static final Logger logger = LoggerFactory.getLogger(JDBCExecuter.class);
  public static void main(String[] args) {
    JDBCExecuter jdbcExecutor = new JDBCExecuter();
    BasicConfigurator.configure();
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "hplussport","postgres","password");
    try{
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      /*Customer customer = customerDAO.findById(10000);
      System.out.println(customer.getFirstName()+" "+customer.getLastName()+customer.getEmail());
      customer.setEmail("gwashinton@wh.gov");
      customer = customerDAO.update(customer);
      System.out.println(customer.getFirstName()+" "+customer.getLastName()+customer.getEmail());
      customer.setEmail("gwashinton@wh.gov");
      */
      /*Customer customer = new Customer();
      customer.setFirstName("John");
      customer.setLastName("Adams");
      customer.setEmail("jadams@wh.gov");
      customer.setPhone("(555) 555-9846");
      customer.setAddress("1234 Main St");
      customer.setCity("Arlington");
      customer.setState("VA");
      customer.setZipCode("01234");
      Customer dbCustomer = customerDAO.create(customer);
      System.out.println(dbCustomer);
      dbCustomer = customerDAO.findById(dbCustomer.getId());
      System.out.println(dbCustomer);
      dbCustomer.setEmail("john.adams@wh.gov");
      dbCustomer = customerDAO.update(dbCustomer);
      System.out.println(dbCustomer);
      customerDAO.delete(10001);

      OrderDAO orderDAO = new OrderDAO(connection);
      Order order = orderDAO.findById(1000);
      System.out.println(order);
      */

      /*OrderDAO orderDAO = new OrderDAO(connection);
      List<Order> orders = orderDAO.getOrdersForCustomer(789);
      orders.forEach(System.out::println);
      */

      /*
      List<Customer> customers = customerDAO.findAllSorted(20);
      customers.forEach(System.out::println);
       */

      customerDAO.findAllSorted(20).forEach(System.out::println);
      System.out.println("Paged");
      for(int i=1;i<3;i++){
        System.out.println("Page number: " + i);
        customerDAO.findAllPaged(10, i).forEach(System.out::println);
      }
    }catch (SQLException e){
      logger.error(e.getMessage(),e);
    }
  }
}
