/**
 * Hello world!
 * Author: Arnold Ramirez
 *
 * Runs Conway's Game of Life
 * [under construction]
 **/

package io.github.arami265;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game implements Runnable
{

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    JFrame frame;
    Canvas canvas;
    BufferStrategy bufferStrategy;

    //Creates variables for game
    //Board can be different sizes
    int tileDimension = 30;
    int colNum = 30;
    int rowNum = 10;
    int boardWidth = (tileDimension * colNum) + colNum - 1;
    int boardHeight = (tileDimension * rowNum) + rowNum - 1;

    int boardXOffset = (WIDTH / 2) - (boardWidth / 2);
    int boardYOffset = (HEIGHT / 2) - (boardHeight / 2);

    boolean hasBeenClicked[][] = new boolean[rowNum][colNum];

    boolean startGame = false, hasRun = false;

    public Game(){
        frame = new JFrame("Conway's Game of Life");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        canvas.addMouseListener(new MouseControl());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();

        //Initializes array that keeps track of clicked tiles
        for(int i = 0; i < colNum; i++)
        {
            for(int i0 = 0; i0 < rowNum; i0++)
            {
                hasBeenClicked[i0][i] = false;
            }
        }
    }


    private class MouseControl extends MouseAdapter{
        public void mousePressed(MouseEvent me)
        {
            //This commented code flags that the game
            //has started running. In update() you can
            //see some animation that starts and stops
            //with this mouse click.
            /*if(!startGame)
                startGame = true;
            else
                startGame = false;*/

            int clickX = me.getX(), clickY = me.getY();
            //Clicks are put into the context of the board for now
            //TODO: Add menu buttons and handle them here as well
            clickX -= boardXOffset;
            clickY -= boardYOffset;

            //These variables are for determining whether a
            //border between tiles is clicked
            int checkX = clickX - tileDimension, checkY = clickY - tileDimension;

            if(clickX < 0 || clickX > boardWidth || clickY < 0 || clickY > boardHeight)
            {
                //If the click is outside the border, nothing happens
            }
            else if(checkX % (tileDimension + 1) == 0)
            {
                //Nothing. A border has been reached
            }
            else if(checkY % (tileDimension + 1) == 0)
            {
                //Nothing. A border has been reached
            }
            //Otherwise, a tile has been clicked
            //and the array is affected
            else
            {
                int clickedTileX = clickX / (tileDimension + 1), clickedTileY = clickY / (tileDimension + 1);

                if(!hasBeenClicked[clickedTileY][clickedTileX])
                    hasBeenClicked[clickedTileY][clickedTileX] = true;
                else
                    hasBeenClicked[clickedTileY][clickedTileX] = false;
            }
        }
    }

    long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    boolean running = true;

    public void run(){

        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        while(running){
            beginLoopTime = System.nanoTime();

            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if(deltaLoop > desiredDeltaLoop){
                //Do nothing. We are already late.
            }else{
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                }catch(InterruptedException e){
                    //Do nothing
                }
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    //Variables for update method
    //deltaCount is for tracking how many seconds pass
    private double x = boardXOffset, y = boardYOffset;
    int deltaCount = 0;


    protected void update(int deltaTime)
    {
        //This code makes a colored tile move across
        //every row in a loop when tapped, pausing when
        //tapped.
        //TODO:Replace this trivial animation with the rules for Conway's Game of Life
        /*if(startGame)
        {
            deltaCount += deltaTime;
            if (deltaCount >= 100)
            {
                x += tileDimension + 1;
                if (x - boardXOffset > boardWidth - tileDimension)
                {
                    x = boardXOffset;

                    y += tileDimension + 1;
                    if (y - boardYOffset > boardHeight - tileDimension)
                    {
                        y = boardYOffset;
                    }
                }
                deltaCount = 0;
            }
        }*/
    }

    //For now just draws a grid of tiles,
    //which change color when clicked
    protected void render(Graphics2D g){

        for(int i = 0; i < colNum; i++)
        {
            for(int i0 = 0; i0 < rowNum; i0++)
            {
                if(hasBeenClicked[i0][i])
                    g.setColor(Color.CYAN);
                else
                    g.setColor(Color.BLACK);

                g.fillRect(boardXOffset + (i * tileDimension) + i, boardYOffset + (i0 * tileDimension) + i0, tileDimension, tileDimension);
            }
        }

        //This code fills one tile with cyan to fulfill animation
        /*if(hasRun)
        {
            g.setColor(Color.CYAN);
            g.fillRect((int) x, (int) y, 25, 25);
            g.setColor(Color.BLACK);
        }*/
    }

    public static void main( String[] args )
    {
        Game game = new Game();
        new Thread(game).start();
    }
}
