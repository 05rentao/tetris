package org.rentao.tetris;

import java.util.Arrays;

public abstract class Shapes {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[][] shape;
    private final int color;


    public Shapes(int color) {
        this.color = color;
    }

    // ===========================================
    // Movers
    //
    // @throws InvalidMoveException when the moved
    // shape intersects with the current board.
    // ===========================================


    public int[][] move_left(int[][] board) throws InvalidMoveException {
        x--;
        try {
            return paintShape(board);
        } catch (InvalidMoveException e) {
            x++;
            throw (new InvalidMoveException());
        }
    }

    public int[][] move_right(int[][] board) throws InvalidMoveException {
        x++;
        try {
            return paintShape(board);
        } catch (InvalidMoveException e) {
            x--;
            throw (new InvalidMoveException());
        }
    }

    public int[][] move_down(int[][] board) throws InvalidMoveException {
        y++;
        try {
            return paintShape(board);
        } catch (InvalidMoveException e) {
            y--;
            throw (new InvalidMoveException());
        }
    }

    /**
     * rotates the shape depending on how many rotations exists for the shape
     * updates int "rotation", and updates 2d array "shape"
     *
     * @param board, the board to paintShape on
     * @return outcome of paintShape, if it exists
     * @throws InvalidMoveException, when the rotated version of the shape
     * intersects with the current board.
     */
    public abstract int[][] rotate(int[][] board) throws InvalidMoveException;

    /**
     * Updates the 2d array "shape" based off of the int "rotation" to reflect
     * what the shape physically looks like.
     *
     * @param rotation the wanted rotation of the shape.
     */
    public abstract void updateRotation(int rotation);

    /**
     * paintShape actually paints the 2D array "shape" onto "board"
     *
     * @param b, the board with the permanent blocks
     *           (not the spaces occupied by currShape)
     * @return a 2d array, typically "displayBoard" that represents the
     * current display of the board
     * @throws InvalidMoveException when the move is such that the shape moves
     *                              into a space already occupied by a block
     *                              or when it is going out of bounds.
     */
    public int[][] paintShape(int[][] b) throws InvalidMoveException {

        int[][] board = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            board[i] = Arrays.copyOf(b[i], b[i].length);
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                try {
                    if (shape[row][col] != 0) {
                        if (board[y + row][x + col] != 0) {
                            throw (new InvalidMoveException("can't move this way"));
                        } else {
                            board[y + row][x + col] = color;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw (new InvalidMoveException("out of bounds"));
                }
            }
        }
        return board;
    }

    public int getColor() {
        return color;
    }

    public void setShape(int[][] s) {
        shape = s;
    }

    public void setX(int i) {
        x = i;
    }

    public void setY(int i) {
        y = i;
    }

    public void setWidth(int i) {
        width = i;
    }

    public void setHeight(int i) {
        height = i;
    }
}