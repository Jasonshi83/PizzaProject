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

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Ingredients{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", amount=" + amount +
            ", price=" + price +
            '}';
  }
}
    