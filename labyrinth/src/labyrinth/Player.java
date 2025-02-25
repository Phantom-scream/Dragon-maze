/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

/**
 *
 * @author user
 */
public class Player {
    private int x;
    private int y; 
    private int targetX; 
    private int targetY; 
    private boolean isMoving;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        this.targetY = startY;
        this.isMoving = false;
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

    public void move(String direction, Labyrinth labyrinth) {
        if (isMoving) return;

        int newX = x;
        int newY = y;

        switch (direction) {
            case "UP" -> newX--;
            case "DOWN" -> newX++;
            case "LEFT" -> newY--;
            case "RIGHT" -> newY++;
        }

        if (isValidMove(newX, newY, labyrinth)) {
            targetX = newX;
            targetY = newY;
            isMoving = true;
        }
    }

    public void completeMove() {
        if (isMoving) {
            System.out.println("Player completed move to: (" + targetX + ", " + targetY + ")");
            x = targetX;
            y = targetY;
            isMoving = false;
        }
    }

  
    private boolean isValidMove(int newX, int newY, Labyrinth labyrinth) {
        return newX >= 0 && newX < labyrinth.getRows()
            && newY >= 0 && newY < labyrinth.getCols()
            && labyrinth.isFloor(newX, newY);
    }
    
    public boolean isNeighbor(int targetX, int targetY) {
        int dx = Math.abs(x - targetX);
        int dy = Math.abs(y - targetY);
        return dx + dy == 1;
    }
}
