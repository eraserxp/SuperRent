/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.util.ArrayList;
import java.util.HashMap;
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

    protected HashMap<Node, ArrayList<Node>> relatedNodeMapping = new HashMap<Node, ArrayList<Node>>();

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

    /**
     * Add the relatedNode to the list corresponding to the target node so that
     * if something went wrong with the targetNode, the relatedNode will also be
     * disabled
     *
     * @param targetNode
     * @param relatedNode
     */
    protected void addRelatedNode(Node targetNode, Node relatedNode) {
        if (!relatedNodeMapping.containsKey(targetNode)) {
            ArrayList<Node> relatedNodes = new ArrayList<>();
            relatedNodeMapping.put(targetNode, relatedNodes);
        }
        relatedNodeMapping.get(targetNode).add(relatedNode);
    }

    /**
     * disable all the nodes that are related to the given node
     *
     * @param node
     */
    protected void disableRelated(Node node) {
        ArrayList<Node> relatedNodes = relatedNodeMapping.get(node);
        for (Node n : relatedNodes) {
            if (n instanceof TextField) {
                ((TextField) n).clear();
            }
            n.setDisable(true);
        }
    }

    /**
     * enable all the nodes that are related to the given node
     *
     * @param node
     */
    protected void enableRelated(Node node) {
        ArrayList<Node> relatedNodes = relatedNodeMapping.get(node);
        for (Node n : relatedNodes) {
            if (n instanceof TextField) {
                ((TextField) n).clear();
            }
            n.setDisable(false);
        }
    }

    protected void hide(Node... nodes) {
        for (Node n : nodes) {
            n.setVisible(false);
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
