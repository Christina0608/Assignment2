/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class JsonViewer {
    

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("=== QUICKCHAT JSON VIEWER ===");
        System.out.print("Enter the path to your JSON file: ");

        String filePath = input.nextLine();

        File jsonFile = new File(filePath);

        if (!jsonFile.exists()) {
            System.out.println("JSON file does not exist.");
            return;
        }

        if (!jsonFile.isFile()) {
            System.out.println("The selected path is not a file.");
            return;
        }

        try {
            String jsonContent = Files.readString(jsonFile.toPath());

            System.out.println("\n=== JSON MESSAGES ===");
            System.out.println(jsonContent);

        } catch (IOException e) {
            System.out.println("Error reading JSON file.");
            e.printStackTrace();
        }
    }
}


