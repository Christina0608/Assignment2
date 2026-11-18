/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sendamessage;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Student
 */
public class POE1And2 {

// PART 3: REQUIRED ARRAYS //

    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    public static void main(String[] args) {

        File myJson = new File("message.json");
        Scanner input = new Scanner(System.in);

        // PART 3: CREATE MANAGER//

        Part3Manager manager = new Part3Manager(myJson);

        // PART 1: REGISTRATION//

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

        Login login = new Login(firstName,lastName,username,password,cellPhoneNumber  );

        System.out.println(login.checkUserName());
        System.out.println(login.checkPasswordComplexity());
        System.out.println(login.checkCellPhoneNumber());

        if (!(login.isUserNameValid()&& login.isPasswordValid()&& login.isCellPhoneValid())) {

            System.out.println("Registration failed. Please restart and correct the details above.");

            input.close();
            return;
        }

        // LOGIN //

        System.out.println();
        System.out.println("=== LOGIN ===");
        
        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();
        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();

        System.out.println(
                login.returnLoginStatus(enteredUsername, enteredPassword)
        );

        if (!login.loginUser(enteredUsername, enteredPassword)) {
            input.close();
            return;
        }

        System.out.println();

        // PART 2: QUICKCHAT //

        System.out.println("=== WELCOME TO QUICKCHAT MENU ===");
        System.out.print("How many messages do you want to send? ");

        int numMessages = input.nextInt();
        input.nextLine();

        int totalSent = 0;

        for (int i = 1; i <= numMessages; i++) {

            System.out.println();

            String messageID = String.valueOf((int) (Math.random() * 100000000)
            );

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

            Message msg = new Message(messageID,recipient,messageText );

            System.out.println(msg.checkRecipientCell());
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("=== QUICKCHAT MENU ===");
            System.out.println("Choose option: 1) Send Message | 2) Coming soon | 3) Quit " );

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                totalSent = sendMessage(messageID,recipient,messageText,myJson,input,totalSent,cellPhoneNumber,manager);

            } else if (choice == 2) {

                System.out.println("Coming soon!");

            } else if (choice == 3) {

                System.out.println("Exiting program.");
                input.close();
                return;

            } else {

                System.out.println("Invalid option selected.");
            }
        }

        //  MAIN MENU //

        int mainChoice;

        do {

            System.out.println("\n=== QUICKCHAT MAIN MENU ===");
            System.out.println("1) Display Sent Messages Report");
            System.out.println("2) Display Array Information");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");

            System.out.print("Choose option: ");

            mainChoice = input.nextInt();
            input.nextLine();

            switch (mainChoice) {

                case 1:
                    manager.displaySentMessages();
                    break;

                case 2:
                    manager.displayArrayInformation();
                    break;

                case 3:
                    System.out.println(
                            "Thank you for using QuickChat."
                    );
                    break;

                case 4:
                    manager.storedMessagesMenu(input);
                    break;

                default:
                    System.out.println("Invalid option selected.");
                    break;
            }

        } while (mainChoice != 3);

        input.close();
    }

    // SEND MESSAGE METHOD //

    public static int sendMessage(String messageID,String recipient,String messageText,File myJson,Scanner input,int totalSent,
            String cellPhoneNumber,
            Part3Manager manager) {

        Message msg = new Message( messageID, recipient, messageText);

        System.out.println(msg.checkRecipientCell());
        System.out.println("Message Hash: " + msg.getMessageHash());
        System.out.println("=== QUICKCHAT MENU ===");
        System.out.println("Choose option: 1) Send | 2) Disregard | 3) Store | 4) Exit");

        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {

            totalSent++;

            System.out.println("Message successfully sent.");
            System.out.println("Details:");
            System.out.println("ID: " + msg.getMessageID());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("Hash: " + msg.getMessageHash());

            // PART 3: ADD SENT MESSAGE//

            manager.addSentMessage(cellPhoneNumber,recipient,messageText,msg.getMessageID(),msg.getMessageHash());

            sentMessages.add(messageText);
            messageHashes.add(msg.getMessageHash());
            messageIDs.add(msg.getMessageID());

        } else if (choice == 2) {

            System.out.println("Message disregarded.");

            // PART 3: ADD DISREGARDED MESSAGE //

            manager.addDisregardedMessage(cellPhoneNumber,recipient,messageText,msg.getMessageID(),msg.getMessageHash()
            );

            disregardedMessages.add(messageText);
            messageHashes.add(msg.getMessageHash());
            messageIDs.add(msg.getMessageID());

        } else if (choice == 3) {

            try (FileWriter writer = new FileWriter(myJson, true)) {

                writer.write(msg.toJSON() + "\n");

                System.out.println( "Message successfully stored as a JSON object in message.json."
                );

                // PART 3: ADD STORED MESSAGE //

                manager.addStoredMessage(cellPhoneNumber,recipient,messageText,msg.getMessageID(),msg.getMessageHash() );

                storedMessages.add(messageText);
                messageHashes.add(msg.getMessageHash());
                messageIDs.add(msg.getMessageID());

            } catch (IOException e) {

                System.out.println("An error occurred while saving the message." );

                e.printStackTrace();
            }

        } else if (choice == 4) {

            System.out.println("Exiting program.");
            System.exit(0);

        } else {

            System.out.println("Invalid option selected.");
        }

        System.out.println();

        return totalSent;
    }
}



