package dev.arami265.GameOfLife;

import dev.arami265.GameOfLife.Display.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

import java.util.Random;

public class GameOfLife implements Runnable {

    private double delta, simulationDelta;
    private int currentFPS;

    private int displayWidth, displayHeight;
    private String title;

    private boolean running = false;
    public static States state;

    private static Thread thread;

    private BufferStrategy buffers;
    private Graphics g;

    //Creates variables for game
    //Board can be different sizes
    private int tileDimension;
    private int tileBorderWidth;

    private int numOfColumns;
    private int numOfRows;


    private int boardXOffset;
    private int boardYOffset;

    //boolean isAlive[][] = new boolean[numOfRows][numOfColumns];
    private Tile[][] tiles;
    private BoardHelper boardHelper;

    // private boolean hasRun;

    public GameOfLife(String title) {
        this.title = title;
    }

    private void init() {
        displayWidth = Config.displayWidth;
        displayHeight = Config.displayHeight;

        tileDimension = Config.tileDimension;
        tileBorderWidth = Config.tileBorderWidth;
        //Figure out how many tiles can fit on the screen
        int boardMargin = Config.boardMargin;

        // The following lines determine exactly what pixels in the window will be rendered to for the board
        numOfColumns = (displayWidth - boardMargin) / (tileDimension + tileBorderWidth);
        numOfRows = (displayHeight - boardMargin) / (tileDimension + tileBorderWidth);

        int boardWidth = (tileDimension * numOfColumns) + (numOfColumns - 1) * tileBorderWidth;
        int boardHeight = (tileDimension * numOfRows) + (numOfRows - 1) * tileBorderWidth;

        boardXOffset = (displayWidth / 2) - (boardWidth / 2);
        boardYOffset = (displayHeight / 2) - (boardHeight / 2);

        //boolean isAlive[][] = new boolean[numOfRows][numOfColumns];
        tiles = new Tile[numOfRows][numOfColumns];
        boardHelper = new BoardHelper();
        // hasRun = false;
        Random rand = new Random();
        double amountFilled = Config.amountFilled;

        //Initializes array of tiles
        for (int i = 0; i < numOfColumns; i++) {
            for (int i0 = 0; i0 < numOfRows; i0++) {
                tiles[i0][i] = new Tile();
                if ((rand.nextFloat()) < amountFilled) {
                    tiles[i0][i].setAlive(true);
                }
            }
        }

        boardHelper.setNeighbors(tiles, numOfColumns, numOfRows);
        state = States.PAUSE;

        Display display = new Display(title, displayWidth, displayHeight);

        //Creates buffer
        display.getCanvas().createBufferStrategy(3);
        buffers = display.getCanvas().getBufferStrategy();
        delta = 1;
    }

    //private double x = boardXOffset, y = boardYOffset;
    private void update() {
        boardHelper.updateBoardState(tiles, numOfColumns, numOfRows);
    }

    private void render() {
        g = buffers.getDrawGraphics();

        //Clear screen
        g.clearRect(0, 0, displayWidth, displayHeight);
        //Draw start

        //Draws the tiles
        for (int col = 0; col < numOfColumns; col++) {
            for (int row = 0; row < numOfRows; row++) {
                if (tiles[row][col].isUpdated())
                    tiles[row][col].setAlive(tiles[row][col].willSurvive());

                if (tiles[row][col].isAlive())
                    g.setColor(Config.tileColor);
                else
                    //g.setColor(new Color(76, 152, 153));
                    g.setColor(Config.boardColor);

                g.fillRect(boardXOffset + (col * tileDimension) + (col * tileBorderWidth), boardYOffset + (row * tileDimension) + (row * tileBorderWidth), tileDimension, tileDimension);
            }
        }
        //Draw end

        buffers.show();
        g.dispose();
    }

    //@Override
    public void run() {
        init();

        double fps = Config.fps, nanoSecPerFrame = 1000000000 / fps, updatesPerSec = Config.updatesPerSec, nanoSecPerUpdate = 1000000000 / updatesPerSec;
        long now, prevTime = System.nanoTime(), timer = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - prevTime) / nanoSecPerFrame;
            simulationDelta += (now - prevTime) / nanoSecPerUpdate;
            timer += now - prevTime;
            prevTime = now;

            if (delta >= 1 && state == States.PLAY && simulationDelta >= 1) {
                update();
                render();

                currentFPS++;

                delta = 0;
                simulationDelta = 0;
            } else if (delta >= 1) {
                render();
                currentFPS++;

                delta = 0;
            }

            //Timer lives here
            if (timer >= 1000000000) {
                System.out.println("FPS: " + currentFPS);
                currentFPS = 0;
                timer = 0;
            }

        }

        stop();
    }

    synchronized void start() {
        running = true;

        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (running) {
            running = false;

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
