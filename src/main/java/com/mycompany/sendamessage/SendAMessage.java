/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sendamessage;

import java.util.Scanner;
/**
 *
 * @author Student
 */
public class SendAMessage {

   public static void main(String[] args) {
    
   Scanner input = new Scanner(System.in);
   System.out.println("Hello open chat");
   System.out.print("How many messages do you want to send? ");

   int numMessages = input.nextInt();
   input.nextLine();
   int totalSent = 0;
   for (int i = 1; i <= numMessages; i++) {
    
   System.out.println("");
  }
   String messageID = String.valueOf((int)(Math.random() * 100));

   System.out.print("Enter recipient number (+ South african code, max 10 characters): ");
   String recipient = input.nextLine();

   System.out.print("Enter your message: ");
   String messageText = input.nextLine();
   if (messageText.length() > 250) {
    
   System.out.println("Message exceeds 250 characters. Please coorect it to 250 characters and not more.");
  }

   Message msg = new Message(messageID, recipient, messageText);

   System.out.println(msg.checkRecipientCell());
   System.out.println("Message Hash: " + msg.getMessageHash());
   System.out.println("Choose option: 1) Send 2) Disregard 3) Store");
   
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
     } else if (choice == 2) {
    
   System.out.println("Press 0 to delete the message.");
     } else if (choice == 3) {
         
   System.out.println("Message successfully stored.");
     
   System.out.println("");
  
  
  }
    }
  }

