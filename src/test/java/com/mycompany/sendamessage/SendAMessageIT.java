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
public class SendAMessageIT {
    
    public SendAMessageIT() {
    }

    
    @Test
    public void testMessageIDSuccess() {
        Message msg = new Message("123456789", "+27718630302", "Hey");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testMessageIDFailure() {
        Message msg = new Message("123456789012345", "+27718630302", "Hello Simphiwe");
        assertFalse(msg.checkMessageID());
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("123456789", "+27718630302", "Hi simle ");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message("123456789", "08575975389", "Hey");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message("1234567890", "+27718630302", "Hi Mike tonight");
        assertEquals("12:0:HITONIGHT", msg.getMessageHash());
    }
}
