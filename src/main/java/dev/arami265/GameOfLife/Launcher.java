package dev.arami265.GameOfLife;

public class Launcher {

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife("Conway's Game of Life", 1300, 900);
        game.start();
    }

}
