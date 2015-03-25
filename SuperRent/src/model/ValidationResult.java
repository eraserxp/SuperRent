/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author eraserxp
 */
public class ValidationResult {
    private boolean result = false;
    
    public void set(boolean newValue) {
        result = newValue;
    }
    
    public boolean get() {
        return result;
    }
}
