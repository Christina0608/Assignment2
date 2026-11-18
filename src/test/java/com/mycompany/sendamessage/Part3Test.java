/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendamessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class Part3Test {
    

    @TempDir
    Path tempDir;

    private Part3Manager manager;
    private File jsonFile;

    @BeforeEach
    public void setUp() {

        jsonFile = tempDir.resolve("message.json").toFile();

        manager = new Part3Manager(jsonFile);
    }

    // TEST 1: SENT MESSAGES ARRAY

    @Test
    public void testSentMessagesArray() {

        manager.addSentMessage(
                "+27821234567",
                "+27829876543",
                "Hello",
                "1001",
                "HASH001"
        );

        ArrayList<String> messages =
                manager.getSentMessages();

        assertEquals(1, messages.size());
        assertEquals("Hello", messages.get(0));
    }

    // TEST 2: DISREGARDED MESSAGES ARRAY

    @Test
    public void testDisregardedMessagesArray() {

        manager.addDisregardedMessage(
                "+27821234567",
                "+27829876543",
                "Disregarded message",
                "1002",
                "HASH002"
        );

        assertEquals(
                1,
                manager.getDisregardedMessages().size()
        );

        assertEquals(
                "Disregarded message",
                manager.getDisregardedMessages().get(0)
        );
    }

    // TEST 3: STORED MESSAGES ARRAY

    @Test
    public void testStoredMessagesArray() {

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "Stored message",
                "1003",
                "HASH003"
        );

        assertEquals(
                1,
                manager.getStoredMessages().size()
        );

        assertEquals(
                "Stored message",
                manager.getStoredMessages().get(0)
        );
    }

    // TEST 4: MESSAGE HASHES AND IDS

    @Test
    public void testMessageHashesAndIDs() {

        manager.addSentMessage(
                "+27821234567",
                "+27829876543",
                "Test message",
                "1004",
                "HASH004"
        );

        assertTrue(
                manager.getMessageIDs().contains("1004")
        );

        assertTrue(
                manager.getMessageHashes().contains("HASH004")
        );
    }

    // TEST 5: DISPLAY LONGEST MESSAGE

    @Test
    public void testDisplayLongestMessage() {

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "Hi",
                "1005",
                "HASH005"
        );

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "This is the longest message",
                "1006",
                "HASH006"
        );

        String output = captureOutput(
                () -> manager.displayLongestMessage()
        );

        assertTrue(
                output.contains("This is the longest message")
        );
    }

    // TEST 6: SEARCH USING MESSAGE ID

    @Test
    public void testSearchMessageID() {

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "Find this message",
                "1007",
                "HASH007"
        );

        String output = captureOutput(
                () -> manager.searchMessageID("1007")
        );

        assertTrue(output.contains("Message found!"));

        assertTrue(
                output.contains("Find this message")
        );
    }

    // TEST 7: SEARCH USING RECIPIENT

    @Test
    public void testSearchRecipient() {

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "Recipient test",
                "1008",
                "HASH008"
        );

        String output = captureOutput(
                () -> manager.searchRecipient("+27829876543")
        );

        assertTrue(
                output.contains("Recipient test")
        );
    }

    // TEST 8: DELETE MESSAGE USING HASH

    @Test
    public void testDeleteMessageHash() throws Exception {

        manager.addStoredMessage(
                "+27821234567",
                "+27829876543",
                "Delete this message",
                "1009",
                "HASH009"
        );

        manager.deleteMessageHash("HASH009");

        assertTrue(
                manager.getStoredMessages().isEmpty()
        );

        assertTrue(
                Files.exists(jsonFile.toPath())
        );

        String fileContent = new String(
                Files.readAllBytes(jsonFile.toPath())
        );

        assertFalse(
                fileContent.contains("Delete this message")
        );
    }

    // TEST 9: SENT MESSAGE REPORT

    @Test
    public void testDisplaySentMessages() {

        manager.addSentMessage(
                "+27821234567",
                "+27829876543",
                "Report test",
                "1010",
                "HASH010"
        );

        String output = captureOutput(
                () -> manager.displaySentMessages()
        );

        assertTrue(output.contains("Report test"));

        assertTrue(
                output.contains("Total messages sent: 1")
        );
    }

    // HELPER METHOD TO CAPTURE CONSOLE OUTPUT

    private String captureOutput(Runnable action) {

        PrintStream originalOutput = System.out;

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        try {

            System.setOut(new PrintStream(output));

            action.run();

        } finally {

            System.setOut(originalOutput);
        }

        return output.toString();
    }
}


   