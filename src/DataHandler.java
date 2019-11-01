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

  int ingreID;
  String ingreName;
  int ingreNum;
  double ingrePrice;


  ArrayList<String> ingredList;


  ArrayList<Orders> totalOrdersList;
  ArrayList<Orders> cookingOrdersList;
  ArrayList<Orders> newOrdersList;
  ArrayList<Orders> completedOrdersList;
  ArrayList<Orders> cancelledOrdersList;

  ArrayList<Ingredients> ingredientsList;


  public void readOrder(Statement stm) throws SQLException {
    String sql = ("Select * from " + "Orders" + " ;");
    ResultSet rs = stm.executeQuery(sql);

    while (rs.next()) {
      Order_ID = rs.getInt("Order_ID");
      Customer_Name = rs.getString("Customer_Name");
      Contact_Number = rs.getString("Contact_Number");
      Order_Time = rs.getTime("Order_Time");
      Complete_Time = rs.getTime("Complete_Time");
      Total_Price = rs.getDouble("Total_Price");
      status = rs.getString("status");
      orderDetail = rs.getString("orderDetail");

      totalOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));

      if (status.equals("new")) {
        newOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
      } else if (status.equals("cookingOrdersList")) {
        cookingOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
      } else if (status.equals("completed")) {
        completedOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
      } else if (status.equals("cancelled")) {
        cancelledOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
      }
    }
  }


  public void readIngredients(Statement stm) throws SQLException {
    String sql = ("Select * from " + "ingredients" + " ;");
    ResultSet rs = stm.executeQuery(sql);

    while (rs.next()) {
      ingreID = rs.getInt("id");
      ingreName = rs.getString("name");
      ingreNum = rs.getInt("Amount");
      ingrePrice = rs.getDouble("Price");

      ingredientsList.add(new Ingredients(ingreID, ingreName, ingreNum, ingrePrice));
    }
  }

  public boolean confirmOrder(Statement stm, String orderDetail) throws SQLException {
    ArrayList<String> temp = splitOrder(orderDetail);






    try {
        for(String ingred:temp)
        {
          int num = 0;
          for(Ingredients ingredients : ingredientsList)
          {
            if(ingredients.getName().equals(ingred) && ingredients.getAmount()>0)
            {
              num = ingredients.getAmount() -1;
              break;
            }
            else
            {
              System.out.println("Not enough" + ingredients.getName() +" storage");
            }
          }
          String ingredSQL = "UPDATE ingredients SET amount " + num + " Where name = " + ingred;

          stm.execute(ingredSQL);
        }
      return true;
    }catch(SQLException e)
    {
      e.printStackTrace();
      return false;
    }
  }

    public ArrayList<String> splitOrder(String orderDetail)
  {
    ingredList = new ArrayList<>();
    String[] parts  = orderDetail.split(" ");
    for (int i = 0; i < parts.length; i++) {
      ingredList.add(parts[i]);
    }
    return ingredList;
  }
}
    