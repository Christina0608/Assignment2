/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sendamessage;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Student
 */
public class POE1And2 {

    public static void main(String[] args) {
        File myJson = new File("message.json");
        Scanner input = new Scanner(System.in);

        // ---- Part 1: Registration and Login ----
        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        System.out.print("Enter your cell phone number (+27...): ");
        String cellPhoneNumber = input.nextLine();

        Login login = new Login(firstName, lastName, username, password, cellPhoneNumber);
        System.out.println(login.checkUserName());
        System.out.println(login.checkPasswordComplexity());
        System.out.println(login.checkCellPhoneNumber());

        if (!(login.isUserNameValid() && login.isPasswordValid() && login.isCellPhoneValid())) {
            System.out.println("Registration failed. Please restart and correct the details above.");
            input.close();
            return;
        }

        System.out.println();
        System.out.println("=== LOGIN ===");
        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();
        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();
        System.out.println(login.returnLoginStatus(enteredUsername, enteredPassword));

        if (!login.loginUser(enteredUsername, enteredPassword)) {
            input.close();
            return;
        }
        System.out.println();

        // ---- Part 2: QuickChat ----
        System.out.println("=== WELCOME TO QUICKCHAT MENU ===");
        System.out.print("How many messages do you want to send? ");

        int numMessages = input.nextInt();
        input.nextLine(); // Consume newline
        int totalSent = 0;

        for (int i = 1; i <= numMessages; i++) {
            System.out.println("");
            String messageID = String.valueOf((int)(Math.random() * 100000000));

            System.out.print("Enter recipient number (+ South African code, max 10 characters): ");
            String recipient = input.nextLine();

            String messageText = "";
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = input.nextLine();
                if (messageText.length() > 250) {
                    System.out.println("Message exceeds 250 characters. Please correct it to 250 characters or less.");
                } else {
                    break;
                }
            }
            
            // FIX: Create the msg object here so the print statements below work
            Message msg = new Message(messageID, recipient, messageText);
            
            System.out.println(msg.checkRecipientCell());
            System.out.println("Message Hash: " + msg.getMessageHash());
            
            System.out.println("=== QUICKCHAT MENU ===");
            System.out.println("Choose option: 1) Send Message | 2) Coming soon | 3) Quit ");
            
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            if (choice == 1) {
                totalSent = sendMessage(messageID, recipient, messageText, myJson, input, totalSent);
            } else if (choice == 2) {
                System.out.println("Coming soon!");
            } else if (choice == 3) {
                System.out.println("Exiting program.");
                System.exit(0);
            } else {
                System.out.println("Invalid option selected.");
            }
        }
        
        input.close();
    }
    
    // sendMessage method placed outside of main (at the class level)
    public static int sendMessage(String messageID, String recipient, String messageText, File myJson, Scanner input, int totalSent) {
        Message msg = new Message(messageID, recipient, messageText);
        
        System.out.println(msg.checkRecipientCell());
        System.out.println("Message Hash: " + msg.getMessageHash());
        
        System.out.println("=== QUICKCHAT MENU ===");
        System.out.println("Choose option: 1) Send | 2) Disregard | 3) Store | 4) Exit");
        
        int choice = input.nextInt();
        input.nextLine(); // Consume newline

        if (choice == 1) {
            totalSent++;
            System.out.println("Message successfully sent.");
            System.out.println("Details:");
            System.out.println("ID: " + msg.getMessageID());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("Hash: " + msg.getMessageHash());
            
        } else if (choice == 2) {
            System.out.println("Message disregarded.");
            
        } else if (choice == 3) {
            // Using true to append new messages to message.json
            try (FileWriter writer = new FileWriter(myJson, true)) {
                // Automatically writes the formatted JSON string from the Message object
                writer.write(msg.toJSON() + "\n");
                System.out.println("Message successfully stored as a JSON object in message.json.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving the message.");
                e.printStackTrace();
            }
        } else if (choice == 4) {
            System.out.println("Exiting program.");
            System.exit(0);
        } else {
            System.out.println("Invalid option selected.");
        }
        
        System.out.println("");
        return totalSent; // Return the updated totalSent count back to main
    }
}