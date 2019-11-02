import java.sql.*;

/**
 * @program PizzaProject
 * @description:
 * @author: Zong Shi
 * @create 2019-11-01 3:28 PM
 */

public class DBconnection {

  DBconnection dc;
  Connection con;
  PreparedStatement pst;
  //Database URL and JDBC driver information
  static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
  //  static final String DB_RUL = "jdbc:mariadb://127.0.0.1/PlayersDatabase";
  static final String DB_RUL = "jdbc:mariadb://127.0.0.1/Pizza";
  static final String tableName = "ingredients";

  //Database credentials
  static final String USER = "pizza";
  static final String PASS = "pizza";


  Connection conn = null;
  Statement stmt = null;

  public DBconnection() {
    dbConnect(JDBC_DRIVER, DB_RUL, USER, PASS);
  }

  public Boolean dbConnect(String JDBC_DRIVER, String DB_RUL, String USER, String PASS) {
    try {
      //Register JDBC driver
      Class.forName(JDBC_DRIVER);


      //Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(
              DB_RUL, USER, PASS);

      stmt = (Statement) conn.createStatement();
      System.out.println("Connected database successfully...");
      return true;

    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("Connection failed, please check the RUL, user name, and password");

    }
    return false;
  }

  public Connection getConn() {
    return conn;
  }

  public Statement getStmt() {
    return stmt;
  }
}
    