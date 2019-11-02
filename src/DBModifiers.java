import java.sql.*;

public class DBModifiers {
    Connection con = null;

    public DBModifiers() {
        try {
            String dbUser = "myuser";
            String usrPass = "mypass";
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost:3306/mydb";
            con = DriverManager.getConnection(url, dbUser, usrPass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //tests
        changeOrderStat(1, "Cooking");
        int stkID = 1;
        newStock(stkID,"Cheese", 50, 1);
        stkID++;
        newStock(stkID, "Ham", 50, 2.50);
        updateStock("Ham", 25);
        updateStock("Cheese", 1.25);
    }

    //implement a mode if necessary - pass through int parameter which sets mode

    public void newStock(int id, String name, int amount, double price) {
        //need to look into about auto incr
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ingredients;");
            if (stmt.executeQuery("select * from ingredients where name = \"" + name + "\";").next()) { //checks to see if this ingredient exists already
                System.out.println("haha nah"); //could just remove id and instead make name primary key
            }
            else {
                if (rs.next()) {
                    rs.last();
                    id = rs.getInt("id") + 1;
                }
                stmt.execute("insert into ingredients (id, name, amount, price) " +
                        "values (" + id + ", \"" + name + "\", " + amount + ", " + price + ");");
            }
            stmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStock(String name, double price) {
        try {
            Statement stmt = con.createStatement();
            String command = "update ingredients set Price = " + price + "where name = \"" + name + "\";";
            stmt.execute(command);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStock(String name, int amount) {
        try {
            Statement stmt = con.createStatement();
//                String getOrder = "select * from orders where Order_ID = " + orderID + ";";
//                ResultSet rs = stmt.executeQuery(getOrder);
            String command = "update ingredients set Amount = " + amount + " where name = \"" + name + "\";";
            stmt.execute(command);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void changeOrderStat(int orderID, String status) {
        /*buttons needed: Drop down with submit to change to any of the four statuses
         * Confirm order - sends through order ID and "cooking"
         * Decline order - sends through order ID and "cancelled"
         * Should have clause that confirm/decline buttons only selectable if status = new*/
        try {
            Statement stmt = con.createStatement();
//                String getOrder = "select * from orders where Order_ID = " + orderID + ";";
//                ResultSet rs = stmt.executeQuery(getOrder);
            String command = "update orders set Status = \"" + status + "\" where Order_ID = " + orderID + ";";
            stmt.execute(command);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        new DBModifiers();
    }
}