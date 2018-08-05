/**
 * Conway's Game of Life [under construction]
 * Author: Arnold Ramirez
 *
 * More info in readme
 *
 *
 * Right now the simulation starts on mouse click.
 * More features to be added.
 *
 * Variables like tileDimension and timeToWait will change simulation.
 **/

package io.github.arami265;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game implements Runnable
{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final int WIDTH = (int)screenSize.getWidth() + 1;
    final int HEIGHT = (int)screenSize.getHeight() - 22;

    JFrame frame;
    Canvas canvas;
    BufferStrategy bufferStrategy;

    //Creates variables for game
    //Board can be different sizes
    final int tileDimension = 4;
    final int borderWidth = 0;

    //Figure out how many tiles can fit on the screen
    final int margin = 20;

    final int numOfColumns = (WIDTH - margin) / (tileDimension + borderWidth);
    final int numOfRows = (HEIGHT - margin) / (tileDimension + borderWidth);


    final int boardWidth = (tileDimension * numOfColumns) + (numOfColumns - 1) * borderWidth;
    final int boardHeight = (tileDimension * numOfRows) + (numOfRows - 1) * borderWidth;

    final int boardXOffset = (WIDTH / 2) - (boardWidth / 2);
    final int boardYOffset = (HEIGHT / 2) - (boardHeight / 2);

    //boolean isAlive[][] = new boolean[numOfRows][numOfColumns];
    public Tile tiles[][] = new Tile[numOfRows][numOfColumns];
    BoardHelper boardHelper = new BoardHelper();

    boolean isRunning = false, hasRun = false;
    Random rand = new Random();
    double percentFilled = .2;

    public Game()
    {
        frame = new JFrame("Conway's Game of Life");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        canvas.addMouseListener(new MouseControl());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();

        //Initializes array of tiles
        for(int i = 0; i < numOfColumns; i++)
        {
            for(int i0 = 0; i0 < numOfRows; i0++)
            {
                tiles[i0][i] = new Tile();
                if((rand.nextFloat()) < percentFilled)
                {
                    tiles[i0][i].setAlive(true);
                }
            }
        }

        boardHelper.setNeighbors(tiles, numOfColumns, numOfRows);
    }

    private class MouseControl extends MouseAdapter{
        public void mousePressed(MouseEvent me)
        {
            //int clickX = me.getX(), clickY = me.getY();

            if(!isRunning)
                isRunning = true;
            else
                isRunning = false;

            if(isRunning)
            {
                //Ignore board clicks
                /*if(boardHelper.boardWasClicked(clickX, clickY,boardXOffset,boardWidth,boardYOffset,boardHeight))
                {
                    //Nothing.
                }

                //Switch game state on click outside of board
                //TODO: Add menu buttons and handle them here as well
                else
                {
                    if(!isRunning)
                        isRunning = true;
                    else
                        isRunning = false;
                }*/
            }
            //If the game is not running
            else
            {
                //Board clicks are registered as setting tiles
                /*if(boardHelper.boardWasClicked(clickX, clickY,boardXOffset,boardWidth,boardYOffset,boardHeight))
                {
                    //Puts click into context of board instead of total canvas size

                    int clickXBoard = clickX - boardXOffset;
                    int clickYBoard = clickY - boardYOffset;

                    //These variables are for determining whether a
                    //border between tiles is clicked
                    int checkX = clickXBoard - tileDimension, checkY = clickYBoard - tileDimension;

                    if(clickXBoard < 0 || clickXBoard > boardWidth || clickYBoard < 0 || clickYBoard > boardHeight)
                    {
                        //If the click is outside the border, nothing happens
                    }
                    else if(boardHelper.borderWasClicked(checkX, checkY, borderWidth, tileDimension))
                    {
                        //Nothing. A border has been reached
                    }

                    //Otherwise, a tile has been clicked
                    //and the array is affected
                    else
                    {
                        int clickedTileX = clickXBoard / (tileDimension + borderWidth), clickedTileY = clickYBoard / (tileDimension + borderWidth);

                        if(!tiles[clickedTileY][clickedTileX].isAlive)
                            tiles[clickedTileY][clickedTileX].setAlive(true);
                        else
                            tiles[clickedTileY][clickedTileX].setAlive(false);
                    }
                }
                //If the board is not clicked, the game state changes
                else
                {
                    if(!isRunning)
                        isRunning = true;
                    else
                        isRunning = false;
                }*/
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

    int genCount = 1;
    //Time to wait is in ms
    int timeToWait = 100;

    protected void update(int deltaTime)
    {
        if(isRunning)
        {
            deltaCount += deltaTime;
            if (deltaCount >= timeToWait)
            {

                boardHelper.updateBoardState(tiles, numOfColumns, numOfRows);
                genCount++;

                deltaCount = 0;
            }
        }
    }

    protected void render(Graphics2D g){

        if(isRunning)
        {
            g.setColor(new Color(14, 199, 178));
            g.fillRect(0,0,WIDTH,HEIGHT);
           // g.setColor(new Color(36, 71, 67));
            //g.drawString("Generation: " + genCount, boardXOffset, 24);

            //Count neighbors
        }
        else
        {
            g.setColor(new Color(36, 71, 67));
            g.fillRect(0,0,WIDTH,HEIGHT);
            //g.setColor(new Color(14, 199, 178));
            //g.drawString("Generation: " + genCount, boardXOffset, 24);
        }

        for(int col = 0; col < numOfColumns; col++)
        {
            for(int row = 0; row < numOfRows; row++)
            {
                if(tiles[row][col].isUpdated() && isRunning)
                    tiles[row][col].setAlive(tiles[row][col].willSurvive());

                if(tiles[row][col].isAlive())
                    g.setColor(new Color(14, 199, 178));
                else
                    g.setColor(new Color(76, 152, 153));

                g.fillRect(boardXOffset + (col * tileDimension) + (col * borderWidth), boardYOffset + (row * tileDimension) + (row * borderWidth), tileDimension, tileDimension);
            }
        }

        //This code fills one tiles with cyan to fulfill animation
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
