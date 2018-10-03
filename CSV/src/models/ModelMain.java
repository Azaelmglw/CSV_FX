package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Azaelmglw
 */

public class ModelMain {
    /*  Parents array list position:
    [0] -> main    |
    */
    
    /*  File Choosers array list position:
    [0] -> text_file_chooser    |
    */
    
    /*  Text Fomratters array  list position:
    [0] -> name_formatter   |
    */
    
    /*  Alerts array list position:
    [0] -> confirmation_alert  |    [1] -> error_alert  |
    */
    
    /*  User input array list position:
    [0] -> name   |   [1] -> email  |
    */
    
    /*  Application output array list position:
    [0] -> result_document  |
    */
    
    private final Stage primaryStage;
    private List<Parent> parents = new ArrayList<>(5);
    private List<FileChooser> file_choosers = new ArrayList<>(5);
    private List<TextFormatter> text_formatters = new ArrayList<>(5);
    private List<Alert> alerts = new ArrayList<>(5);
    private List<String> user_input = new ArrayList<>(5);
    private List<String> app_output = new ArrayList<>();
    private List<String> user_names = new ArrayList<>();
    private List<String> user_emails = new ArrayList<>();
    
    private Optional <ButtonType> result;
    
    private String field_assigner = "";
    private String result_document_aux = "";
    private int cursor = 0;
    private File csv_file;
    private FileReader file_reader;
    private FileWriter file_writer;
    
    public ModelMain(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void AddElement(){
        if(getName().isEmpty() || getEmail().isEmpty()){
            getAlert(1).setHeaderText("No value to be added.");
            getAlert(1).showAndWait();
        }
        else if(!getEmail().contains("@")){
            getAlert(1).setHeaderText("Incorrect email.");
            getAlert(1).showAndWait();
        }
        else{
            result_document_aux += (getName() + "," + getEmail() + "\n");
            setResultDocument(result_document_aux);
            user_names.add(getName());
            user_emails.add(getEmail());
        }
    }
    
    
    public void ReadFile(){
        try{
            getFileChooser(0).setTitle("Select resource file");
            csv_file = getFileChooser(0).showOpenDialog(getPrimaryStage());
            file_reader = new FileReader(csv_file);
            user_names.clear();
            user_emails.clear();
            int character = 0;
            while ((character = file_reader.read()) != -1) {
                field_assigner += (char)character;
                result_document_aux += (char)character;
                if((char)character == ','){
                    user_names.add(field_assigner.replaceAll("," , ""));
                    field_assigner = "";
                }
                else if((char)character == '\n'){
                    user_emails.add(field_assigner);
                    field_assigner = "";
                }
            }
            setResultDocument(result_document_aux);
            file_reader.close();
        }
        catch(FileNotFoundException e){
            getAlert(1).setContentText("Err 000: File not Found:" + e);
        }
        catch(IOException e){
            getAlert(1).setContentText("Err 001: The file couldn't be read: " + e);
        }
    }
    
    public void WriteFile(){      
        try{
            getFileChooser(0).setTitle("Choose a path to save the file.");
            csv_file = getFileChooser(0).showSaveDialog(getPrimaryStage());
            file_writer = new FileWriter(csv_file, false);
            file_writer.write(getResultDocument());
            file_writer.close();
        }
        catch(IOException e){
            getAlert(0).setHeaderText("" + e);
        }
    }
    
    public void SaveCurrentChangesConfirmationRequest(){
        getAlert(0).setTitle("Confirmation Required");
        getAlert(0).setHeaderText("Save changes of the current file?");
        getAlert(0).setContentText("Choose one of the following options.");
        
        result = getAlert(0).showAndWait();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Parent getParent(int parent_position) {
        return parents.get(parent_position);
    }

    public void setParent(int parent_position, Parent parent) {
        this.parents.add(parent_position, parent);
    }    
    
    public FileChooser getFileChooser(int file_chooser_position){
        return file_choosers.get(file_chooser_position);
    }
    
    public void setFileChooser(int file_chooser_position, FileChooser file_chooser){
        this.file_choosers.add(file_chooser_position, file_chooser);
    }
    
    public TextFormatter getTextFormatter(int text_formatter_position){
        return text_formatters.get(text_formatter_position);
    }
    
    public void setTextFormatter(int text_formatter_position, TextFormatter text_formatter){
        this.text_formatters.add(text_formatter_position, text_formatter);
    }
    
    public Alert getAlert(int alert_position) {
        return alerts.get(alert_position);
    }

    public void setAlert(int alert_position, Alert alert) {
        this.alerts.add(alert_position, alert);
    }
    
    public String getName(){
        return user_input.get(0);
    }
    
    public void setName(String name){
        user_input.add(0, name);
    }
    
    public String getEmail(){
        return user_input.get(1);
    }
    
    public void setEmail(String email){
        this.user_input.add(1, email);
    }
    
    public String getResultDocument(){
        return app_output.get(0);
    }
    
    public void setResultDocument(String result_document){
        this.app_output.add(0, result_document);
    }
    
    public List getUserNames(){
        return user_names;
    }
    
    public List getUserEmails(){
        return user_emails;
    }
    
    public String getDocumentAux(){
        return result_document_aux;
    }
    
    public int getCursor(){
        return cursor;
    }
    
    public void setCursorPosition(int position){
        this.cursor = position;
    }
    
    public Optional getResult(){
        return result;
    }
}