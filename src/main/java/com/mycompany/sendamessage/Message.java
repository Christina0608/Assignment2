/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendamessage;

/**
 *
 * @author Student
 */
public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;


    public Message(String messageID, String recipient, String messageText) {
        
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        
    }

   
     public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    
    public String checkRecipientCell() {
        if (recipient.length() == 12 && recipient.startsWith("+27")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    
    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return messageID.substring(0, 2) + ":0:" + firstWord.toUpperCase() + lastWord.toUpperCase();
    }
    
    
public String toJSON() {
    return "{\n" +
           "  \"messageID\": \"" + messageID + "\",\n" +
           "  \"recipient\": \"" + recipient + "\",\n" +
           "  \"messageText\": \"" + messageText + "\",\n" +
           "  \"messageHash\": \"" + messageHash + "\"\n" +
           "},";
}

   
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    
    }
