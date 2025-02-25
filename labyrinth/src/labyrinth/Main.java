/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package labyrinth;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class Main {
    public static void main(String[] args) {
        try {
            String playerName = JOptionPane.showInputDialog(
                null,
                "Enter your name:",
                "Welcome to the Labyrinth Game",
                JOptionPane.QUESTION_MESSAGE
            );

            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player";
            }

            try {
                new DataBase();
                System.out.println("Database connection successful!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Unable to connect to the database. Please ensure your database is running.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1); 
            }

            JFrame frame = new JFrame("Labyrinth Game");
            LabyrinthGame game = new LabyrinthGame("levels/level1.txt", playerName);
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to close the game?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "An error occurred while starting the game: " + e.getMessage(),
                "Game Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
