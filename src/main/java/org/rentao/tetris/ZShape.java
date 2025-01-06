package org.rentao.tetris;

public class ZShape extends Shapes {
    private int rotation;

    public ZShape(int rotation, int color) {
        super(color);
        this.rotation = rotation;
        updateRotation(rotation);
        super.setY(0);
        super.setX(3);
        super.setHeight(3);
        super.setWidth(3);
    }

    @Override
    public int[][] rotate(int[][] board) throws InvalidMoveException {
        rotation ++;
        if (rotation > 1) {
            rotation = 0;
        }
        updateRotation(rotation);
        try {
            return paintShape(board);
        } catch (InvalidMoveException e) {
            rotation --;
            if (rotation < 0) {
                rotation = 1;
            }
            updateRotation(rotation);
            throw (new InvalidMoveException());
        }
    }

    @Override
    public void updateRotation(int rotation) {
        int color = super.getColor();
        if (rotation == 0) {
            super.setShape(new int[][]{
                    {0,     0,      0       },
                    {color, color,  0       },
                    {0,     color,  color   }
            });
        } else if (rotation == 1) {
            super.setShape(new int[][]{
                    {0,     color,  0       },
                    {color, color,  0       },
                    {color, 0,      0       }
            });
        }
    }

}