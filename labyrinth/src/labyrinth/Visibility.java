/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package labyrinth;

public class Visibility {
    private Labyrinth labyrinth;
    private Player player;
    private int visibilityR;

    
    public Visibility(Labyrinth labyrinth, Player player, int visibilityRadius) {
        this.labyrinth = labyrinth;
        this.player = player;
        this.visibilityR = visibilityRadius;
    }

    public boolean isVisible(int targetX, int targetY) {
        if (targetX < 0 || targetX >= labyrinth.getRows() || targetY < 0 || targetY >= labyrinth.getCols()) {
            return false;  
        }
        int playerX = player.getX();
        int playerY = player.getY();
        double distance = Math.sqrt(Math.pow(playerX - targetX, 2) + Math.pow(playerY - targetY, 2));
        return distance <= visibilityR;
    }

    public boolean[][] getVisM() {
        int rows = labyrinth.getRows();
        int cols = labyrinth.getCols();
        boolean[][] visibilityMask = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visibilityMask[i][j] = isVisible(i, j);
            }
        }

        return visibilityMask;
    }
}
