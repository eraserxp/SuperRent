/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AppContext;
import model.ValidationResult;

/**
 *
 *
 */
public abstract class AbstractController {

    private Stage attachedStage;

    private AbstractController previousPageController;

    public void setPreviousController(AbstractController controller) {
        previousPageController = controller;
    }

    public AbstractController getPreviousController() {
        return previousPageController;
    }

    //update the current view
    public void update(Object o) {

    }

    /**
     * pass an object and use previous controller to update previous view
     *
     * For this to work, you need to cast the object o to a specific object that
     * the previousPageController can recognize and make use of. So the current
     * page and the next page must both know the actual type of the object.
     */
    public void updatePreviousPage(Object o) {
        previousPageController.update(o);
    }

    protected void setStage(Stage stage) {
        attachedStage = stage;
    }

    protected Stage getStage() {
        return attachedStage;
    }

    //create next stage based on the given fxml
    protected void setupNextPage(AbstractController currentController, String fxml, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        try {
            AnchorPane page = (AnchorPane) loader.load();
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(AppContext.getInstance().getPrimaryStage());
            Scene scene = new Scene(page);
            newStage.setScene(scene);

            AbstractController controller = loader.getController();
            controller.setStage(newStage);
            controller.setPreviousController(this);
            newStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ReserveRentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean isInputEmpty(TextField t) {
        return (t.getText() == null || t.getText().trim().length() == 0);
    }

    protected boolean isInputEmpty(PasswordField t) {
        return (t.getText() == null || t.getText().trim().length() == 0);
    }

    protected boolean isInputTooLong(TextField t, int maxsize) {
        return t.getText().trim().length() > maxsize;
    }

    protected boolean isInputTooLong(PasswordField t, int maxsize) {
        return t.getText().trim().length() > maxsize;
    }

    protected boolean areTwoInputsMatch(PasswordField p1, PasswordField p2) {
        return p1.getText().trim().equals(p2.getText().trim());
    }

    protected boolean isInputLength(TextField t, int size) {
        return t.getText().trim().length() == size;
    }

    /**
     * Check if the phone number is valid (must be 10 digits, and in some common
     * formats)
     *
     * See the link for details:
     * http://howtodoinjava.com/2014/11/12/java-regex-validate-and-format-north-american-phone-numbers/
     *
     * @param t
     * @return
     */
    protected boolean isInputPhoneNo(TextField t) {
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(t.getText().trim());
        return matcher.matches();
    }

    protected String formatPhoneNo(String validPhoneNo) {
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(validPhoneNo);
        return matcher.replaceFirst("$1-$2-$3");
    }

    protected boolean isCardNo(TextField t, String cardtype) {

        boolean valid = false;

        if (cardtype == "MasterCard") {
            valid = isMasterCardNo(t);
        } else if (cardtype == "Visa") {
            valid = isVisaCardNo(t);
        } else if (cardtype == "American Express") {
            valid = isAmericanCardNo(t);
        }

        return valid;
    }

    protected boolean isVisaCardNo(TextField t) {
        String regex_visa = "^4[0-9]{12}(?:[0-9]{3})?$";
        Pattern pattern = Pattern.compile(regex_visa);
        Matcher matcher = pattern.matcher(t.getText().trim());
        return matcher.matches();

    }

    protected boolean isMasterCardNo(TextField t) {
        String regex_master = "^5[1-5][0-9]{14}$";
        Pattern pattern = Pattern.compile(regex_master);
        Matcher matcher = pattern.matcher(t.getText().trim());
        return matcher.matches();

    }

    protected boolean isAmericanCardNo(TextField t) {
        String regex_america = "^3[47][0-9]{13}$";
        Pattern pattern = Pattern.compile(regex_america);
        Matcher matcher = pattern.matcher(t.getText().trim());
        return matcher.matches();

    }

    protected boolean isExpiryDateNo(TextField t) {
        String regex = "^(0[1-9]|1[012])[- /.](20)\\d\\d$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(t.getText().trim());
        return matcher.matches();
    }

    /**
     * Use the infoLabel to show the warning message s
     *
     * @param infoLabel
     * @param s
     */
    protected void showWarning(Label infoLabel, String s) {
        infoLabel.setVisible(true);
        infoLabel.setTextFill(Color.RED);
        infoLabel.setText(s);
//        infoLabel.setWrapText(true);
    }

    protected void showSuccessMessage(Label infoLabel, String s) {
        infoLabel.setVisible(true);
        infoLabel.setTextFill(Color.GREEN);
        infoLabel.setText(s);
    }

    protected void configureComboBox(ComboBox cmb, ArrayList<String> choices) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (String c : choices) {
            items.add(c);
        }
        cmb.setItems(items);
    }

    protected void configureHourCMB(ComboBox cmb) {
        ArrayList<String> workHours = new ArrayList<>();
        for (int i = 6; i <= 21; i++) {
            workHours.add(Integer.toString(i) + ":00");
        }
        configureComboBox(cmb, workHours);
    }

    /**
     * checks if the text in the two password fields is a valid password and
     * prints out the corresponding warning in infoLabel
     *
     * @param passwordField
     * @param repasswordField
     * @param infoLabel
     * @return
     */
    protected boolean checkTwoPasswdFields(PasswordField passwordField, PasswordField repasswordField,
            Label infoLabel) {
        boolean passwordValid = false;
        if (isInputEmpty(passwordField)) {
            showWarning(infoLabel, "Password can't be empty!");
        } else if (isInputTooLong(passwordField, 20)) {
            showWarning(infoLabel, "Password is too long!");
        } else if (!areTwoInputsMatch(passwordField, repasswordField)) {
            showWarning(infoLabel, "Password doesn't match!");
        } else {
            passwordValid = true;
        }
        return passwordValid;
    }

    protected void setUpPasswordField(PasswordField passwordField, PasswordField repasswordField,
            Label infoLabel, ValidationResult validationResult) {

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                    validationResult.set(true);
                } else {
                    validationResult.set(false);
                }
            }

            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });

        repasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                    validationResult.set(true);
                } else {
                    validationResult.set(false);
                }
            }
            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });

    }

    protected void setUpUsernameField_checkEmpty(TextField usernameField, Label infoLabel,
            ValidationResult validationResult) {
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                //validate the username
                if (isInputEmpty(usernameField)) {
                    showWarning(infoLabel, "Username can't be empty!");
                    validationResult.set(false);
                } else {
                    validationResult.set(true);
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                hide(infoLabel);
            }
        });
    }

    protected void popUpError(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
//        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    protected void popUpMessage(String infoMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        //alert.setTitle("");
//        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(infoMessage);

        alert.showAndWait();
    }

    protected int daysBetween(LocalDate fromDate, LocalDate toDate) {
        return (int) ChronoUnit.DAYS.between(fromDate, toDate);
    }

    /**
     * clear and clearAndDisable a combobox
     *
     * @param c
     */
    protected void clearAndDisable(ComboBox c) {
        c.valueProperty().set(null);
        c.setDisable(true);
    }

    protected void hide(Node... nodes) {
        for (Node n : nodes) {
            n.setVisible(false);
        }
    }

    protected void show(Node... nodes) {
        for (Node n : nodes) {
            n.setVisible(true);
        }
    }

    protected void clearText(TextField... tfs) {
        for (TextField t : tfs) {
            t.clear();
        }
    }

    protected void disableNodes(Node... nodes) {
        for (Node n : nodes) {
            n.setDisable(true);
        }
    }

    //check for the input if its integer
    protected boolean isInputInteger(TextField t) {
        

        return t.getText().trim().matches("^[0-9]+$");

    }

    protected void enableNodes(Node... nodes) {
        for (Node n : nodes) {
            n.setDisable(false);
        }
    }

    protected void clearLabels(Label... labels) {
        for (Label l : labels) {
            l.setText("");
        }
    }

    protected void unSelect(CheckBox... cbs) {
        for (CheckBox cb : cbs) {
            cb.setSelected(false);
        }
    }

    protected LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

}
