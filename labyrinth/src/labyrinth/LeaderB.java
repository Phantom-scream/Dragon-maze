/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author user
 */
public class LeaderB {
    private final DataBase database;

    public LeaderB() throws SQLException {
        this.database = new DataBase();
    }

    public void addScore(String playerName, int solvedLabyrinths) {
        try {
            database.addPlayerScore(playerName, solvedLabyrinths);
        } catch (SQLException e) {
            System.err.println("Error adding score: " + e.getMessage());
        }
    }

    public void displayLeaderboard() {
        try {
            ArrayList<LeaderBoardEnt> leaderboard = database.getLeaderboard();
            SwingUtilities.invokeLater(() -> showLeaderD(leaderboard));
            System.out.println("Leaderboard:");
            for (LeaderBoardEnt entry : leaderboard) {
                System.out.println(entry);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving leaderboard: " + e.getMessage());
        }
    }
    
    private void showLeaderD(ArrayList<LeaderBoardEnt> leaderboard) {
        JDialog leaderboardDialog = new JDialog((Frame) null, "Leaderboard", true);
        leaderboardDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardPanel.add(title);

        leaderboardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (LeaderBoardEnt entry : leaderboard) {
            JLabel entryLabel = new JLabel(entry.toString());
            entryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            entryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardPanel.add(entryLabel);
        }

        leaderboardDialog.add(leaderboardPanel);
        leaderboardDialog.setSize(300, 400);
        leaderboardDialog.setLocationRelativeTo(null);
        leaderboardDialog.setVisible(true);
    }

}
