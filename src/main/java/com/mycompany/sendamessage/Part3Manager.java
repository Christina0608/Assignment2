/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendamessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Part3Manager {


    public Part3Manager(File json) {
        this.jsonFile = null;
    }
  

    // FIVE REQUIRED ARRAYS

    private final ArrayList<MessageRecord> sentMessages = new ArrayList<>();
    private final ArrayList<MessageRecord> disregardedMessages = new ArrayList<>();
    private final ArrayList<MessageRecord> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();

    private final File jsonFile;


    // MESSAGE RECORD

    private static class MessageRecord {

        String sender;
        String recipient;
        String message;
        String messageID;
        String messageHash;

        MessageRecord(String sender, String recipient,
                      String message, String messageID,
                      String messageHash) {

            this.sender = sender;
            this.recipient = recipient;
            this.message = message;
            this.messageID = messageID;
            this.messageHash = messageHash;
        }
    }

    // ADD SENT MESSAGE

    public void addSentMessage(String sender, String recipient,
                               String message, String id, String hash) {

        sentMessages.add(new MessageRecord(
                sender, recipient, message, id, hash
        ));

        updateHashAndIDArrays();
    }

    // ADD DISREGARDED MESSAGE

    public void addDisregardedMessage(String sender, String recipient,
                                      String message, String id, String hash) {

        disregardedMessages.add(new MessageRecord(
                sender, recipient, message, id, hash
        ));

        updateHashAndIDArrays();
    }

    // ADD STORED MESSAGE

    public void addStoredMessage(String sender, String recipient,
                                 String message, String id, String hash) {

        storedMessages.add(new MessageRecord(
                sender, recipient, message, id, hash
        ));

        updateHashAndIDArrays();
    }

    // UPDATE HASH AND ID ARRAYS

    private void updateHashAndIDArrays() {

        messageHashes.clear();
        messageIDs.clear();

        for (MessageRecord msg : sentMessages) {
            messageHashes.add(msg.messageHash);
            messageIDs.add(msg.messageID);
        }

        for (MessageRecord msg : disregardedMessages) {
            messageHashes.add(msg.messageHash);
            messageIDs.add(msg.messageID);
        }

        for (MessageRecord msg : storedMessages) {
            messageHashes.add(msg.messageHash);
            messageIDs.add(msg.messageID);
        }
    }

    // A. DISPLAY SENDER AND RECIPIENT

    public void displayStoredMessages() {

        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        System.out.println("\n=== STORED MESSAGES ===");

        for (MessageRecord msg : storedMessages) {
            System.out.println("Sender: " + msg.sender);
            System.out.println("Recipient: " + msg.recipient);
            System.out.println("-------------------------");
        }
    }

    // B. DISPLAY LONGEST MESSAGE

    public void displayLongestMessage() {

        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        MessageRecord longest = storedMessages.get(0);

        for (MessageRecord msg : storedMessages) {
            if (msg.message.length() > longest.message.length()) {
                longest = msg;
            }
        }

        System.out.println("\n=== LONGEST STORED MESSAGE ===");
        System.out.println("Sender: " + longest.sender);
        System.out.println("Recipient: " + longest.recipient);
        System.out.println("Message: " + longest.message);
        System.out.println("Length: " + longest.message.length());
    }

    // C. SEARCH USING MESSAGE ID

    public void searchMessageID(String id) {

        boolean found = false;

        for (MessageRecord msg : storedMessages) {

            if (msg.messageID.equals(id)) {
                System.out.println("\nMessage found!");
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.message);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Message ID not found.");
        }
    }

    // D. SEARCH USING RECIPIENT

    public void searchRecipient(String recipient) {

        boolean found = false;

        System.out.println("\n=== RECIPIENT MESSAGES ===");

        for (MessageRecord msg : storedMessages) {

            if (msg.recipient.equals(recipient)) {
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.message);
                System.out.println("Message ID: " + msg.messageID);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }

    // E. DELETE USING MESSAGE HASH

    public void deleteMessageHash(String hash) {

        boolean removed = false;

        for (int i = 0; i < storedMessages.size(); i++) {

            if (storedMessages.get(i).messageHash.equals(hash)) {
                storedMessages.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            rewriteJSONFile();
            updateHashAndIDArrays();
            System.out.println("Message successfully deleted.");
        } else {
            System.out.println("Message hash not found.");
        }
    }

    // REWRITE JSON FILE AFTER DELETION

    private void rewriteJSONFile() {

        try (FileWriter writer = new FileWriter(jsonFile, false)) {

            for (MessageRecord msg : storedMessages) {

                Message message = new Message(
                        msg.messageID,
                        msg.recipient,
                        msg.message
                );

                writer.write(message.toJSON() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error updating JSON file.");
            e.printStackTrace();
        }
    }

    // DISPLAY SENT MESSAGES REPORT

    public void displaySentMessages() {

        if (sentMessages.isEmpty()) {
            System.out.println("No sent messages available.");
            return;
        }

        System.out.println("\n=== SENT MESSAGES REPORT ===");

        for (MessageRecord msg : sentMessages) {
            System.out.println("Message ID: " + msg.messageID);
            System.out.println("Message Hash: " + msg.messageHash);
            System.out.println("Sender: " + msg.sender);
            System.out.println("Recipient: " + msg.recipient);
            System.out.println("Message: " + msg.message);
            System.out.println("-------------------------");
        }

        System.out.println(
                "Total messages sent: " + sentMessages.size()
        );
    }

    // DISPLAY ARRAY INFORMATION

    public void displayArrayInformation() {

        updateHashAndIDArrays();

        System.out.println("\n=== ARRAY INFORMATION ===");
        System.out.println("Sent Messages: " + sentMessages.size());
        System.out.println("Disregarded Messages: " + disregardedMessages.size());
        System.out.println("Stored Messages: " + storedMessages.size());
        System.out.println("Message Hashes: " + messageHashes);
        System.out.println("Message IDs: " + messageIDs);
    }

    // STORED MESSAGES MENU

    public void storedMessagesMenu(Scanner input) {

        int choice;

        do {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("1. Display sender and recipient");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search using Message ID");
            System.out.println("4. Search using recipient");
            System.out.println("5. Delete using Message Hash");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    displayStoredMessages();
                    break;

                case 2:
                    displayLongestMessage();
                    break;

                case 3:
                    System.out.print("Enter Message ID: ");
                    String id = input.nextLine();
                    searchMessageID(id);
                    break;

                case 4:
                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine();
                    searchRecipient(recipient);
                    break;

                case 5:
                    System.out.print("Enter Message Hash: ");
                    String hash = input.nextLine();
                    deleteMessageHash(hash);
                    break;

                case 6:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }

        } while (choice != 6);
    }

    // GETTERS FOR TESTING

    public ArrayList<String> getSentMessages() {

        ArrayList<String> messages = new ArrayList<>();

        for (MessageRecord msg : sentMessages) {
            messages.add(msg.message);
        }

        return messages;
    }

    public ArrayList<String> getDisregardedMessages() {

        ArrayList<String> messages = new ArrayList<>();

        for (MessageRecord msg : disregardedMessages) {
            messages.add(msg.message);
        }

        return messages;
    }

    public ArrayList<String> getStoredMessages() {

        ArrayList<String> messages = new ArrayList<>();

        for (MessageRecord msg : storedMessages) {
            messages.add(msg.message);
        }

        return messages;
    }

    public ArrayList<String> getMessageHashes() {
        updateHashAndIDArrays();
        return new ArrayList<>(messageHashes);
    }

    public ArrayList<String> getMessageIDs() {
        updateHashAndIDArrays();
        return new ArrayList<>(messageIDs);
    }

    private static class Message {

        public Message() {
        }
    }
}



