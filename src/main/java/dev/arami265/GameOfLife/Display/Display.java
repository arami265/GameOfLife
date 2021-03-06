package dev.arami265.GameOfLife.Display;

import dev.arami265.GameOfLife.GameOfLife;
import dev.arami265.GameOfLife.States;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;


    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        initDisplay();
    }

    private void initDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.addMouseListener(new MouseControl());

        frame.add(canvas);
        frame.pack();

        frame.setVisible(true);
    }

    private class MouseControl extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            //int clickX = me.getX(), clickY = me.getY();

            if (GameOfLife.state == States.PAUSE)
                GameOfLife.state = States.PLAY;
            else
                GameOfLife.state = States.PAUSE;
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Frame getFrame() {
        return frame;
    }
}
