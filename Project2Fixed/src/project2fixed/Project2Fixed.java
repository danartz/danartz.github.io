/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2fixed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.Collections;


/**
 *
 * @author dan
 */
public class Project2Fixed extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Media m2 = new Media("Bob", "test");
        //inventory.add(m2);
        // System.out.println(inventory.get(0));
        // If file doesn't exist create one and begin writing to it instead
        Label fileErrorLabel = new Label();
        ArrayList<Media> inventory = new ArrayList<>();
        final DatePicker datePicker = new DatePicker();

        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(new File("inventory.bin")));
            inventory = (ArrayList<Media>) ois.readObject();
            fileErrorLabel.setText("inventory.bin loaded..");
        } catch (IOException e) {
            fileErrorLabel.setText("File not found!.. Creating \n" + "inventory.bin");
        } catch (java.lang.ClassNotFoundException nf) {
            fileErrorLabel.setText("class not found!");
        }

        ObservableList<Media> inventoryGraphical = FXCollections.observableArrayList(inventory);
        ListView<Media> inventoryList = new ListView<Media>();
        inventoryList.setItems(inventoryGraphical);
        
        // Default sort is set to Alphabetical sorting - before a sort type is selected
        Collections.sort(inventoryGraphical, new MediaNameComparator());

        // Creating different assets
        primaryStage.setTitle("Media Collection");
        BorderPane bpane = new BorderPane();
        ScrollBar scrollVert = new ScrollBar();
        scrollVert.setOrientation(Orientation.VERTICAL);
        VBox rightContent = new VBox(12);
        VBox buttonContent = new VBox(15);
        RadioButton titleSort = new RadioButton("By title");
        RadioButton dateSort = new RadioButton("By date loaned");
        ToggleGroup sortGroup = new ToggleGroup();
        titleSort.setToggleGroup(sortGroup);
        dateSort.setToggleGroup(sortGroup);
        Label sortLabel = new Label("Sort");
        GridPane nameForm = new GridPane();
        TextField title = new TextField();
        TextField format = new TextField();
        Label titleLabel = new Label("Title: ");
        Label formatLabel = new Label("Format: ");

        // Setting locatino of assets in grid
        Button addItem = new Button("Add");
        addItem.setMaxSize(40, 15);
        addItem.setPrefSize(40, 15);
        nameForm.add(titleLabel, 0, 0);
        nameForm.add(title, 1, 0);
        nameForm.add(formatLabel, 0, 1);
        nameForm.add(format, 1, 1);
        nameForm.add(addItem, 0, 2);
        rightContent.setPadding(new Insets(20, 20, 20, 20));
        GridPane loanForm = new GridPane();
        loanForm.setHgap(5);
        loanForm.setVgap(10);
        TextField loaned = new TextField();
        TextField loanedDate = new TextField();
        Label loanedTo = new Label("Loaned To: ");
        Label loanedD = new Label("Loaned On: ");
        Button loanB = new Button("Loan");
        loanB.setMaxSize(50, 15);
        loanB.setPrefSize(50, 15);
        loanForm.add(loanedTo, 0, 0);
        loanForm.add(loaned, 1, 0);
        loanForm.add(loanedD, 0, 1);
        loanForm.add(datePicker, 1, 1);
        loanForm.add(loanB, 0, 2);
        Button remove = new Button("Remove");
        remove.setMaxSize(60, 15);
        remove.setPrefSize(60, 15);
        Button returnButton = new Button("Return");
        returnButton.setMaxSize(60, 15);
        returnButton.setPrefSize(60, 15);
        nameForm.setHgap(5);
        nameForm.setVgap(10);

        // Styling
        titleLabel.setStyle("-fx-text-fill: #003399; -fx-font-weight: bold;");
        formatLabel.setStyle("-fx-text-fill: #003399; -fx-font-weight: bold;");
        nameForm.setPadding(new Insets(15, 15, 15, 15));
        loanForm.setPadding(new Insets(15, 15, 15, 15));
        nameForm.setStyle("-fx-border-width: 2px; -fx-border-color: #ffad33; -fx-background-color: #ffffff; "
                + " -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        loanForm.setStyle("-fx-border-width: 2px; -fx-border-color: #ffad33;  -fx-border-radius: 10 10 10 10;"
                + " -fx-background-radius: 10 10 10 10;  -fx-background-color: #ffffff;");
        loanedTo.setStyle("-fx-text-fill: #003399; -fx-font-weight: bold;");
        loanedD.setStyle("-fx-text-fill: #003399; -fx-font-weight: bold;");
        fileErrorLabel.setStyle("-fx-text-fill: #ff3300; -fx-background-color: #ffffff;");
        sortLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold;");
        titleSort.setStyle("-fx-text-fill: #ffffff;");
        dateSort.setStyle("-fx-text-fill: #ffffff;");
        fileErrorLabel.setPadding(new Insets(2, 2, 2, 2));
        buttonContent.setPadding(new Insets(0, 0, 0, 10));
        addItem.setStyle("-fx-background-color: #ffad33; -fx-text-fill: #003399");
        loanB.setStyle("-fx-background-color: #ffad33; -fx-text-fill: #003399");
        

        // Load right of GUI
        rightContent.setStyle("-fx-background-color: #003399;");
        rightContent.getChildren().add(fileErrorLabel);
        rightContent.getChildren().add(nameForm);
        buttonContent.getChildren().add(remove);
        buttonContent.getChildren().add(returnButton);
        rightContent.getChildren().add(buttonContent);
        rightContent.getChildren().add(loanForm);
        rightContent.getChildren().add(sortLabel);
        rightContent.getChildren().add(titleSort);
        rightContent.getChildren().add(dateSort);

        bpane.setRight(rightContent);
        bpane.setCenter(inventoryList);
        bpane.setStyle("-fx-background-color: #ffffff;");

        // Add Item
        addItem.setOnAction(e -> {
            String titleString = title.getText();
            String formatString = format.getText();
            addItem(inventoryGraphical, titleString, formatString);
            title.clear();
            format.clear();
            if (titleSort.isSelected()) { // sorts based on which sort is selected
                Collections.sort(inventoryGraphical, new MediaNameComparator());
            }else if (dateSort.isSelected()) {
                Collections.sort(inventoryGraphical, new MediaDateComparator());
            }else{ // if no sort type is selected default is alphabetical
                Collections.sort(inventoryGraphical, new MediaNameComparator());
            }
            
            inventoryList.refresh();
        });

        inventory.addAll(inventoryGraphical);

        // On quit event, saves file
        ArrayList<Media> inventoryCopy = new ArrayList<>();
        primaryStage.setOnCloseRequest(e -> {
            copyArrayList(inventoryGraphical, inventoryCopy);
            try {  // writes to binary file called inventory.bin
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(new File("inventory.bin")));
                oos.writeObject(inventoryCopy);
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Remove event
        remove.setOnAction(e -> {
            int select = inventoryList.getSelectionModel().getSelectedIndex();
            // blocks user from clicking remove without anything selected
            if (select != -1) {
                inventoryGraphical.remove(select);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Media Collection");
                alert.setContentText("No item selected!");
                alert.show();
            }
        });

        //Loan to lamda
        loanB.setOnAction(e -> {
            String loanedName = loaned.getText();
            loanTo(inventoryGraphical, inventoryList, datePicker, loanedName);
            loaned.clear();
            if (titleSort.isSelected()) { // sorts based on which sort is currently selected
                Collections.sort(inventoryGraphical, new MediaNameComparator());
            }else if (dateSort.isSelected()) {
                Collections.sort(inventoryGraphical, new MediaDateComparator());
            }
            inventoryList.refresh();
        });

        // Return item event
        returnButton.setOnAction(e -> {
            int select = inventoryList.getSelectionModel().getSelectedIndex();
            if (select != -1) {

                if (inventoryGraphical.get(select).getOnLoan() == false) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Media Collection");
                    alert.setContentText("This item isn't loaned out!");
                    alert.show();
                }
                inventoryGraphical.get(select).setLoan(false);
                inventoryList.refresh();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Media Collection");
                alert.setContentText("No item selected!");
                alert.show();
            }
            
            if (titleSort.isSelected()) {
                Collections.sort(inventoryGraphical, new MediaNameComparator());
            }else if (dateSort.isSelected()) {
                Collections.sort(inventoryGraphical, new MediaDateComparator());
            }

        });
        
        // Radio button select sort type
        // Alpha name sort
        titleSort.setOnAction(e -> {

            Collections.sort(inventoryGraphical, new MediaNameComparator());
            inventoryList.refresh();

        });

        // Date sort
        dateSort.setOnAction(e -> {
            Collections.sort(inventoryGraphical, new MediaDateComparator());
            inventoryList.refresh();
        });

        Scene scene = new Scene(bpane, 800, 800);

        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Created this function to copy the observable list to an empty arrayList to then save to a file
    public static void copyArrayList(ObservableList<Media> inventoryGraphical, ArrayList<Media> inventoryCopy) {
        inventoryCopy.addAll(inventoryGraphical);
    }

    // Loan to function
    public static void loanTo(ObservableList<Media> inventoryGraphical, ListView<Media> inventoryList, final DatePicker datePicker, String loanedName) {
        int select = inventoryList.getSelectionModel().getSelectedIndex();
        LocalDate dateOrig = datePicker.getValue();
        if (select != -1) {
            if (dateOrig != null && inventoryGraphical.get(select).getOnLoan() != true && !loanedName.trim().equals("")) {
                inventoryGraphical.get(select).setDateLoaned(dateOrig, loanedName);
                inventoryGraphical.get(select).setLoan(true);
                inventoryList.refresh();
            } else if (inventoryGraphical.get(select).getOnLoan() == true) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Media Collection");
                alert.setContentText("That title is already loaned out, please choose another.");
                alert.show();
            } else if (dateOrig == null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Media Collection");
                alert.setContentText("No date selected!");
                alert.show();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Media Collection");
                alert.setContentText("Loaned to field empty!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Media Collection");
            alert.setContentText("No item selected!");
            alert.show();
        }

    }

    // Add item function, searches if the item already exists of the same format type
    // This allows users to have multiple formats eg Tomb Raider DVD and Tomb Raider Xbox game
    public static void addItem(ObservableList<Media> inventoryGraphical, String itemName, String itemFormat) {

        // ask if this has to be a try catch
        boolean existsFlag = false;
        for (int i = 0; i < inventoryGraphical.size(); i++) {
            if (inventoryGraphical.get(i).getItemName().toUpperCase().equals(itemName.toUpperCase())  && 
                   inventoryGraphical.get(i).getItemFormat().toUpperCase().equals(itemFormat.toUpperCase()) ){
                // System.out.println("Item already exists, try another name.");
                existsFlag = true;
                break;
            }
        }
        if (existsFlag == true) { // if file already exists function exits and true value returned
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Media Collection");
            alert.setContentText("That title already exists in the media collection!");
            alert.show();
        } else if (itemName.trim().equals("")) { // otherwise item is added to array list and false value is returned to halt the loop
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Media Collection");
            alert.setContentText("The title field is empty!");
            alert.show();
        } else if (itemFormat.trim().equals("")) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Media Collection");
            alert.setContentText("The format field is empty!");
            alert.show();

        } else {
            Media m1 = new Media(itemName, itemFormat);
            inventoryGraphical.add(m1);
        }

    }

}
