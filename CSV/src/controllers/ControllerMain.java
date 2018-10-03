package controllers;

import models.ModelMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonType;
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
    
    @FXML
    private void OpenFile(ActionEvent event) {
        if(model_main.getDocumentAux().length() > 1){
            model_main.SaveCurrentChangesConfirmationRequest();
            if (model_main.getResult().get() == ButtonType.YES) {
                System.out.println("user's confirmation choice: YES");
                SaveFile(event);
                model_main.ReadFile();
                
                model_main.setResultDocument("");
                OpenFile(event);
            } 
            else if (model_main.getResult().get() == ButtonType.NO) {
                System.out.println("user's confirmation choice: NO");
                model_main.ReadFile();
                FirstValue(event);
            } 
            else if (model_main.getResult().get() == ButtonType.CANCEL) {
                System.out.println("user's confirmation choice: CANCEL");
            }
        }
        else{
            model_main.ReadFile();
            name_tfield.setTextFormatter(null);
            FirstValue(event);
        }
    }
    
    @FXML
    private void SaveFile(ActionEvent event) {
        model_main.WriteFile();
    }
    
    @FXML
    private void AddValue(ActionEvent event){
        SetValues();
        model_main.AddElement();
        name_tfield.setTextFormatter(null);
        FirstValue(event);
    }
    
    @FXML private void ClearInputFields(ActionEvent event){
        name_tfield.setText("");
        email_tfield.setText("");
    }
    
    @FXML private void LastValue(ActionEvent event){
        name_tfield.setTextFormatter(null);
        name_tfield.setText("" + model_main.getUserNames().get(model_main.getUserNames().size() - 1));
        email_tfield.setText("" + model_main.getUserEmails().get(model_main.getUserEmails().size() - 1));
        model_main.setCursorPosition(model_main.getUserNames().size() - 1);
    }
    
    @FXML private void PreviousValue(ActionEvent event){
        name_tfield.setTextFormatter(null);
        model_main.setCursorPosition(model_main.getCursor() - 1);
        if (model_main.getCursor() < 0) {
            model_main.setCursorPosition(model_main.getCursor() + 1);
        }
        name_tfield.setText("" + model_main.getUserNames().get(model_main.getCursor()));
        email_tfield.setText("" + model_main.getUserEmails().get(model_main.getCursor()));
    }
    
    @FXML private void NextValue(ActionEvent event){
        name_tfield.setTextFormatter(null);
        model_main.setCursorPosition(model_main.getCursor() + 1);
        if(model_main.getCursor() > model_main.getUserNames().size() - 1){
            model_main.setCursorPosition(model_main.getCursor() - 1);
        }
        name_tfield.setText("" + model_main.getUserNames().get(model_main.getCursor()));
        email_tfield.setText("" + model_main.getUserEmails().get(model_main.getCursor()));
    }
    
    @FXML private void FirstValue(ActionEvent event){
        name_tfield.setTextFormatter(null);
        name_tfield.setText("" + model_main.getUserNames().get(0));
        email_tfield.setText("" + model_main.getUserEmails().get(0));
        model_main.setCursorPosition(0);
    }
    
    @FXML
    private void SetValues(){
        model_main.setName(name_tfield.getText());
        model_main.setEmail(email_tfield.getText());
    }
}