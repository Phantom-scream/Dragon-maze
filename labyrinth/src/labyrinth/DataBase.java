/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;


import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author user
 */
public class DataBase {
    private Connection connection;

    public DataBase() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/highscore";
        String user = "root";
        String password = "Iphone13pro/";
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void addPlayerScore(String name, int solvedLabyrinths) throws SQLException {
        String query = "INSERT INTO leaderboard (timestamp, name, solved_labyrinths) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setString(2, name);
            statement.setInt(3, solvedLabyrinths);
            statement.executeUpdate();
        }
    }

    public ArrayList<LeaderBoardEnt> getLeaderboard() throws SQLException {
        String query = "SELECT * FROM leaderboard ORDER BY solved_labyrinths DESC, timestamp ASC";
        ArrayList<LeaderBoardEnt> leaderboard = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {
            while (results.next()) {
                String name = results.getString("name");
                int solvedLabyrinths = results.getInt("solved_labyrinths");
                leaderboard.add(new LeaderBoardEnt(name, solvedLabyrinths));
            }
        }

        return leaderboard;
    }
}