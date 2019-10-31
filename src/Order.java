import java.sql.*;
import java.util.ArrayList;

/**
 * @program JavaTutorial
 * @description:
 * @author: Zong Shi
 * @create 2019-10-31 12:43 PM
 */

public class Orders {
  int Order_ID;
  String Customer_Name;
  String Contact_Number;
  Time Order_Time;
  Time Complete_Time;
  double Total_Price;
  String status;
  String orderDetail;
  Pizza pizza;

  int id;
  String name;





  public Orders(int order_ID, String customer_Name, String contact_Number, Time order_Time, Time complete_Time, double total_Price, String status, String orderDetail) {
    Order_ID = order_ID;
    Customer_Name = customer_Name;
    Contact_Number = contact_Number;
    Order_Time = order_Time;
    Complete_Time = complete_Time;
    Total_Price = total_Price;
    this.status = status;
    this.orderDetail = orderDetail;
  }



  public void splitOrder(String orderDetail)
  {
    ingredList = new ArrayList<>();
    String[] parts  = orderDetail.split(" ");
    for (int i = 0; i < parts.length; i++) {
      ingredList.add(parts[i]);
    }
  }

  public void readIngredients()
  {
    String sql = ("Select * from " + "ingredients" + " ;" );
    ResultSet rs = st.executeQuery(sql);
    while(rs.next())
    {
      id = rs.getInt("id");

    }
  }


}
    