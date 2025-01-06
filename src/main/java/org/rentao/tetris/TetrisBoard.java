package org.rentao.tetris;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class TetrisBoard extends JPanel {

    protected int[][] board = new int[20][10];
    private int[][] displayBoard = new int[20][10];
    private Shapes currShape;
    private Shapes nextShape;
    public static Map<String, Integer> pointsCount = new TreeMap<>();
    private double timeElapsed = 0;

    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static final int INTERVAL = 500;
    protected final Timer timer = new Timer(INTERVAL, e -> tick());
    public final static Map<Integer, Color> colorPicker = Map.of(
            0, Color.BLACK,
            1, Color.RED,
            2, Color.ORANGE,
            3, Color.YELLOW,
            4, Color.GREEN,
            5, Color.BLUE
    );

    protected boolean playing;
    private final JTextArea status;

    public TetrisBoard(JTextArea status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (playing) {
            timer.start();
        }

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                try {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        displayBoard = currShape.move_left(board);
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        displayBoard = currShape.move_right(board);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        displayBoard = currShape.rotate(board);
                    }
                } catch (InvalidMoveException ex) {
                    return;
                }

                try {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        displayBoard = currShape.move_down(board);
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        for (int i = 0; i < 20; i++) {
                            displayBoard = currShape.move_down(board);
                        }
                    }
                } catch (InvalidMoveException ex) {
                    nextRound();
                }
            }
        });
        this.status = status;
    }

    public void reset() {

        board = new int[HEIGHT][WIDTH];
        displayBoard = new int[HEIGHT][WIDTH];

        currShape = randomShape();
        nextShape = randomShape();

        try {
            displayBoard = currShape.paintShape(board);
        } catch (InvalidMoveException e) {
            // This should never happen
        }

        playing = true;
        setFocusable(true);
        pointsCount.clear();
        timeElapsed = 0;
        updateText();
        timer.restart();
        requestFocusInWindow();
    }


    void tick() {
        if (playing) {
            this.timeElapsed += 0.5;

            try {
                displayBoard = currShape.move_down(board);
            } catch (InvalidMoveException ex) {
                nextRound();
            }

            updateText();
            repaint();
        } else {
            timer.stop();
        }
    }

    /**
     * Set the shape down permanently, also checks if game over,
     * and if a row is cleared.
     */
    public void nextRound() {
        board = displayBoard;
        currShape = nextShape;
        nextShape = randomShape();
        try {
            displayBoard = currShape.paintShape(board);
        } catch (InvalidMoveException ex) {
            checkFail();
        }
        checkClear();
        timer.restart();
    }

    /**
     * Check if the blocks have reached y level 1 (second row to top),
     * if so then set playing to false.
     */
    public void checkFail() {
        for (int x = 0; x < 10; x++) {
            if (board[1][x] != 0) {
                String txt = status.getText();
                status.setText(txt + "\n" + "You lose!");
                playing = false;
                break;
            }
        }
    }

    /**
     * Check if there are any rows that are full, and calls clearRow.
     */
    public void checkClear() {
        boolean rowCleared;
        for (int y = 0; y < 20; y++) {
            rowCleared = true;
            for (int x = 0; x < 10; x++) {
                if (board[y][x] == 0) {
                    rowCleared = false;
                    break;
                }
            }
            if (rowCleared) {
                updatePoints(y);
                clearRow(y);
                updateText();
            }
        }
    }

    /**
     * Clear out a row on board and move everything down.
     * Then calls updatePoints.
     * @param row the row to be cleared
     */
    public void clearRow(int row) {
        for (int x =0; x < 10; x++) {
            for (int y = row; y > 0; y--) {
                board[y][x] = board[y-1][x];
            }
        }
    }

    /**
     * Updates the points based on the newly cleared row.
     * @param row the row in which the points are to be accumulated from
     */
    public void updatePoints(int row) {
        for (int x = 0; x < WIDTH; x++) {
            if (board[row][x] == 1) {
                pointsCount.put("red", pointsCount.getOrDefault("red", 0)+10);
            } else if (board[row][x] == 2) {
                pointsCount.put("orange", pointsCount.getOrDefault("orange", 0)+10);
            } else if (board[row][x] == 3) {
                pointsCount.put("yellow", pointsCount.getOrDefault("yellow", 0)+10);
            } else if (board[row][x] == 4) {
                pointsCount.put("green", pointsCount.getOrDefault("green", 0)+10);
            } else if (board[row][x] == 5) {
                pointsCount.put("blue", pointsCount.getOrDefault("blue", 0)+10);}
        }
        pointsCount.put("points", pointsCount.getOrDefault("points", 0)+100);}

    public void updateText() {
        status.setText("Points: "+ pointsCount.getOrDefault("points", 0) + "\n"+ "\n"
                + "Red: " + pointsCount.getOrDefault("red", 0) + "\n"
                + "Orange: " + pointsCount.getOrDefault("orange", 0) + "\n"
                + "Yellow: " + pointsCount.getOrDefault("yellow", 0) + "\n"
                + "Green: " + pointsCount.getOrDefault("green", 0) + "\n"
                + "Blue: " + pointsCount.getOrDefault("blue", 0) + "\n" + "\n"
                + "Time Elapsed: " + "\n"
                + (double)(int) timeElapsed);
        status.repaint();
    }

    /**
     * Generates a random Shapes to put on board.
     * @return random Shapes object
     */
    public Shapes randomShape() {
        int n = new Random().nextInt(7); // Random number between 0 and 6
        return switch (n) {
            case 0 -> new OShape(0, new Random().nextInt(4) + 1);
            case 1 -> new IShape(new Random().nextInt(1), new Random().nextInt(5) + 1);
            case 2 -> new SShape(new Random().nextInt(1), new Random().nextInt(5) + 1);
            case 3 -> new ZShape(new Random().nextInt(1), new Random().nextInt(5) + 1);
            case 4 -> new LShape(new Random().nextInt(3), new Random().nextInt(5) + 1);
            case 5 -> new JShape(new Random().nextInt(3), new Random().nextInt(5) + 1);
            case 6 -> new TShape(new Random().nextInt(3), new Random().nextInt(5) + 1);
            default -> throw new IllegalStateException("Unexpected value: " + n);
        };
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH*20, HEIGHT*20);
    }

    public void draw(Graphics g) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                g.setColor(colorPicker.get(displayBoard[y][x]));
                g.fillRect(x*20, y*20, 20, 20);
            }
        }
    }


    public int getPoints () {
        return pointsCount.getOrDefault("points", 0);
    }


}