package FinalAssignment.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import FinalAssignment.application.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;


//This is our fxml document controller. It takes the blueprint designed in our
//FXMLdocument to display an interface for the user. Upon intialization, it 
//prepares a viewable, iterable version of the current loaded database. 
//It stores the data of each clothing object from the database in an observable
//array. 

public class FXMLDocumentController implements Initializable{
    
    @FXML
    private Label label;
    
    @FXML
    Tab tab1, tab2;
    
    @FXML
    TabPane tabPane;
    
    @FXML
    AnchorPane rootPane;
    
    @FXML
    private ListView listView, listView2;
    
    ObservableList<Clothing> clothes;
    Database data = new Database();
    
    @FXML
    public void fileLoadEvent() throws Exception {
        data.ReadToDatabase("data.txt");
        clothes = FXCollections.observableArrayList(data.grabList());
        ObservableList<String> names = FXCollections.observableArrayList();
        for (int x = 0; x < clothes.size(); x++){
            names.add(clothes.get(x).getName() + "  -  " + clothes.get(x).getSerial());
            listView.getItems().add(names.get(x));
        }
    }
    //Here we have several button events designed to iterate through the viewable
    //list. That way the user can scroll through the listview, or use buttons to
    //go through it. 
    @FXML
    public void topButtonEvent(ActionEvent e) {
        listView.getSelectionModel().clearSelection();
        listView.getSelectionModel().selectFirst();
    }
    @FXML
    public void bottomButtonEvent(ActionEvent e) {
        listView.getSelectionModel().clearSelection();
        listView.getSelectionModel().selectLast();
    }
    @FXML
    public void upButtonEvent(ActionEvent e) {
        listView.getSelectionModel().selectPrevious();      
    }
    @FXML
    public void downButtonEvent(ActionEvent e) {
        listView.getSelectionModel().selectNext();   
    }
    //The select button method shown here displays the second of two tabs in
    //the program. This tab details specific information on a Clothing object.
    //The button determines what piece of clothing to view based on what the user
    //is currently selecting on the listview. 
    @FXML
    public void selectButtonEvent(ActionEvent e) {
        listView2.getItems().clear();
        String currentItem = (String) listView.getSelectionModel().getSelectedItem();
        tabPane.getSelectionModel().select(tab2);
        ObservableList<String> selection = FXCollections.observableArrayList();
        for (int x = 0; x < clothes.size(); x++){
            if ((clothes.get(x).getName() + "  -  " + clothes.get(x).getSerial()).equals(currentItem)){
                selection.add(clothes.get(x).getName());
                selection.add(clothes.get(x).getType());
                selection.add(Double.toString(clothes.get(x).getPrice()));
                selection.add(Boolean.toString(clothes.get(x).getAvailable()));
                selection.add(clothes.get(x).getSerial());
                selection.add(clothes.get(x).getSize());
                for (int y = 0; y < selection.size(); y++)
                    listView2.getItems().add(selection.get(y));
            }
        }       
    }
    //the back button method returns the user to tab 1. 
    @FXML
    public void backButtonEvent(ActionEvent e){
        tabPane.getSelectionModel().select(tab1);
    }
    //This method is for the edit button. It prompts the user to enter a password
    //so they may edit a clothing objects attributes. The user is asked to update
    //the availability, serial code, and price of the item. Once this is 
    //performed, the database is called and given data to write back to the
    //text file. 
    @FXML
    public void editButtonEvent(ActionEvent e) throws Exception{
        TextInputDialog password = new TextInputDialog("");
        password.setHeaderText("Admin Permission Required");
        password.setContentText("password is 'pass'");

        
        Optional<String> attempt = password.showAndWait();
        if (attempt.get().equals("pass")){

            ArrayList<String> options = new ArrayList<>();
            options.add("Yes");
            options.add("No");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("...", options);
            dialog.setHeaderText("Update Stock Below");
            dialog.setContentText("Is the item Available?");
            
            boolean flag = true;
            Optional<String> result = dialog.showAndWait();
            if (result.get().equals("No")){
                flag = false;
            }
            
            TextInputDialog price = new TextInputDialog(listView2.getItems().get(2).toString());
            price.setHeaderText("Update Price Below");
            price.setContentText("Enter Price ");
            Optional<String> result2 = price.showAndWait();
            
            TextInputDialog serial = new TextInputDialog(listView2.getItems().get(4).toString());
            serial.setHeaderText("Update Serial Below");
            serial.setContentText("Enter Serial ");
            Optional<String> result3 = serial.showAndWait();
            
            data.WriteFromDatabase(listView2.getItems().get(0).toString(),flag, 
                    Double.parseDouble(result2.get()), result3.get());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            fileLoadEvent();
        }
        catch(Exception e){
            System.out.println("Something went Wrong");
        }
    }
    
}        
