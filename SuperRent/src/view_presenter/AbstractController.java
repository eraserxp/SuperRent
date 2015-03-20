/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 *
 */
public class AbstractController {

    protected boolean isInputEmpty(TextField t) {
        return (t.getText() == null || t.getText().length() == 0);
    }

    protected boolean isInputEmpty(PasswordField t) {
        return (t.getText() == null || t.getText().length() == 0);
    }

    protected boolean isInputTooLong(TextField t, int maxsize) {
        return t.getText().length() > maxsize;
    }

    protected boolean isInputTooLong(PasswordField t, int maxsize) {
        return t.getText().length() > maxsize;
    }

    protected boolean areTwoInputsMatch(PasswordField p1, PasswordField p2) {
        return p1.getText().equals(p2.getText());
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
    }

    protected void configureComboBox(ComboBox cmb, ArrayList<String> choices) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (String c : choices) {
            items.add(c);
        }
        cmb.setItems(items);
    }
    
    
}
