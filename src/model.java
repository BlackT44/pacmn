import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class Model extends JPanel {

    private int[][] map;
    private int tileSize = 20;
    private int pacmanX, pacmanY; // Pac-Man's position
    private List<Ghost> ghosts; // List of ghosts
    private int ghostSpeed = 1; // Speed of ghost movement
    private int lives = 3; // Number of Pac-Man lives
    private Timer timer; // Timer for game updates

    private Image pacmanImage; // Pac-Man image
    private Image ghostImage;  // Ghost image

    public Model(int difficulty) {

        // Adjust ghost speed based on difficulty
        ghostSpeed = difficulty;


        // Initialize the game map
        initializeMap();

        // Place Pac-Man at the starting position
        pacmanX = 1;
        pacmanY = 1;

        // Initialize ghosts
        initializeGhosts();

        // Add a key listener for movement
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePacman(e.getKeyCode());
                repaint();
            }
        });

        // Start the game timer
        timer = new Timer(200, e -> {
            moveGhosts();
            checkCollisions();
            repaint();
        });
        timer.start();

    }




    private void initializeMap() {
        // 0 = empty space, 1 = wall, 2 = food
        map = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1},
                {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1},
                {1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 2, 1},
                {1, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
    }

    private void initializeGhosts() {
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(9, 6)); // Add a ghost at position (9, 6)
        ghosts.add(new Ghost(10, 10)); // Add another ghost
    }

    private void movePacman(int keyCode) {
        int newX = pacmanX;
        int newY = pacmanY;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                newY--;
                break;
            case KeyEvent.VK_DOWN:
                newY++;
                break;
            case KeyEvent.VK_LEFT:
                newX--;
                break;
            case KeyEvent.VK_RIGHT:
                newX++;
                break;
        }

        // Check boundaries and collisions
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length && map[newY][newX] != 1) {
            pacmanX = newX;
            pacmanY = newY;

            // Eat food
            if (map[pacmanY][pacmanX] == 2) {
                map[pacmanY][pacmanX] = 0;
            }
        }
    }


    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            int newX = ghost.x + ghost.dx * ghostSpeed;
            int newY = ghost.y + ghost.dy * ghostSpeed;

            // Change direction if ghost hits a wall
            if (newX < 0 || newX >= map[0].length || newY < 0 || newY >= map.length || map[newY][newX] == 1) {
                ghost.changeDirection();
            } else {
                ghost.x = newX;
                ghost.y = newY;
            }

            // Check collision with Pac-Man
            if (ghost.x == pacmanX && ghost.y == pacmanY) {
                lives--;
                if (lives == 0) {
                    JOptionPane.showMessageDialog(this, "Game Over!");
                    System.exit(0);
                }
            }
        }
    }

    private void checkCollisions() {
        for (Ghost ghost : ghosts) {
            if (ghost.x == pacmanX && ghost.y == pacmanY) {
                lives--;
                if (lives == 0) {
                    JOptionPane.showMessageDialog(this, "Game Over!");
                    System.exit(0);
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else if (map[y][x] == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(x * tileSize + tileSize / 4, y * tileSize + tileSize / 4, tileSize / 2, tileSize / 2);
                }
            }
        }

        // Draw Pac-Man
        g.setColor(Color.ORANGE);
        g.fillOval(pacmanX * tileSize, pacmanY * tileSize, tileSize, tileSize);

        // Draw Ghosts
        g.setColor(Color.RED);
        for (Ghost ghost : ghosts) {
            g.fillOval(ghost.x * tileSize, ghost.y * tileSize, tileSize, tileSize);
        }


        // Draw Lives
        g.setColor(Color.GREEN);
        g.drawString("Lives: " + lives, 10, 20);
    }



    // Inner class for Ghost
    class Ghost {
        int x, y;
        int dx = 0, dy = 1; // Initial direction

        public Ghost(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void changeDirection() {
            // Randomize direction
            dx = (int) (Math.random() * 3) - 1;
            dy = (int) (Math.random() * 3) - 1;

            // Ensure ghosts don't stop moving
            if (dx == 0 && dy == 0) {
                dx = 1;
            }
        }
    }
}
