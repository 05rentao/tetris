package org.rentao.tetris;

public class OShape extends Shapes {
    private final int rotation;

    public OShape(int rotation, int color) {
        super(color);
        this.rotation = rotation;
        updateRotation(rotation);
        super.setY(0);
        super.setX(4);
        super.setHeight(2);
        super.setWidth(2);
    }

    @Override
    public int[][] rotate(int[][] board) throws InvalidMoveException {
        try {
            return paintShape(board);
        } catch (InvalidMoveException e) {
            updateRotation(rotation);
            throw (new InvalidMoveException());
        }
    }

    @Override
    public void updateRotation(int rotation) {
        int color = super.getColor();
        setShape( new int[][]{
                {color,     color   },
                {color,     color   },
        });
    }

}