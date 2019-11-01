import com.sun.xml.internal.bind.v2.model.core.ID;

/**
 * @program PizzaProject
 * @description:
 * @author: Zong Shi
 * @create 2019-11-01 3:01 PM
 */

public class Ingredients {
  int id;
  String name;
  int amount;
  double price;

  public Ingredients(int id, String name, int amount, double price) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.price = price;
  }
}
    