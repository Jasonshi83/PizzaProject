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


  ArrayList<Orders> totalOrdersList = new ArrayList<>();
  ArrayList<Ingredients> ingredientsList =new ArrayList<>();

  DBconnection dBconnection;
  Statement stm;


//  ArrayList<Orders> cookingOrdersList;
//  ArrayList<Orders> newOrdersList;
//  ArrayList<Orders> completedOrdersList;
//  ArrayList<Orders> cancelledOrdersList;


  public DataHandler() throws SQLException {
    dBconnection = new DBconnection();
    stm = dBconnection.getStmt();
    readOrder();
    readIngredients();

  }

  public void readOrder() throws SQLException {
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
//      System.out.println("Test1");
//      System.out.println(Order_ID);
//      System.out.println(Customer_Name);
//      System.out.println(Contact_Number);
//      System.out.println(Order_Time);
//      System.out.println(Complete_Time);
//      System.out.println(Total_Price);
//      System.out.println(status);
//      System.out.println(orderDetail);
      totalOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
//      System.out.println("Test2");



//      if (status.equals("new")) {
//        newOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
//      } else if (status.equals("cookingOrdersList")) {
//        cookingOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
//      } else if (status.equals("completed")) {
//        completedOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
//      } else if (status.equals("cancelled")) {
//        cancelledOrdersList.add(new Orders(Order_ID, Customer_Name, Contact_Number, Order_Time, Complete_Time, Total_Price, status, orderDetail));
//      }
    }
    System.out.println(totalOrdersList.size());
  }


  public ArrayList<Ingredients> readIngredients() throws SQLException {
    String sql = ("Select * from " + "ingredients" + " ;");
    ResultSet rs = stm.executeQuery(sql);

    while (rs.next()) {
      ingreID = rs.getInt("id");
      ingreName = rs.getString("name");
      ingreNum = rs.getInt("Amount");
      ingrePrice = rs.getDouble("Price");
      ingredientsList.add(new Ingredients(ingreID, ingreName, ingreNum, ingrePrice));
    }
    return ingredientsList;

  }

  //Including a method to update the status from "new" to "cooking" and a method to update the ingredients storage on the table "ingredients"
  public boolean confirmOrder(int order_ID) throws SQLException {
    //Execute SQL to update the order status in the DB
    for(Orders order : totalOrdersList)
    {
      if(order.getOrder_ID() == order_ID && order.getStatus().equalsIgnoreCase("new"))
      {
        String orderSQL = "UPDATE orders set status = " + "'cooking'" + " where order_ID = " + order_ID;
        stm.execute(orderSQL);
        break;
      }
    }

    //Execute SQL to update the ingredients storage in the DB
    String orderToConfirm = findOrder(order_ID);
    ArrayList<String> temp = splitOrder(orderToConfirm);
    System.out.println("ingredientsDetail :" + temp);

    try {
        for(String ingred:temp)
        {
          int num = 0;
          for(Ingredients ingredients : ingredientsList)
          {
            if(ingredients.getName().equals(ingred))
            {
              if(ingredients.getAmount()>0)
              {
                num = ingredients.getAmount() - 1;
                break;
              }else {
                System.out.println("Not enough" + ingredients.getName() +" storage");
              }
            }
          }
          System.out.println("ingred :" + ingred);
          String ingredSQL = "UPDATE ingredients SET amount = " + num + " Where name = " + "'" + ingred + "'";

          stm.execute(ingredSQL);
        }
      return true;
    }catch(SQLException e)
    {
      e.printStackTrace();
      return false;
    }
  }

  public void completeOrder(int order_ID) throws SQLException {
    for(Orders order : totalOrdersList)
    {
      if(order.getOrder_ID() == order_ID && order.getStatus().equalsIgnoreCase("cooking"))
      {
        String orderSQL = "UPDATE orders set status = " + "'completed'" + " where order_ID = " + order_ID;
        stm.execute(orderSQL);
        break;
      }
    }

  }

  public void cancelOrder(int order_ID) throws SQLException {
    for(Orders order : totalOrdersList)
    {
      if(order.getOrder_ID() == order_ID && order.getStatus().equalsIgnoreCase("new"))
      {
        System.out.println("order detail " +  order.toString() + "---------");
        String orderSQL = "UPDATE orders set status = " + "'cancelled'" + " where order_ID = " + order_ID;
        stm.execute(orderSQL);

        String ingredDetail = order.getOrderDetail();
        ArrayList<String> ingredArray = this.splitOrder(ingredDetail);
        for(String ingred : ingredArray)
        {
          for(Ingredients ingredients : ingredientsList)
          {
            if(ingredients.getName().equals(ingred))
            {
              int num = ingredients.getAmount() +1;
              String ingredSQL = "UPDATE ingredients SET amount = " + num + " Where name = " + "'" + ingred + "'";
              stm.execute(ingredSQL);
            }
          }

        }



        break;
      }





    }







  }







    public ArrayList<String> splitOrder(String orderDetail)
  {
    ArrayList<String> ingredList = new ArrayList<>();
    String[] parts  = orderDetail.split(" ");
    for (int i = 0; i < parts.length; i++) {
      ingredList.add(parts[i]);
    }
    return ingredList;
  }

  //method to find the orderDetail if the order_ID match with order ID in the total order list
  public String findOrder(int order_ID)
  {
    for(Orders order : totalOrdersList)
    {
      if(order.getOrder_ID()==order_ID)
      {
        return order.getOrderDetail();
      }
    }
    return null;
  }


  //Testing code
  public void printOutOrders()
  {
    for(Orders order : totalOrdersList)
    {
      System.out.println(order.toString());
    }
  }

  //Testing code
  public void printOutIngredients()
  {
    System.out.println("ingredientsList size " + ingredientsList.size());
    for(Ingredients ingredient : ingredientsList)
    {
      System.out.println(ingredient.toString());
    }
  }




}
    