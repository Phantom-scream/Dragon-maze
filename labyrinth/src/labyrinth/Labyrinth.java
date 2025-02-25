/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 *
 * @author user
 */
public class Labyrinth {
    private char[][] grid;
    private int rows;
    private int cols;

    public Labyrinth(String filePath) throws IOException {
        loadFromFile(filePath);
    }

    public Labyrinth(char[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }

    private void loadFromFile(String filePath) throws IOException {
        ArrayList<char[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.toCharArray());
            }
        }
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("The labyrinth file is empty: " + filePath);
        }
        
        this.rows = lines.size();
        this.cols = lines.get(0).length;
        if (rows == 0 || cols == 0) {
            throw new IllegalArgumentException("The labyrinth grid must have non-zero dimensions: " + filePath);
        }
        
        for (char[] line : lines) {
            if (line.length != cols) {
                throw new IllegalArgumentException("Not correct row lengths in labyrinth file: " + filePath);
            }
        }
        this.grid = lines.toArray(new char[rows][cols]);
    }
    
    public boolean isFloor(int x, int y) {
        return grid[x][y] == 'f';
    }

    public boolean isWall(int x, int y) {
        return grid[x][y] == 'w';
    }

    public boolean iswBounds(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public void setCell(int x, int y, char value) {
        grid[x][y] = value;
    }

    public int getStartX() {
        return rows - 1;
    }

    public int getStartY() {
        return 0;
    }

    public int[] getRandomEC() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isFloor(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (emptyCells.isEmpty()) {
            throw new IllegalStateException("No empty cells");
        }
        return emptyCells.get(new Random().nextInt(emptyCells.size()));
    }
}
