import java.sql.*;
import java.util.ArrayList;

/**
 * @program JavaTutorial
 * @description:
 * @author: Zong Shi
 * @create 2019-11-01 10:54 AM
 */

public class DataHandler {

  int Order_ID;
  String Customer_Name;
  String Contact_Number;
  Time Order_Time;
  Time Complete_Time;
  double Total_Price;
  String status;
  String orderDetail;


  ArrayList<Orders> onGoingOrdersList;
  ArrayList<Orders> totalOrdersList;

  public void readOrder() throws SQLException {
    Connection conn = DBConnection.getDBConnection().getConnection();
    Statement stm;
    String sql =("Select * from " + "Orders" + " ;" );
    ResultSet rs = stm.executeQuery(sql);

    while(rs.next())
    {
      Order_ID = rs.getInt("Order_ID");
      Customer_Name = rs.getString("Customer_Name");
      Contact_Number = rs.getString("Contact_Number");
      Order_Time = rs.getTime("Order_Time");
      Complete_Time = rs.getTime("Complete_Time");
      Total_Price= rs.getDouble("Total_Price");
      status = rs.getString("status");
      orderDetail = rs.getString("orderDetail");

      totalOrdersList.add(new Orders(Order_ID,Customer_Name,Contact_Number,Order_Time,Complete_Time,Total_Price,status,orderDetail));

      if(status.equals())




    }


  }
}
    