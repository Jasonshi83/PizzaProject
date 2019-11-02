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

  public int getOrder_ID() {
    return Order_ID;
  }

  public String getCustomer_Name() {
    return Customer_Name;
  }

  public String getContact_Number() {
    return Contact_Number;
  }

  public Time getOrder_Time() {
    return Order_Time;
  }

  public Time getComplete_Time() {
    return Complete_Time;
  }

  public double getTotal_Price() {
    return Total_Price;
  }

  public String getStatus() {
    return status;
  }

  public String getOrderDetail() {
    return orderDetail;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Orders{" +
            "Order_ID=" + Order_ID +
            ", Customer_Name='" + Customer_Name + '\'' +
            ", Contact_Number='" + Contact_Number + '\'' +
            ", Order_Time=" + Order_Time +
            ", Complete_Time=" + Complete_Time +
            ", Total_Price=" + Total_Price +
            ", status='" + status + '\'' +
            ", orderDetail='" + orderDetail + '\'' +
            ", id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
    