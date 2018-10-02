package controllers;

import models.ModelMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Azaelmglw
 */

public class ControllerMain implements Initializable {
    
    private final ModelMain model_main;
    
    public ControllerMain(ModelMain model_main){
        this.model_main = model_main;
    }
         
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name_tfield.focusedProperty().addListener((observable) -> {
            if (name_tfield.getTextFormatter() == null) {
                System.out.println("First Name container doesn't have a TextFormatter currently.");
                name_tfield.setTextFormatter(model_main.getTextFormatter(0));
            }
        });
    }
    
    @FXML
    private TextField name_tfield;
    
    @FXML
    private TextField email_tfield;
    
    @FXML TextArea change_display_txtarea;
   
    @FXML private void NewElement(){
        name_tfield.setText("");
        email_tfield.setText("");
    }
    
    @FXML private void AddValue(){
        SetValues();
        model_main.AddElement();
        change_display_txtarea.setText(model_main.getResultDocument());
    }
    
    @FXML
    private void SaveFile(ActionEvent event) {
        model_main.SaveFile();
    }
    
    @FXML
    
    private void SetValues(){
        model_main.setName(name_tfield.getText());
        model_main.setEmail(email_tfield.getText());
    }
}