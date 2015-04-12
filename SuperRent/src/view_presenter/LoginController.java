/**
 *
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.AppContext;
import model.LoginModel;

/**
 *
 *
 */
public class LoginController extends AbstractController implements Initializable {

    String user_type;
    
 
    @FXML
    private Button register_button;
    
    @FXML
    private Button login_button;
    
    @FXML
    private Button clear_button;
    
     @FXML
    private Label usernameValidator;
      @FXML
    private Label passwordValidator;
       @FXML
    private Label credentialValidator;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
     String username,password,type;
     
     private LoginModel loginModel = new LoginModel();
     
     boolean valid;
     
    private boolean usernameOK = false;
    private boolean passwordOK = false;
    
    
      private void setUpUserField() {
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
             if (!newValue) {
             if (isInputEmpty(usernameField)) {
                    showWarning(usernameValidator, "Username can't be empty!");
                    usernameOK = false;
                    
                } else {
                    usernameOK = true;
                    
                }
             }
            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(usernameValidator);
                hide(passwordValidator);
            }
        });
    }
       private void setUpPassField() {
        passwordField.focusedProperty().addListener((observable1, oldValue1, newValue1) -> {
            // when user enter some text and leave the password field, check the password
             if (!newValue1) {
             if (isInputEmpty(passwordField)) {
                    showWarning(passwordValidator, "Password can't be empty!");
                    passwordOK = false;
                    
                } else {
                    passwordOK = true;
                    
                }
             }
            //when user is entering something into the namefield, remove the validation information
            if (newValue1) {
                hide(passwordValidator);
               
            }
        });
    }

       public void start(Stage stage) throws Exception {
           
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    
    @FXML
    private void handleloginButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Parent next_page_parent = null;
        
        usernameField.requestFocus();
        passwordField.requestFocus();
        login_button.requestFocus();
               
        if(usernameOK==true && passwordOK==true)
        {
        username=usernameField.getText();
        password=passwordField.getText();
        
        valid=loginModel.isValidCredentials(username,password);
        //System.out.println("Valid="+valid);
       
         
        
        if (valid==true) 
        {
            type=loginModel.getUserCredentials(username,password);          
            user_type=type.toUpperCase();
            // switch to different pages according to the type of the user
            switch (user_type) {
                case "CUSTOMER":
                    AppContext.getInstance().setUsername(username);
                    AppContext.getInstance().setUserType(user_type);
                  next_page_parent = FXMLLoader.load(getClass().getResource("CustomerOverview.fxml"));
                    break;
                case "CLERK":
                    // set the enxt page to be the page for clerk
                    AppContext.getInstance().setUsername(username);
                    AppContext.getInstance().setUserType(user_type);
                    next_page_parent = FXMLLoader.load(getClass().getResource("ClerkOverview.fxml"));
                    break;
                case "MANAGER":
                    // set the enxt page to be the page for manager
                     AppContext.getInstance().setUsername(username);
                    AppContext.getInstance().setUserType(user_type);
                    next_page_parent = FXMLLoader.load(getClass().getResource("ManagerOverview.fxml"));
                    break;
                case "ADMIN":
                    // set the enxt page to be the page for Aadministrator
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
//                    AdminViewController controller = loader.getController();
//                    controller.setStage(app_stage);
//                    next_page_parent = loader.load();
                    
                    AppContext.getInstance().setUsername(username);
                    AppContext.getInstance().setUserType(user_type);
                    next_page_parent = FXMLLoader.load(getClass().getResource("AdminOverview.fxml"));
                    break;
            }

            Scene next_page_scene = new Scene(next_page_parent);

            app_stage.setScene(next_page_scene);
            app_stage.show();
        } 
        
        else
        {
            //System.out.println("Else part");
            this.credentialValidator.setVisible(true);
            this.credentialValidator.setText("Invalid Credentials");
        //credentialcheck();
        /*Parent root = FXMLLoader.load(getClass().getResource("Invalid.fxml"));
        Scene scene = new Scene(root);
        app_stage.setScene(scene);
        app_stage.show();*/
                   
        }
        
        }
    }

    @FXML
    private void handleregisterButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
//        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        app_stage.hide();
//               
//        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
//        Scene scene = new Scene(root);
//        app_stage.setScene(scene);
//        app_stage.show();
        setupNextPage(this, "Register.fxml", "Customer Registration");
        
        
    }
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUpManage();
        
         clear_button.setOnAction((event) -> {
    // Button was clicked, do something...
         clearText(usernameField, passwordField);
          hide(usernameValidator, passwordValidator,credentialValidator);
      });
        
    }

    
    private void setUpManage() {

        //set all validator labels to be invisible
        hide(usernameValidator, passwordValidator,credentialValidator);
        //clear the text fields
        clearText(usernameField,passwordField);
        //disable the three buttons

         setUpUserField();
         setUpPassField();
         
          

    }
    
    
    
}
