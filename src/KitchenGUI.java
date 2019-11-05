
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

public class KitchenGUI extends Application {

    public Stage pumpkin;
    public Scene reload;
    public KitchenGUI g = this;
    static DataHandler dh;
    public VBox rightView;
    public VBox centerContent;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //INITIAL SET UP BELOW THIS LINE ------------------------------------------------------------------------------------------------------
        pumpkin = primaryStage;

        //layout parent
        BorderPane container = new BorderPane();

        //menu
        VBox leftMenu = new VBox();
        leftMenu.setId("leftMenu");
        leftMenu.setAlignment(Pos.TOP_LEFT);
        leftMenu.setPrefWidth(150);
        leftMenu.setPadding(new Insets(10, 10, 10, 10));
        leftMenu.setSpacing(10);

        //display zone
        centerContent = new VBox();
        centerContent.setId("centerContent");
        centerContent.setPrefWidth(500); //set initial size?
        centerContent.setPrefHeight(400);
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.setPadding(new Insets(10, 10, 10, 10));

        //ADD CONTENT TO DISPLAY TO CENTER CONTENT ONLY-------------------------------------------------------------------------

        GridPane welcome = new GridPane();
        welcome.setHgap(15);
        welcome.setVgap(30);
        welcome.setAlignment(Pos.TOP_CENTER);

        centerContent.getChildren().clear();
        String checkForNew = Integer.toString(dh.getNewOrderList().size());
        Text howManyNew = new Text(checkForNew);
        howManyNew.setFont(Font.loadFont("file:src/Fonts/ZukaDoodle.ttf", 80));
        welcome.add(howManyNew, 0, 0);

        Text manyNew = new Text("NEW");
        manyNew.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 40));
        welcome.add(manyNew, 0, 1);

        String checkForCooking = Integer.toString(dh.getCookingOrderList().size());
        Text howManyCooking = new Text(checkForCooking);
        howManyCooking.setFont(Font.loadFont("file:src/Fonts/ZukaDoodle.ttf", 80));
        welcome.add(howManyCooking, 1, 0);

        Text manyCooking = new Text("COOKING");
        manyCooking.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 40));
        welcome.add(manyCooking, 1, 1);

        String checkForCompleted = Integer.toString(dh.getCompletedOrderList().size());
        Text howManyCompleted = new Text(checkForCompleted);
        howManyCompleted.setFont(Font.loadFont("file:src/Fonts/ZukaDoodle.ttf", 80));
        welcome.add(howManyCompleted, 2, 0);

        Text manyCompleted = new Text("COMPLETED");
        manyCompleted.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 40));
        welcome.add(manyCompleted, 2, 1);

        String checkForCancelled = Integer.toString(dh.getCancelledOrderList().size());
        Text howManyCancelled = new Text(checkForCancelled);
        howManyCancelled.setFont(Font.loadFont("file:src/Fonts/ZukaDoodle.ttf", 80));
        welcome.add(howManyCancelled, 3, 0);

        Text manyCancelled = new Text("CANCELLED");
        manyCancelled.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 40));
        welcome.add(manyCancelled, 3, 1);

        for (int n = 0; n<welcome.getChildren().size(); n++){
            GridPane.setHalignment(welcome.getChildren().get(n), HPos.CENTER);
        }

        centerContent.getChildren().add(welcome);

        //MENU SET UP BELOW-----------------------------------------------------------------------------------------------------

        //header
        HBox topMenu = new HBox();
        topMenu.setId("topMenu");
        topMenu.setPadding(new Insets(10, 10, 10, 10));
        topMenu.setSpacing(30);
        topMenu.setAlignment(Pos.CENTER);

        //logo
        Image bulba = new Image(getClass().getResourceAsStream("transparentbulbasaurpizza.png"));
        ImageView logoView = new ImageView(bulba);
        logoView.setFitWidth(311);
        logoView.setFitHeight(233);
        //topMenu.getChildren().add(logoView);
        centerContent.getChildren().add(logoView);

        //date display
        LocalDate date = LocalDate.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateDisplay = date.format(formattedDate);
        Text showDate = new Text(dateDisplay);
        topMenu.getChildren().add(showDate);

        //title
        Text topTitle = new Text("Pokémon Pizzeria");
        topTitle.setId("topTitle");
        topTitle.setFont(Font.loadFont("file:src/Fonts/wavepool.ttf", 56));
        topMenu.getChildren().add(topTitle);

        //time display
        LocalTime time = LocalTime.now();
        DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeDisplay = time.format(formattedTime);
        Text showTime = new Text(timeDisplay);
        topMenu.getChildren().add(showTime);
        bindToTime(showTime);// this calls the method for the clock to update every second

        //LEFT SIDE LISTENERS BELOW------------------------------------------------------------------------------------------------

        Text addHeading = new Text("Add");
        addHeading.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
        leftMenu.getChildren().add(addHeading);

        leftMenu.getChildren().add(new ImageView(new Image("shinyBulba.gif", 50,49.3, false, false, false)));

