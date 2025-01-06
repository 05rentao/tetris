package org.rentao.tetris;

public class InvalidMoveException extends Exception
{
    public InvalidMoveException() {}

    public InvalidMoveException(String message)
    {
        super(message);
    }

}
