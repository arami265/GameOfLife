package dev.arami265.GameOfLife;

import dev.arami265.GameOfLife.Display.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

import java.util.Random;

public class GameOfLife implements Runnable {

    private Display display;

    double delta, simulationDelta;
    int currentFPS;

    public int width, height;
    public String title;

    public boolean running = false;
    public static States state;

    private static Thread thread;

    private BufferStrategy buffers;
    private Graphics g;

    //Creates variables for game
    //Board can be different sizes
    int tileDimension;
    int borderWidth;

    //Figure out how many tiles can fit on the screen
    int margin;

    int numOfColumns;
    int numOfRows;


    int boardWidth;
    int boardHeight;

    int boardXOffset;
    int boardYOffset;

    //boolean isAlive[][] = new boolean[numOfRows][numOfColumns];
    public Tile tiles[][];
    BoardHelper boardHelper;

    boolean hasRun;
    Random rand;
    double percentFilled;

    public GameOfLife(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init()
    {
        //Creates variables for game
        //Board can be different sizes
        tileDimension = 5;
        borderWidth = 0;

        //Figure out how many tiles can fit on the screen
        margin = 0;

        numOfColumns = (width - margin) / (tileDimension + borderWidth);
        numOfRows = (height - margin) / (tileDimension + borderWidth);


        boardWidth = (tileDimension * numOfColumns) + (numOfColumns - 1) * borderWidth;
        boardHeight = (tileDimension * numOfRows) + (numOfRows - 1) * borderWidth;

        boardXOffset = (width / 2) - (boardWidth / 2);
        boardYOffset = (height / 2) - (boardHeight / 2);

        //boolean isAlive[][] = new boolean[numOfRows][numOfColumns];
        tiles = new Tile[numOfRows][numOfColumns];
        boardHelper = new BoardHelper();
        hasRun = false;
        rand = new Random();
        percentFilled = .1;

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
        state = States.PAUSE;

        display = new Display(title, width, height);

        //Creates buffer
        display.getCanvas().createBufferStrategy(3);
        buffers = display.getCanvas().getBufferStrategy();
        delta = 1;
    }

    //private double x = boardXOffset, y = boardYOffset;
    private void update()
    {
        boardHelper.updateBoardState(tiles, numOfColumns, numOfRows);
    }

    private void render()
    {
        g = buffers.getDrawGraphics();

        //Clear screen
        g.clearRect(0,0,width,height);
        //Draw start

        for(int col = 0; col < numOfColumns; col++)
        {
            for(int row = 0; row < numOfRows; row++)
            {
                if(tiles[row][col].isUpdated())
                    tiles[row][col].setAlive(tiles[row][col].willSurvive());

                if(tiles[row][col].isAlive())
                    g.setColor(new Color(14, 199, 178));
                else
                    //g.setColor(new Color(76, 152, 153));
                    g.setColor(Color.DARK_GRAY);

                g.fillRect(boardXOffset + (col * tileDimension) + (col * borderWidth), boardYOffset + (row * tileDimension) + (row * borderWidth), tileDimension, tileDimension);
            }
        }
        //Draw end

        buffers.show();
        g.dispose();
    }

    @Override
    public void run()
    {
        init();

        double fps = 30, nanoSecPerFrame = 1000000000 / fps, updatesPerSec = 3, nanoSecPerUpdate = 1000000000 / updatesPerSec;
        long now, prevTime = System.nanoTime(), timer = 0;

        while(running)
        {
            now = System.nanoTime();
            delta += (now - prevTime) / nanoSecPerFrame;
            simulationDelta += (now - prevTime) / nanoSecPerUpdate;
            timer += now - prevTime;
            prevTime = now;

            if(delta >= 1 && state == States.PLAY && simulationDelta >= 1)
            {
                update();
                render();

                currentFPS++;

                delta = 0;
                simulationDelta = 0;
            }
            else if(delta >= 1)
            {
                render();
                currentFPS++;

                delta = 0;
            }

            //Timer lives here
            if(timer >= 1000000000)
            {
                System.out.println("FPS: " + currentFPS);
                currentFPS = 0;
                timer = 0;
            }

        }

        stop();
    }

    public synchronized void start()
    {
        running = true;

        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop()
    {
        if(running)
        {
            running = false;

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
