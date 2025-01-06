package org.rentao.tetris;

import javax.swing.*;
import java.awt.*;

public class RunTetris implements Runnable {

    @Override
    public void run() {
        final JFrame frame = new JFrame("TOP LEVEL FRAME");
        frame.setLocation(300, 300);

        // bottom: Status
        boolean flowLayout = true;
        final JPanel points_Panel = new JPanel(flowLayout);
        final JTextArea status = new JTextArea("Running...");
        points_Panel.add(status);
        frame.add(points_Panel, BorderLayout.EAST);

        // Main playing area
        final TetrisBoard board = new TetrisBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Bottom: reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        final JButton guide = new JButton("Guide");
        guide.addActionListener(e -> {JOptionPane.showMessageDialog(null,
                "up arrow = rotate clockwise" + "\n" +
                        "down arrow = move down" + "\n" +
                        "left arrow = move left" + "\n" +
                        "right arrow = move right", "guide", JOptionPane.PLAIN_MESSAGE);});
        final JPanel control_panel = new JPanel(flowLayout);
        control_panel.add(reset);
        control_panel.add(guide);
        frame.add(control_panel, BorderLayout.SOUTH);

        // Top: tetris sign
        final JLabel sign = new JLabel("Tetris");
        boolean FlowLayout = true;
        final JPanel sign_panel = new JPanel(FlowLayout);
        sign_panel.add(sign);
        frame.add(sign_panel, BorderLayout.NORTH);

        // Put the frame on the screen
        frame.setSize(305, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();

    }
}