import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static int DELAY = 200; // slower start

    Snake snake;
    Food food;

    boolean running = false;
    Timer timer;
    int score = 0;

    JButton restartButton; 

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        this.setLayout(null); // required for button positioning

        restartButton = new JButton("Try Again");
        restartButton.setBounds(220, 400, 150, 40);
        restartButton.setFocusable(false);
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());

        this.add(restartButton);

        startGame();
    }

    void startGame() {
        snake = new Snake();
        food = new Food();
        score = 0;

        running = true;
        DELAY = 250;

        timer = new Timer(DELAY, e -> gameLoop());
        timer.start();
    }

    void restartGame() {
        snake = new Snake();
        food = new Food();
        score = 0;

        running = true;
        DELAY = 250;

        timer.setDelay(DELAY);
        timer.start();

        restartButton.setVisible(false);
        this.requestFocusInWindow(); // regain keyboard focus
    }

    void gameLoop() {
        if (running) {
            snake.move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    void checkFood() {
        if (snake.x[0] == food.x && snake.y[0] == food.y) {
            snake.bodyParts++;
            score++;
            food.generate();

            //
            if (DELAY > 50) {
                DELAY -= 5;
                timer.setDelay(DELAY);
            }
        }
    }

    void checkCollisions() {

        // self collision
        for (int i = snake.bodyParts; i > 0; i--) {
            if (snake.x[0] == snake.x[i] && snake.y[0] == snake.y[i]) {
                running = false;
            }
        }

        // wall collision
        if (snake.x[0] < 0 || snake.x[0] >= WIDTH ||
            snake.y[0] < 0 || snake.y[0] >= HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    void draw(Graphics g) {

        if (running) {

            // food
            g.setColor(Color.red);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // snake
            for (int i = 0; i < snake.bodyParts; i++) {
                if (i == 0)
                    g.setColor(Color.green);
                else
                    g.setColor(new Color(45, 180, 0));

                g.fillRect(snake.x[i], snake.y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // score
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);

        } else {
            gameOver(g);
        }
    }

    void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over", 180, 300);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 240, 350);

        restartButton.setVisible(true); 
    }

    class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.direction != 'R')
                        snake.direction = 'L';
                    break;

                case KeyEvent.VK_RIGHT:
                    if (snake.direction != 'L')
                        snake.direction = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if (snake.direction != 'D')
                        snake.direction = 'U';
                    break;

                case KeyEvent.VK_DOWN:
                    if (snake.direction != 'U')
                        snake.direction = 'D';
                    break;
            }
        }
    }
}