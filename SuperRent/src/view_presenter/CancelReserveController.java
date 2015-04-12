/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.AdminModel;
import model.AppContext;
import model.CustomerModel;
import model.ManagerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class CancelReserveController extends AbstractController implements Initializable {

    @FXML
    private ComboBox<String> StatusCombobox;
   

    @FXML
    private TextField ConfNoTextField;

    
    @FXML
    private Button showButton;

    @FXML
    private Button showallButton;
    
    @FXML
    private Button cancelButton;

    @FXML
    private Label showValidator;

    @FXML
    private Label cancelLabel;
    
    @FXML
    private CheckBox statuscb;
    
    @FXML
    private CheckBox confnocb;
    
      

    @FXML
    private VBox ReserveVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    private CustomerModel cusModel = new CustomerModel();
    

    String username,status,cancelreserve;
    int conformationno;
    String cnf_reserve,status_reserve;
    
    ArrayList statusarraylist = new ArrayList();
    
    boolean statusOK,statusselected;
    boolean confnoOK,confnoselected;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username = AppContext.getInstance().getUsername();
         hide(showValidator);
        setUpCheckBox();
        
   }

    private void setUpCheckBox()
    {
        
        statusarraylist.add("Pending");
        statusarraylist.add("Rented");
        statusarraylist.add("Expired");
        statusarraylist.add("Cancelled");
        
        configureComboBox(StatusCombobox, statusarraylist);
        
      statuscb.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
            Boolean old_val, Boolean newValue) {
            
            if (statuscb.isSelected()==true) {
                //System.out.println("Selected");
                StatusCombobox.setDisable(false);
                
                              
            }

            //when user is entering something into the namefield, remove the validation information
            if (statuscb.isSelected()==false) {
                //System.out.println("Not Selected");
                hide(showValidator);
                //showWarning(showValidator,"");
                StatusCombobox.setDisable(true);
                StatusCombobox.getSelectionModel().clearSelection();
              
            }
                
        }
    });
       
       confnocb.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
            Boolean old_val, Boolean newValue) {
            
            if (confnocb.isSelected()==true) {
                //System.out.println("Selected");
                ConfNoTextField.setDisable(false);
                //setUpConfirmationNoField();
             }

            //when user is entering something into the namefield, remove the validation information
            if (confnocb.isSelected()==false) {
                //System.out.println("Not Selected");
                ConfNoTextField.setDisable(true);
                ConfNoTextField.clear();
                hide(showValidator);
                showWarning(showValidator,"");
            }
                
        }
    });
        
       
       
           
              
        
    }
    
    
  /*  private void setUpStatusCMB() {
 
        //statusarraylist.add("");
        
        //StatusCombobox.getSelectionModel().selectFirst();
        
        StatusCombobox.setOnAction((ActionEvent event) -> {
            
            //System.out.println("Status"+);
            

        });
    }*/
    
    private void setUpConfirmationNoField() {
        ConfNoTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(ConfNoTextField)) {
                    showWarning(showValidator, "Confirmation No can't be empty!");
                    confnoOK = false;
                    confnoselected=false;
                } else {
                    
                    if(isInputInteger(ConfNoTextField)==true)
                    {
                    confnoOK = true;
                    confnoselected=true;
                    //conformationno=Integer.parseInt(ConfNoTextField.getText());
                    //System.out.println("Confirmation Number"+conformationno);
                    }
                    else 
                    {
                    showWarning(showValidator, "Confirmation No should be a Number!");
                    confnoOK = false;
                    confnoselected=false;
                    }
                    
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(showValidator);
            }
        });
    }
    
    
    

    
    @FXML
    private void handleShowAllButtonAction() throws IOException, SQLException {
        if (tableContent != null) {
            ReserveVbox.getChildren().remove(tableContent);
        }
 
        boolean result =cusModel.isReservationExist(username);
        
        if(result==false)
            popUpError("No Data Found !");
        else
        {
            
        
        tableContent = cusModel.getallReservations(username);
        ReserveVbox.getChildren().add(tableContent);
         tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    cancelreserve = tableContent.getSelectionModel().getSelectedItem().toString();
                    
                      cnf_reserve = cancelreserve.split(",")[0].substring(1);

                      status_reserve = cancelreserve.split(",")[7].split("]")[0].trim();
                     
                     if(status_reserve.equals("pending"))
                     {
                         cancelButton.setDisable(false);
                     }
                     else
                     {
                         cancelButton.setDisable(true);
                     }
                     
                    // System.out.println("Confrm "+cancelreserve+"Status "+status_reserve);//"cancelreserve);
                }
            }
        });
        }
    }
    
    @FXML
    private void handleShowButtonAction() throws IOException, SQLException {
        if (tableContent != null) {
            ReserveVbox.getChildren().remove(tableContent);
        }

        boolean s1=statuscb.isSelected();
        boolean s2=confnocb.isSelected();
        
        System.out.println("Ammu "+s1+" "+s2);
        
        
        if(s1==true && s2==true )
        {
            System.out.println("HiHello");
        if(StatusCombobox.getSelectionModel().isEmpty())
                {
                showWarning(showValidator,"Status can't be empty!");
                statusOK=false;
                statusselected=false;
                }
               else 
               {
                hide(showValidator);
                showWarning(showValidator,"");
                status = StatusCombobox.getSelectionModel().getSelectedItem().toString();
                statusOK=true;
                statusselected=true;
               }
        }
        
        else if(s1==true)
        {
            System.out.println("Hi");
                if(StatusCombobox.getSelectionModel().isEmpty())
                {
                showWarning(showValidator,"Status can't be empty!");
                statusOK=false;
                statusselected=false;
                }
               else 
               {
                hide(showValidator);
                showWarning(showValidator,"");
                status = StatusCombobox.getSelectionModel().getSelectedItem().toString();
                statusOK=true;
                statusselected=true;
               }
        }
        
        else if(s2==true)
        {
            System.out.println("Hello");
            ConfNoTextField.requestFocus();
        setUpConfirmationNoField();
        }
        
        System.out.println("Ammu2 "+s1+" "+s2+" "+statusOK+" "+confnoOK);
        
        if(statusOK==true && confnoOK==true && s1==true && s2==true)
        {        
            
        conformationno=Integer.parseInt(ConfNoTextField.getText());
        boolean result =cusModel.isReservationbyCNSExist(username,conformationno,status.toLowerCase());
        
        if(result==false)
            popUpError("No Data Found !");
        else
        {
            
        
        tableContent = cusModel.getReservationsbyCNS(username,conformationno,status.toLowerCase());
        ReserveVbox.getChildren().add(tableContent);
         tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    //soldLabel.setVisible(false);
                    cancelreserve = tableContent.getSelectionModel().getSelectedItem().toString();
                    
                     cnf_reserve = cancelreserve.split(",")[0].substring(1);

                      status_reserve = cancelreserve.split(",")[7].split("]")[0].trim();
                     
                     if(status_reserve.equals("pending"))
                     {
                         cancelButton.setDisable(true);
                     }
                    
                    
                    
                }
              }
            });
        }
        }
               
        
        else if(statusOK==true && s1==true)
        {        
            
            
        boolean result =cusModel.isReservationbyStatusExist(username, status.toLowerCase());
        
        if(result==false)
            popUpError("No Data Found !");
        else
        {
            
        
        tableContent = cusModel.getReservationsbyStatus(username,status.toLowerCase());
        ReserveVbox.getChildren().add(tableContent);
         tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    cancelreserve = tableContent.getSelectionModel().getSelectedItem().toString();
                     cnf_reserve = cancelreserve.split(",")[0].substring(1);

                     status_reserve = cancelreserve.split(",")[7].split("]")[0].trim();
                     
                     if(status_reserve.equals("pending"))
                     {
                         cancelButton.setDisable(true);
                     }
                    
                }
              }
            });
        }
        }
        
        else if(confnoOK==true && s2==true)
        {        
            
        conformationno=Integer.parseInt(ConfNoTextField.getText());
        boolean result =cusModel.isReservationbyCNExist(username, conformationno);
        
        if(result==false)
            popUpError("No Data Found !");
        else
        {
            
        conformationno=Integer.parseInt(ConfNoTextField.getText());
        tableContent = cusModel.getReservationsbyCN(username, conformationno);
        ReserveVbox.getChildren().add(tableContent);
         tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                   cancelreserve = tableContent.getSelectionModel().getSelectedItem().toString();
                  cnf_reserve = cancelreserve.split(",")[0].substring(1);

                  status_reserve = cancelreserve.split(",")[7].split("]")[0].trim();
                 
                 if(status_reserve.equals("pending"))
                     {
                         cancelButton.setDisable(true);
                     }
                }
              }
            });
        }
        }
        
            
        

       

    }

    @FXML
    private void handleCancelReserveButtonAction() throws IOException, SQLException {
        
        
        if(cusModel.cancelreservation(username,Integer.parseInt(cnf_reserve),status_reserve))
        {
            showSuccessMessage(cancelLabel, "Reservation has been cancelled!");
            handleShowAllButtonAction();
            
        }
        else {

            showWarning(cancelLabel, "Reservation was not cancelled!");
         }
        
        
        
    }

    
    
}
