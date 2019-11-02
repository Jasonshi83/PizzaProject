import java.sql.SQLException;

/**
 * @program PizzaProject
 * @description:
 * @author: Zong Shi
 * @create 2019-11-01 3:26 PM
 */

public class Main {
  public static void main(String[] args) throws SQLException {
    DataHandler dh = new DataHandler();

    System.out.println("Before");
//    dh.printOutOrders();
//    dh.printOutIngredients();

//    System.out.println("after");

//    dh.confirmOrder(1);
//    dh.confirmOrder(2);
//    dh.completeOrder(1);

//    dh.printOutOrders();
    dh.cancelOrder(3);





  }
}
    