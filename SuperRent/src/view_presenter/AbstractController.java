/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 *
 */
public abstract class AbstractController {
    
 

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
     * Check if the phone number is valid (must be 10 digits, and in some common formats)
     * 
     * See the link for details:
     * http://howtodoinjava.com/2014/11/12/java-regex-validate-and-format-north-american-phone-numbers/
     * @param t
     * @return 
     */
    protected boolean isInputPhoneNo(TextField t) {
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
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
        infoLabel.setText(s);
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

    /**
     * checks if the text in the two password fields is a valid password and
     * prints out the corresponding warning in infoLabel
     *
     * @param passwordField
     * @param repasswordField
     * @param infoLabel
     * @return
     */
    protected boolean checkTwoPasswdFields(PasswordField passwordField, PasswordField repasswordField, Label infoLabel) {
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

}
