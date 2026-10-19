/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.sendamessage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginIT {
     
   String firstName = "Simphiwe";
    String lastName = "Starr";
    String username = "Starr_y";
    String password = "Simphiwe@christ1na";
    String cellPhoneNumber = "0783812656";

   
    @Test
    public void testCheckUserNameCorrect() {

        Login login = new Login(
                firstName,
                lastName,
                "Sta_y",
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Username successfully captured.",
                login.checkUserName()
        );
    }

    
    @Test
    public void testCheckUserNameIncorrect() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                login.checkUserName()
        );
    }

    
    @Test
    public void testPasswordCorrect() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Password successfully captured.",
                login.checkPasswordComplexity()
        );
    }

    @Test
    public void testPasswordIncorrect() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                "password",
                cellPhoneNumber
        );

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                login.checkPasswordComplexity()
        );
    }

    
    @Test
    public void testCellPhoneCorrect() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                "+27838126565"
        );

        assertEquals(
                "Cell phone number successfully added.",
                login.checkCellPhoneNumber()
        );
    }

   
    @Test
    public void testCellPhoneIncorrect() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Cell phone number incorrectly formatted or does not contain international code.",
                login.checkCellPhoneNumber()
        );
    }

   
    @Test
    public void testLoginSuccessful() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertTrue(
                login.loginUser(username, password)
        );
    }

    
    @Test
    public void testLoginFailed() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertFalse(
                login.loginUser(
                        "WrongUser",
                        "WrongPassword"
                )
        );
    }

    @Test
    public void testSuccessfulLoginMessage() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Welcome Simphiwe Starr it is great to see you again.",
                login.returnLoginStatus(username, password)
        );
    }

    @Test
    public void testFailedLoginMessage() {

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                login.returnLoginStatus(
                        "WrongUser",
                        "WrongPassword"
                )
        );
    }
}