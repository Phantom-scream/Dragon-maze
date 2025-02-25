/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import java.io.IOException;


/**
 *
 * @author user
 */
public class Game {
    private Labyrinth labyrinth;
    private Player player;
    private Dragon dragon;
    private Visibility visibility;
    private int currentLevel;
    private final String[] levelFiles = {
        "levels/level1.txt",
        "levels/level2.txt",
        "levels/level3.txt",
        "levels/level4.txt",
        "levels/level5.txt",
        "levels/level6.txt",
        "levels/level7.txt",
        "levels/level8.txt",
        "levels/level9.txt",
        "levels/level10.txt"
    };

    public Game() {
        this.currentLevel = 0;
        loadLevel(currentLevel);
        
    }

    public boolean loadLevel(int levelind) {
        if (levelind < 0 || levelind >= levelFiles.length) {
            return false;
        }

        try {
            this.labyrinth = new Labyrinth(levelFiles[levelind]);
            this.player = new Player(labyrinth.getStartX(), labyrinth.getStartY());
            int[] dragonStart = labyrinth.getRandomEC();
            this.dragon = new Dragon(labyrinth, player);
            this.visibility = new Visibility(labyrinth, player, 3); 
            this.currentLevel = levelind;
            
            return true;
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
            fallbLabyrinth();
            return true;
        }
    }

    private void fallbLabyrinth() {
        int rows = 10;
        int cols = 10;
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    grid[i][j] = 'w';
                } else {
                    grid[i][j] = 'f';
                }
            }
        }
        grid[rows - 1][0] = 'f';
        grid[0][cols - 1] = 'f';
        this.labyrinth = new Labyrinth(grid);
        this.player = new Player(labyrinth.getStartX(), labyrinth.getStartY());
        int[] dragonStart = labyrinth.getRandomEC();
        this.dragon = new Dragon(labyrinth, player);
        this.visibility = new Visibility(labyrinth, player, 3);
    }

    public boolean loadNextLevel() {
        if (currentLevel + 1 < levelFiles.length) {
            return loadLevel(currentLevel + 1);
        } else {
            System.out.println("No more levels to load.");
            return false;
        }
    }

    public Labyrinth getCurrentLabyrinth() {
        return labyrinth;
    }

    public Player getPlayer() {
        return player;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}