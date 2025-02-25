/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author user
 */

public class LabyrinthGame extends JPanel implements KeyListener {
    private String name;
    public Game game;
    private Labyrinth labyrinth;
    private Player player;
    private Dragon dragon;
    private Visibility visibility;
    private Timer gameTimer;
    private JButton restartButton;
    private int solvedL = 0;
    private LeaderB leaderB;

    private int elapsedTime;

    private BufferedImage wallImage;
    private BufferedImage floorImage;
    private BufferedImage playerImage;
    private BufferedImage dragonImage;
    private boolean gameOver;

    public LabyrinthGame(String labyrinthFile, String name) throws Exception {
        this.name = name;
        game = new Game();
        labyrinth = game.getCurrentLabyrinth();
        player = game.getPlayer();
        dragon = game.getDragon();
        visibility = game.getVisibility();
        
        gameOver = false;
        
        leaderB = new LeaderB();

        elapsedTime = 0;

        try {
            wallImage = ImageIO.read(new File("elements/Wall.png"));
            floorImage = ImageIO.read(new File("elements/Floor.png"));
            playerImage = ImageIO.read(new File("game/Player.png"));
            dragonImage = ImageIO.read(new File("game/Dragon.png"));
        } catch (IOException e) {
            throw new RuntimeException("There is an error loading images: " + e.getMessage());
        }
        
        restartButton = new JButton("Restart");
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        setLayout(null);
        restartButton.setBounds(200, 220, 100, 50);
        add(restartButton);

        gameTimer = new Timer(500, e -> {
            elapsedTime++;
            dragon.move(labyrinth);
            if (player.isNeighbor(dragon.getX(), dragon.getY()) || (player.getX() == dragon.getX() && player.getY() == dragon.getY())) {
                gameTimer.stop();
                gameOver = true;
                showGameoverD();
            }
            repaint();
        });
        
        gameTimer.start();

        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(500, 500));
    }
    
    private void resetLevel() throws Exception {
        labyrinth = game.getCurrentLabyrinth();
        player = game.getPlayer();
        dragon = game.getDragon();
        visibility = game.getVisibility();
        elapsedTime = 0;
        repaint();
    }
    
    private void restartGame() {
        try {
            game = new Game();
            labyrinth = game.getCurrentLabyrinth();
            player = game.getPlayer();
            dragon = game.getDragon();
            visibility = game.getVisibility();
            elapsedTime = 0;
            gameOver = false;
            restartButton.setVisible(false);
            gameTimer.restart();
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error restarting the game: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean[][] visibilityMask = visibility.getVisM();

        int cellSize = Math.min(getWidth() / labyrinth.getCols(), getHeight() / labyrinth.getRows());

        for (int i = 0; i < labyrinth.getRows(); i++) {
            for (int j = 0; j < labyrinth.getCols(); j++) {
                if (visibilityMask[i][j]) {
                    if (labyrinth.isWall(i, j)) {
                        if (wallImage != null) {
                            g.drawImage(wallImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        } else {
                            g.setColor(Color.DARK_GRAY);
                            g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    } else {
                        if (floorImage != null) {
                            g.drawImage(floorImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        } else {
                          g.setColor(Color.LIGHT_GRAY);
                          g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    }
                    
                    if (player.getX() == i && player.getY() == j) {
                        g.drawImage(playerImage, player.getY() * cellSize, player.getX() * cellSize, cellSize, cellSize, null);
                        
                    }
                    
                    if (dragon.getX() == i && dragon.getY() == j) {
                        g.drawImage(dragonImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    }
                } else {
                     g.setColor(Color.BLACK);
                     g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Time: " + elapsedTime + "s", 10, 20);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Player: " + name, 10, 40);
        
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            return; 
        }
        
        String direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> "UP";
            case KeyEvent.VK_A -> "LEFT";
            case KeyEvent.VK_S -> "DOWN";
            case KeyEvent.VK_D -> "RIGHT";
            default -> null;
        };

        if (direction != null) {
            int oldX = player.getX();
            int oldY = player.getY();
            player.move(direction, labyrinth);
            player.completeMove();

            if (player.getX() != oldX || player.getY() != oldY) {
                System.out.println("Player moved to: (" + player.getX() + ", " + player.getY() + ")");
            } else {
                System.out.println("Move is blocked.");
            }
            repaint();
        }

        if (player.getX() == 0 && player.getY() == labyrinth.getCols() - 1) {
            JOptionPane.showMessageDialog(this, "Level Complete!");
            
            solvedL++;
            gameTimer.stop();
            
            if (game.loadNextLevel()) {
                loadGameLevel();
                solvedL++;
            } else {
                solvedL++;
                try {
                    leaderB.addScore(name, solvedL);
                } catch (Exception t) {
                    JOptionPane.showMessageDialog(this, "Error saving to leaderboard: " + t.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Congratulations! You've completed all levels!");
                leaderB.displayLeaderboard();
                gameTimer.stop();
                gameOver = true;
            }
        }

        if (player.isNeighbor(dragon.getX(), dragon.getY()) || (player.getX() == dragon.getX() && player.getY() == dragon.getY())) {
            gameTimer.stop();
            gameOver = true;
            try {
                leaderB.addScore(name, solvedL);
            } catch (Exception r) {
                JOptionPane.showMessageDialog(this, "Error saving to leaderboard: " + r.getMessage());
            }
            showGameoverD();
            leaderB.displayLeaderboard();
        }

    }
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void showGameoverD() {
        restartButton.setText("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setVisible(true);
        restartButton.addActionListener(e -> {
            restartGame();
            SwingUtilities.getWindowAncestor(restartButton).dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel("Game Over! The dragon got you!");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

    
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton okButton = new JButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> SwingUtilities.getWindowAncestor(okButton).dispose());
        panel.add(okButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(restartButton);
        
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }

    private void loadGameLevel() {
        labyrinth = game.getCurrentLabyrinth();
        player = game.getPlayer();
        dragon = game.getDragon();
        visibility = game.getVisibility();
        elapsedTime = 0;
        gameTimer.stop();

        gameTimer = new Timer(500, e -> {
            elapsedTime++;
            dragon.move(labyrinth); 
            if (player.isNeighbor(dragon.getX(), dragon.getY()) || (player.getX() == dragon.getX() && player.getY() == dragon.getY())) {
                gameTimer.stop();
                gameOver = true;
                showGameoverD();
            }
            repaint();
        });
        gameTimer.start();
        repaint();
    }
}