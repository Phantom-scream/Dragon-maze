/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import java.util.Random;

/**
 *
 * @author user
 */
public class Dragon {
    private int x;
    private int y; 
    private int targetX;
    private int targetY; 
    private boolean isMoving;

    private final Random random;
    private int currentDirection; 

    public Dragon(Labyrinth labyrinth, Player player) {
        this.random = new Random();
        int[] position;

        do {
            position = labyrinth.getRandomEC(); 
            this.x = position[0];
            this.y = position[1];
        } while (istooClose(player)); 

        this.targetX = x;
        this.targetY = y;
        this.isMoving = false;
        this.currentDirection = random.nextInt(4); 
    }

    private boolean istooClose(Player player) {
        int dx = Math.abs(player.getX() - x);
        int dy = Math.abs(player.getY() - y);
        return dx + dy <= 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void move(Labyrinth labyrinth) {
        if (isMoving) return;

        int[][] directions = {
            {-1, 0}, 
            {1, 0},  
            {0, -1}, 
            {0, 1}  
        };

        int newX = x + directions[currentDirection][0];
        int newY = y + directions[currentDirection][1];

        if (isValidMove(newX, newY, labyrinth)) {
            targetX = newX;
            targetY = newY;
            isMoving = true;
            completeMove();
        } else {
            currentDirection = random.nextInt(4);
            move(labyrinth);
        }
    }

    public void completeMove() {
        if (isMoving) {
            x = targetX;
            y = targetY;
            isMoving = false;
        }
    }

    private boolean isValidMove(int newX, int newY, Labyrinth labyrinth) {
        return newX >= 0 && newX < labyrinth.getRows() && newY >= 0 && newY < labyrinth.getCols() && labyrinth.isFloor(newX, newY);
    }
}