//        Button addNewIngredient = new Button("New Ingredient");
//        addNewIngredient.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                centerContent.getChildren().clear();
//                centerContent.getChildren().add(new Text("I am adding a new ingredient option!"));
//                g.refresh();
//            }
//        });
//        leftMenu.getChildren().add(addNewIngredient);

        Text viewHeading = new Text("View");
        viewHeading.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
        leftMenu.getChildren().add(viewHeading);

        Text orderHeading = new Text("orders");
        orderHeading.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 14));
        leftMenu.getChildren().add(orderHeading);

        Button currentOrders = new Button("Cooking");//-----------------------------------------------------------------------
        currentOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TITLE------------------------------
                centerContent.getChildren().clear();
                ScrollPane bigData = new ScrollPane();
                bigData.setContent(null);//clear
                Text lilTitle = new Text("in the oven...");
                lilTitle.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                centerContent.getChildren().add(lilTitle);
                //SPINNER----------------------------
                ArrayList <Orders> cookingOrdersToShow = dh.getCookingOrderList();
                Spinner<Integer> orderSelected = new Spinner<Integer>();
                SpinnerValueFactory availableOrders = new SpinnerValueFactory() {
                    @Override
                    public void increment(int steps) {
                        int current = (int) this.getValue();
                        int flag = 0;
                        int newValue = 0;
                        if(cookingOrdersToShow.size()==1){
                            newValue = current;
                        }
                        else{
                            for(Orders order : cookingOrdersToShow)
                            {
                                if(order.getOrder_ID() != current)
                                {
                                    flag++;
                                }
                                else
                                {
                                    if(flag>=1) {
                                        newValue = cookingOrdersToShow.get(flag - 1).getOrder_ID();
                                        System.out.println(flag + "flag testing");
                                    }
                                    else
                                    {
                                        newValue = cookingOrdersToShow.get(0).getOrder_ID();
                                    }

                                }

                            }

                        }
                        this.setValue(newValue);
                    }

                    @Override
                    public void decrement(int steps) {
                        int current = (int) this.getValue();
                        int flag = 0;
                        int newValue = 0;
                        if(cookingOrdersToShow.size()==1){
                            newValue = current;
                        }
                        else{

                            for(Orders order : cookingOrdersToShow)
                            {
                                if(order.getOrder_ID() != current)
                                {
                                    flag++;
                                }

                                else
                                {
                                    if(flag<cookingOrdersToShow.size()-1) {
                                        newValue = cookingOrdersToShow.get(flag + 1).getOrder_ID();
                                    }
                                    else
                                    {
                                        newValue = cookingOrdersToShow.get(flag).getOrder_ID();
                                    }
                                }
                            }
                        }
                        this.setValue(newValue);
                    }
                };

                availableOrders.setValue(cookingOrdersToShow.get(0).getOrder_ID());
                orderSelected.setValueFactory(availableOrders);

                centerContent.getChildren().add(orderSelected);
                Button conf = new Button("order complete");
                conf.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            int found = 99999;
                            for (int i = 0; i < cookingOrdersToShow.size(); i++) {
                                if (orderSelected.getValue() == cookingOrdersToShow.get(i).getOrder_ID()) {
                                    found = i;
                                }
                            }
                            if (found == 99999) {
                                Alert error = new Alert(Alert.AlertType.ERROR, "invalid order, please select again");
                                error.show();
                            } else {
                                Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                        "confirm order " + orderSelected.getValue() + " for " + cookingOrdersToShow.get(found).getCustomer_Name() + " is complete");
                                Optional<ButtonType> result = checker.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    // ... user chose OK
                                    System.out.println("order completed");
                                    checker.close();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Order Completed");
                                    alert.setHeaderText("ORDER COMPLETED");
                                    alert.setContentText("The customer will be notified that their order is complete.");
                                    alert.show();
                                    dh.confirmOrder(orderSelected.getValue());
                                    dh = new DataHandler();
                                    g.refresh();

                                } else {
                                    // ... user chose CANCEL or closed the dialog
                                }
                                //checker.show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                centerContent.getChildren().add(conf);

                //centerContent.getChildren().add(confirmBox);

                //GRID-------------------------------
                GridPane table = new GridPane();
                table.getChildren().removeAll();//clear!
                table.setVgap(5);
                table.setHgap(10);
                table.setAlignment(Pos.CENTER_LEFT);
                Text id = new Text("ID");
                table.add(id, 0, 0);
                Text custName = new Text("Customer");
                table.add(custName, 1, 0);
                Text oD = new Text("Order Details");
                table.add(oD, 2, 0);
                //centerContent.getChildren().add(table);
                //FILL-------------------------------
                ArrayList<Orders> oven = null;
                oven = dh.getCookingOrderList();
                //System.out.println("entered ingredient loop");

                for (int i = 0; i < oven.size(); i++) {
                    //System.out.println(i);
                    String ordID = Integer.toString(oven.get(i).getOrder_ID());
                    table.add(new Text(ordID), 0, i + 1);
                    table.add(new Text(oven.get(i).getCustomer_Name()), 1, i + 1);
                    System.out.println(oven.get(i).orderDetail);
                    String ordDet = (dh.ChrissyString(oven.get(i).getOrder_ID()));
                    table.add(new Text(ordDet), 2, i + 1);
                }
                bigData.setContent(table);
                centerContent.getChildren().add(bigData);
                try {
                    dh = new DataHandler();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                g.refresh();
            }
        });
        leftMenu.getChildren().add(currentOrders);

        Button completedOrders = new Button("Completed");//-------------------------------------------------------------------
        completedOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TITLE------------------------------
                centerContent.getChildren().clear();
                ScrollPane bigData = new ScrollPane();
                bigData.setContent(null);//clear
                Text lilTitle = new Text("in the belly...");
                lilTitle.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                centerContent.getChildren().add(lilTitle);
                //GRID-------------------------------
                GridPane table = new GridPane();
                table.getChildren().removeAll();//clear!
                table.setVgap(5);
                table.setHgap(10);
                table.setAlignment(Pos.CENTER_LEFT);
                Text id = new Text("ID");
                table.add(id, 0, 0);
                Text custName = new Text("Customer");
                table.add(custName, 1, 0);
                Text oD = new Text("Order Details");
                table.add(oD, 2, 0);
                Text complTime = new Text("Time Completed");
                table.add(complTime, 3, 0);
                //centerContent.getChildren().add(table);
                //FILL-------------------------------
                ArrayList<Orders> belly = null;
                belly = dh.getCompletedOrderList();
                //System.out.println("entered ingredient loop");

                for (int i = 0; i < belly.size(); i++) {
                    //System.out.println(i);
                    String ordID = Integer.toString(belly.get(i).getOrder_ID());
                    table.add(new Text(ordID), 0, i + 1);
                    table.add(new Text(belly.get(i).getCustomer_Name()), 1, i + 1);
                    //System.out.println(belly.get(i).orderDetail);
                    String ordDet = (dh.ChrissyString(belly.get(i).getOrder_ID()));
                    table.add(new Text(ordDet), 2, i + 1);
                    String finishedTime;
                    if (belly.isEmpty()==false && belly.get(i).getComplete_Time()!=null){
                        finishedTime = belly.get(i).getComplete_Time().toString();
                    }
                    else{
                        finishedTime = "finished ages ago";
                    }
                    table.add(new Text(finishedTime), 3, i+1);
                }
                bigData.setContent(table);
                centerContent.getChildren().add(bigData);
                try {
                    dh = new DataHandler();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                g.refresh();
            }
        });
        leftMenu.getChildren().add(completedOrders);

        Button cancelledOrders = new Button("Cancelled");//-------------------------------------------------------------------
        cancelledOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TITLE------------------------------
                centerContent.getChildren().clear();
                ScrollPane bigData = new ScrollPane();
                bigData.setContent(null);//clear
                Text lilTitle = new Text("in the void...");
                lilTitle.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                centerContent.getChildren().add(lilTitle);
                //GRID-------------------------------
                GridPane table = new GridPane();
                table.getChildren().removeAll();//clear!
                table.setVgap(5);
                table.setHgap(10);
                table.setAlignment(Pos.CENTER_LEFT);
                Text id = new Text("ID");
                table.add(id, 0, 0);
                Text custName = new Text("Customer");
                table.add(custName, 1, 0);
                Text oD = new Text("Order Details");
                table.add(oD, 2, 0);
                //centerContent.getChildren().add(table);
                //FILL-------------------------------
                ArrayList<Orders> bin = null;
                bin = dh.getCancelledOrderList();
                //System.out.println("entered ingredient loop");

                for (int i = 0; i < bin.size(); i++) {
                    //System.out.println(i);
                    String ordID = Integer.toString(bin.get(i).getOrder_ID());
                    table.add(new Text(ordID), 0, i + 1);
                    table.add(new Text(bin.get(i).getCustomer_Name()), 1, i + 1);
                    //System.out.println(bin.get(i).orderDetail);
                    String ordDet = (dh.ChrissyString(bin.get(i).getOrder_ID()));
                    table.add(new Text(ordDet), 2, i + 1);
                }
                bigData.setContent(table);
                centerContent.getChildren().add(bigData);
                g.refresh();
            }
        });
        leftMenu.getChildren().add(cancelledOrders);

        Text invHeading = new Text("ingredients");
        invHeading.setFont(Font.loadFont("file:src/Fonts/jabjai_heavy.ttf", 14));
        leftMenu.getChildren().add(invHeading);

        Button seeIngredients = new Button("Current Stock");
        seeIngredients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TITLE------------------------------
                centerContent.getChildren().clear();
                ScrollPane bigData = new ScrollPane();
                bigData.setContent(null);//clear
                Text lilTitle = new Text("in the pantry...");
                lilTitle.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                centerContent.getChildren().add(lilTitle);
                //GRID-------------------------------
                GridPane table = new GridPane();
                table.getChildren().removeAll();//clear!
                table.setVgap(5);
                table.setHgap(10);
                table.setAlignment(Pos.CENTER_LEFT);
                Text id = new Text("ID");
                table.add(id, 0, 0);
                Text ingred = new Text("Ingredient");
                table.add(ingred, 1, 0);
                Text q = new Text("Quantity In Stock");
                table.add(q, 2, 0);
                //centerContent.getChildren().add(table);
                //FILL-------------------------------
                ArrayList<Ingredients> pantry = null;
                try {
                    pantry = dh.readIngredients();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //System.out.println("entered ingredient loop");

                for (int i = 0; i < pantry.size(); i++) {
                    //System.out.println(i);
                    String ingrID = Integer.toString(pantry.get(i).getId());
                    table.add(new Text(ingrID), 0, i + 1);
                    table.add(new Text(pantry.get(i).getName()), 1, i + 1);
                    String ingrQuan = Integer.toString(pantry.get(i).getAmount());
                    table.add(new Text(ingrQuan), 2, i + 1);
                }
                bigData.setContent(table);
                centerContent.getChildren().add(bigData);
                g.refresh();
            }
        });
        leftMenu.getChildren().add(seeIngredients);

        Text updateHeading = new Text("Update");
        updateHeading.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
        leftMenu.getChildren().add(updateHeading);

        Button updateStock = new Button("Stock Levels");
        updateStock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                centerContent.getChildren().clear();
                //TITLE------------------------------
                ScrollPane bigData = new ScrollPane();
                bigData.setContent(null);//clear
                Text lilTitle = new Text("in the pantry...");
                lilTitle.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                centerContent.getChildren().add(lilTitle);
                //SPINNER----------------------------
               ArrayList <Ingredients> stockToShow= null;
                try {
                    stockToShow = dh.readIngredients();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Spinner<Integer> orderSelected = new Spinner<Integer>();
                ArrayList<Ingredients> finalStockToShow = stockToShow;
                ArrayList<Ingredients> finalStockToShow1 = stockToShow;
                SpinnerValueFactory availableOrders = new SpinnerValueFactory() {
                    @Override
                    public void increment(int steps) {
                        int current = (int) this.getValue();
                        int flag = 0;
                        int newValue = 0;
                        if(finalStockToShow.size()==1){
                            newValue = current;
                        }
                        else{
                            for(Ingredients ing : finalStockToShow)
                            {
                                if(ing.getId() != current)
                                {
                                    flag++;
                                }
                                else
                                {
                                    if(flag>=1) {
                                        newValue = finalStockToShow1.get(flag - 1).getId();
                                        System.out.println(flag + "flag testing");
                                    }
                                    else
                                    {
                                        newValue = finalStockToShow1.get(0).getId();
                                    }

                                }

                            }

                        }
                        this.setValue(newValue);
                    }

                    @Override
                    public void decrement(int steps) {
                        int current = (int) this.getValue();
                        int flag = 0;
                        int newValue = 0;
                        if(finalStockToShow.size()==1){
                            newValue = current;
                        }
                        else{

                            for(Ingredients ing : finalStockToShow)
                            {
                                if(ing.getId() != current)
                                {
                                    flag++;
                                }

                                else
                                {
                                    if(flag< finalStockToShow1.size()-1) {
                                        newValue = finalStockToShow1.get(flag + 1).getId();
                                    }
                                    else
                                    {
                                        newValue = finalStockToShow1.get(flag).getId();
                                    }
                                }
                            }
                        }
                        this.setValue(newValue);
                    }
                };

                availableOrders.setValue(stockToShow.get(0).getId());
                orderSelected.setValueFactory(availableOrders);

                centerContent.getChildren().add(orderSelected);
                Button updStock = new Button("add 50");
                ArrayList<Ingredients> finalStockToShow2 = stockToShow;
                updStock.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            int found = 99999;
                            for (int i = 0; i < finalStockToShow2.size(); i++) {
                                if (orderSelected.getValue() == finalStockToShow2.get(i).getId()) {
                                    found = i;
                                }
                            }
                            if (found == 99999) {
                                Alert error = new Alert(Alert.AlertType.ERROR, "invalid ingredient, please select again");
                                error.show();
                            } else {
                                Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                        "confirm add 50 to " + finalStockToShow2.get(found).getName());
                                Optional<ButtonType> result = checker.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    // ... user chose OK
                                    System.out.println("stock updated");
                                    checker.close();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Stock Updated");
                                    alert.setHeaderText("STOCK UPDATED");
                                    alert.setContentText(finalStockToShow2.get(found).getName() +" has been updated.");
                                    alert.show();
                                    dh.updateIngred(finalStockToShow2.get(found).getName(), 50);
                                    //dh.confirmOrder(orderSelected.getValue());
                                    dh = new DataHandler();
                                    g.refresh();

                                } else {
                                    // ... user chose CANCEL or closed the dialog
                                }
                                //checker.show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                centerContent.getChildren().add(updStock);

                //GRID-------------------------------
                GridPane table = new GridPane();
                table.getChildren().removeAll();//clear!
                table.setVgap(5);
                table.setHgap(10);
                table.setAlignment(Pos.CENTER_LEFT);
                Text id = new Text("ID");
                table.add(id, 0, 0);
                Text ingred = new Text("Ingredient");
                table.add(ingred, 1, 0);
                Text q = new Text("Quantity In Stock");
                table.add(q, 2, 0);
                //centerContent.getChildren().add(table);
                //FILL-------------------------------
                ArrayList<Ingredients> pantry = null;
                try {
                    pantry = dh.readIngredients();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //System.out.println("entered ingredient loop");

                for (int i = 0; i < pantry.size(); i++) {
                    //System.out.println(i);
                    String ingrID = Integer.toString(pantry.get(i).getId());
                    table.add(new Text(ingrID), 0, i + 1);
                    table.add(new Text(pantry.get(i).getName()), 1, i + 1);
                    String ingrQuan = Integer.toString(pantry.get(i).getAmount());
                    table.add(new Text(ingrQuan), 2, i + 1);
                }
                bigData.setContent(table);
                centerContent.getChildren().add(bigData);
                g.refresh();
            }
        });
        leftMenu.getChildren().add(updateStock);

        //RIGHT SIDE SET UP BELOW--------------------------------------------------------------------------------------------------
        rightView = new VBox();
        rightView.setPrefWidth(160);
        rightView.setPadding(new Insets(10, 10, 10, 10));
        rightView.setAlignment(Pos.TOP_RIGHT);
        rightView.setId("rightView");
        Text alwaysSee = new Text("New" + "\n" + "Orders");
        alwaysSee.setTextAlignment(TextAlignment.CENTER);
        alwaysSee.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
        rightView.getChildren().add(alwaysSee);

        VBox newOrders = new VBox();
        GridPane summaryView = new GridPane();
        summaryView.setAlignment(Pos.TOP_RIGHT);
        summaryView.setHgap(5);
        summaryView.setVgap(3);
        ArrayList<Orders> newOrdersToShow = dh.getNewOrderList();
        for (int i = 0; i < newOrdersToShow.size(); i++) {
            summaryView.add(new Text(newOrdersToShow.get(i).getStatus() + " order for"), 0, i);
            summaryView.add(new Text(newOrdersToShow.get(i).getCustomer_Name()), 1, i);
        }
        rightView.getChildren().add(summaryView);

        Button viewToConfirm = new Button("See More Details");
        viewToConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                centerContent.getChildren().clear();
                HBox confirmBox = new HBox();
                confirmBox.getChildren().add(new Text("confirm order: "));
                if (newOrdersToShow.isEmpty()) {
                    System.out.println("no new orders to confirm :-(");
                } else {

                    Spinner<Integer> orderSelected = new Spinner<Integer>();
                    SpinnerValueFactory availableOrders = new SpinnerValueFactory() {
                        @Override
                        public void increment(int steps) {
                            int current = (int) this.getValue();
                            int flag = 0;
                            int newValue = 0;
                            if(newOrdersToShow.size()==1){
                                newValue = current;
                            }
                            else{
                                for(Orders order : newOrdersToShow)
                                {
                                    if(order.getOrder_ID() != current)
                                    {
                                        flag++;
                                    }
                                    else
                                    {
                                        if(flag>=1) {
                                            newValue = newOrdersToShow.get(flag - 1).getOrder_ID();
                                            System.out.println(flag + "flag testing");
                                        }
                                        else
                                        {
                                            newValue = newOrdersToShow.get(0).getOrder_ID();
                                        }

                                    }

                                }

                            }
                            this.setValue(newValue);
                        }

                        @Override
                        public void decrement(int steps) {
                            int current = (int) this.getValue();
                            int flag = 0;
                            int newValue = 0;
                            if(newOrdersToShow.size()==1){
                                newValue = current;
                            }
                            else{

                                for(Orders order : newOrdersToShow)
                                {
                                    if(order.getOrder_ID() != current)
                                    {
                                        flag++;
                                    }

                                    else
                                    {
                                        if(flag<newOrdersToShow.size()-1) {
                                            newValue = newOrdersToShow.get(flag + 1).getOrder_ID();
                                        }
                                        else
                                        {
                                            newValue = newOrdersToShow.get(flag).getOrder_ID();
                                        }
                                    }
                                }
                            }
                            this.setValue(newValue);
                        }
                    };

                    availableOrders.setValue(newOrdersToShow.get(0).getOrder_ID());
                    orderSelected.setValueFactory(availableOrders);

                    confirmBox.getChildren().add(orderSelected);
                    Button conf = new Button("accept order");
                    conf.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                int found = 99999;
                                for (int i = 0; i < newOrdersToShow.size(); i++) {
                                    if (orderSelected.getValue() == newOrdersToShow.get(i).getOrder_ID()) {
                                        found = i;
                                    }
                                }
                                if (found == 99999) {
                                    Alert error = new Alert(Alert.AlertType.ERROR, "invalid order, please select again");
                                    error.show();
                                } else {
                                    Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                            "confirm order " + orderSelected.getValue() + " for " + newOrdersToShow.get(found).getCustomer_Name());
                                    Optional<ButtonType> result = checker.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK
                                        System.out.println("order confirmed");
                                        checker.close();
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Confirmed Order");
                                        alert.setHeaderText("ORDER CONFIRMED");
                                        alert.setContentText("The customer will be notified that their order is cooking.");
                                        alert.show();
                                        dh.confirmOrder(orderSelected.getValue());
                                        g.refresh();

                                    } else {
                                        // ... user chose CANCEL or closed the dialog
                                    }
                                    //checker.show();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    confirmBox.getChildren().add(conf);

                    Button decline = new Button("decline order");
                    decline.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                int found = 99999;
                                for (int i = 0; i < newOrdersToShow.size(); i++) {
                                    if (orderSelected.getValue() == newOrdersToShow.get(i).getOrder_ID()) {
                                        found = i;
                                    }
                                }
                                if (found == 99999) {
                                    Alert error = new Alert(Alert.AlertType.ERROR, "invalid order, please select again");
                                    error.show();
                                } else {
                                    Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                            "confirm decline order " + orderSelected.getValue() + " for " + newOrdersToShow.get(found).getCustomer_Name());
                                    Optional<ButtonType> result = checker.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK
                                        System.out.println("order declined");
                                        checker.close();
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Declined Order");
                                        alert.setHeaderText("ORDER DECLINED");
                                        alert.setContentText("The customer will be notified that their order has been declined.");
                                        alert.show();
                                        dh.cancelOrder(orderSelected.getValue());
                                        g.refresh();

                                    } else {
                                        // ... user chose CANCEL or closed the dialog
                                    }
                                    //checker.show();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    confirmBox.getChildren().add(decline);

                    centerContent.getChildren().add(confirmBox);
                }

                GridPane seeNewOrders = new GridPane();
                seeNewOrders.setVgap(10);
                seeNewOrders.setHgap(10);
                Text id = new Text("ID");
                seeNewOrders.add(id, 0, 0);
                Text ingred = new Text("Customer Name");
                seeNewOrders.add(ingred, 1, 0);
                Text q = new Text("Order Details");
                seeNewOrders.add(q, 2, 0);
                for (
                        int i = 0; i < newOrdersToShow.size(); i++) {
                    String orderID = Integer.toString(newOrdersToShow.get(i).getOrder_ID());
                    seeNewOrders.add(new Text(orderID), 0, i + 1);
                    seeNewOrders.add(new Text(newOrdersToShow.get(i).getCustomer_Name()), 1, i + 1);
                    seeNewOrders.add(new Text(newOrdersToShow.get(i).getOrderDetail()), 2, i + 1);
                }
                centerContent.getChildren().

                        add(seeNewOrders);
                g.refresh();

            }
        });
        rightView.getChildren().add(viewToConfirm);


        //FINAL SET UP BELOW-------------------------------------------------------------------------------------------------------

        container.setRight(rightView);
        container.setLeft(leftMenu);
        container.setCenter(centerContent);
        container.setTop(topMenu);

        reload = new

                Scene(container);
        reload.getStylesheets().

                add("PizzaPugStyle.css");

        primaryStage.setTitle("♥.∙: *∞* :∙.♥.∙: *∞* :∙.♥ Pokémon Pizzeria Kitchen App ♥.∙: *∞* :∙.♥.∙: *∞* :∙.♥");
        primaryStage.setScene(reload);
        primaryStage.show();
    }

    //REFRESH SCENE METHOD--------------------------------------------------------------------------------------------------------
    public void refresh() {
        //System.out.println("refreshing scene...");
        pumpkin.setScene(reload);
        pumpkin.show();
    }

    //AUTO REFRESH FOR CLOCK AND NEW ORDERS---------------------------------------------------------------------------------------------------------
    private void bindToTime(Text display) {
        Timeline timeline = new Timeline(
                //what to reload
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                //reload time
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                display.setText(simpleDateFormat.format(time.getTime()));

                                //reload new orders
                                try {
                                    dh = new DataHandler();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                rightView.getChildren().clear();
                                Text alwaysSee = new Text("New" + "\n" + "Orders");
                                alwaysSee.setTextAlignment(TextAlignment.CENTER);
                                alwaysSee.setFont(Font.loadFont("file:src/Fonts/jabjai_light.ttf", 28));
                                rightView.getChildren().add(alwaysSee);

                                VBox newOrders = new VBox();
                                GridPane summaryView = new GridPane();
                                summaryView.setAlignment(Pos.TOP_RIGHT);
                                summaryView.setHgap(5);
                                summaryView.setVgap(3);
                                ArrayList<Orders> newOrdersToShow = dh.getNewOrderList();
                                for (int i = 0; i < newOrdersToShow.size(); i++) {
                                    summaryView.add(new Text(newOrdersToShow.get(i).getStatus() + " order for"), 0, i);
                                    summaryView.add(new Text(newOrdersToShow.get(i).getCustomer_Name()), 1, i);
                                }
                                rightView.getChildren().add(summaryView);

                                Button viewToConfirm = new Button("See More Details");
                                viewToConfirm.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        centerContent.getChildren().clear();
                                        HBox confirmBox = new HBox();
                                        confirmBox.getChildren().add(new Text("confirm order: "));
                                        if (newOrdersToShow.isEmpty()) {
                                            System.out.println("no new orders to confirm :-(");
                                        } else {

                                            Spinner<Integer> orderSelected = new Spinner<Integer>();
                                            SpinnerValueFactory availableOrders = new SpinnerValueFactory() {
                                                @Override
                                                public void increment(int steps) {
                                                    int current = (int) this.getValue();
                                                    int flag = 0;
                                                    int newValue = 0;
                                                    if(newOrdersToShow.size()==1){
                                                        newValue = current;
                                                    }
                                                    else{
                                                        for(Orders order : newOrdersToShow)
                                                        {
                                                            if(order.getOrder_ID() != current)
                                                            {
                                                                flag++;
                                                            }
                                                            else
                                                            {
                                                                if(flag>=1) {
                                                                    newValue = newOrdersToShow.get(flag - 1).getOrder_ID();
                                                                    System.out.println(flag + "flag testing");
                                                                }
                                                                else
                                                                {
                                                                    newValue = newOrdersToShow.get(0).getOrder_ID();
                                                                }

                                                            }

                                                        }

                                                    }
                                                    this.setValue(newValue);
                                                }

                                                @Override
                                                public void decrement(int steps) {
                                                    int current = (int) this.getValue();
                                                    int flag = 0;
                                                    int newValue = 0;
                                                    if(newOrdersToShow.size()==1){
                                                        newValue = current;
                                                    }
                                                    else{

                                                        for(Orders order : newOrdersToShow)
                                                        {
                                                            if(order.getOrder_ID() != current)
                                                            {
                                                                flag++;
                                                            }

                                                            else
                                                            {
                                                                if(flag<newOrdersToShow.size()-1) {
                                                                    newValue = newOrdersToShow.get(flag + 1).getOrder_ID();
                                                                }
                                                                else
                                                                {
                                                                    newValue = newOrdersToShow.get(flag).getOrder_ID();
                                                                }
                                                            }
                                                        }
                                                    }
                                                    this.setValue(newValue);
                                                }
                                            };

                                            availableOrders.setValue(newOrdersToShow.get(0).getOrder_ID());
                                            orderSelected.setValueFactory(availableOrders);

                                            confirmBox.getChildren().add(orderSelected);
                                            Button conf = new Button("accept order");
                                            conf.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    try {
                                                        int found = 99999;
                                                        for (int i = 0; i < newOrdersToShow.size(); i++) {
                                                            if (orderSelected.getValue() == newOrdersToShow.get(i).getOrder_ID()) {
                                                                found = i;
                                                            }
                                                        }
                                                        if (found == 99999) {
                                                            Alert error = new Alert(Alert.AlertType.ERROR, "invalid order, please select again");
                                                            error.show();
                                                        } else {
                                                            Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                                                    "confirm order " + orderSelected.getValue() + " for " + newOrdersToShow.get(found).getCustomer_Name());
                                                            Optional<ButtonType> result = checker.showAndWait();
                                                            if (result.get() == ButtonType.OK) {
                                                                // ... user chose OK
                                                                System.out.println("order confirmed");
                                                                checker.close();
                                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                alert.setTitle("Confirmed Order");
                                                                alert.setHeaderText("ORDER CONFIRMED");
                                                                alert.setContentText("The customer will be notified that their order is cooking.");
                                                                alert.show();
                                                                dh.confirmOrder(orderSelected.getValue());
                                                                g.refresh();

                                                            } else {
                                                                // ... user chose CANCEL or closed the dialog
                                                            }
                                                            //checker.show();
                                                        }
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                            confirmBox.getChildren().add(conf);

                                            Button decline = new Button("decline order");
                                            decline.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    try {
                                                        int found = 99999;
                                                        for (int i = 0; i < newOrdersToShow.size(); i++) {
                                                            if (orderSelected.getValue() == newOrdersToShow.get(i).getOrder_ID()) {
                                                                found = i;
                                                            }
                                                        }
                                                        if (found == 99999) {
                                                            Alert error = new Alert(Alert.AlertType.ERROR, "invalid order, please select again");
                                                            error.show();
                                                        } else {
                                                            Alert checker = new Alert(Alert.AlertType.CONFIRMATION,

                                                                    "confirm decline order " + orderSelected.getValue() + " for " + newOrdersToShow.get(found).getCustomer_Name());
                                                            Optional<ButtonType> result = checker.showAndWait();
                                                            if (result.get() == ButtonType.OK) {
                                                                // ... user chose OK
                                                                System.out.println("order declined");
                                                                checker.close();
                                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                alert.setTitle("Declined Order");
                                                                alert.setHeaderText("ORDER DECLINED");
                                                                alert.setContentText("The customer will be notified that their order has been declined.");
                                                                alert.show();
                                                                dh.cancelOrder(orderSelected.getValue());
                                                                g.refresh();

                                                            } else {
                                                                // ... user chose CANCEL or closed the dialog
                                                            }
                                                            //checker.show();
                                                        }
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                            confirmBox.getChildren().add(decline);

                                            centerContent.getChildren().add(confirmBox);
                                        }

                                        GridPane seeNewOrders = new GridPane();
                                        seeNewOrders.setVgap(10);
                                        seeNewOrders.setHgap(10);
                                        Text id = new Text("ID");
                                        seeNewOrders.add(id, 0, 0);
                                        Text ingred = new Text("Customer Name");
                                        seeNewOrders.add(ingred, 1, 0);
                                        Text q = new Text("Order Details");
                                        seeNewOrders.add(q, 2, 0);
                                        for (
                                                int i = 0; i < newOrdersToShow.size(); i++) {
                                            String orderID = Integer.toString(newOrdersToShow.get(i).getOrder_ID());
                                            seeNewOrders.add(new Text(orderID), 0, i + 1);
                                            seeNewOrders.add(new Text(newOrdersToShow.get(i).getCustomer_Name()), 1, i + 1);
                                            seeNewOrders.add(new Text(newOrdersToShow.get(i).getOrderDetail()), 2, i + 1);
                                        }
                                        centerContent.getChildren().

                                                add(seeNewOrders);
                                        g.refresh();

                                    }
                                });
                                rightView.getChildren().add(viewToConfirm);
                                g.refresh();
                            }
                        }
                ),
                //wait a second!
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public static void main(String[] args) throws SQLException {
        dh = new DataHandler();//connect to database via handler
        launch(args);//launch FX
    }
}
