/*
 * Created by Osman Balci on 2016.04.23  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("phoneNumberValidator")
/**
 * Validates the phone number inputted.
 * 
 * @author Andres
 * Based on Dr. Balci's validators
 */
public class PhoneNumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        // Type cast the inputted "value" to zipcode of type String
        String phoneNumber = (String) value;

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
        
        /* REGular EXpression (regex) for validating the U.S. Postal ZIP code
        
            ^           start of regex
            [0-9]{5}    match a digit, exactly five times
            (?:         group but don't capture
            -           match a hyphen
            [0-9]{4}    match a digit, exactly four times
            )           end of the non-capturing group
            ?           make the group optional
            $           end of regex
        */

        // REGular EXpression (regex) to validate the U.S. Phone Number entered
        String regex = "^[0-9]{3}(?:-[0-9]{3})(?:-[0-9]{4})$";
        
        if (!phoneNumber.matches(regex)) {
            throw new ValidatorException(new FacesMessage("U.S. Phone numbers "
                    + "consist of 3 digits, hyphen, 3 digits, hyphen, and 4 digits!"));
        } 
    } 
    
}