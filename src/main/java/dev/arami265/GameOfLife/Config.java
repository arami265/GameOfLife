package dev.arami265.GameOfLife;

import java.awt.*;

final class Config {
    // Size of the display window
    static final int displayWidth = 1300;
    static final int displayHeight = 900;

    // Target FPS for the entire rendering
    static final int fps = 30;
    // Target updates per second for the actual simulation;
    // this way the window can be rendered at a steady rate
    // while the simulation's updates per sec can be changed.
    // (This number can be between 1 and the fps set above)
    static final int updatesPerSec = 2;

    // The size of the tiles; if not divisible by displayWidth or displayHeight, the best board size will be determined.
    static final int tileDimension = 5;
    // Number of pixels to border each tile. Adjusting this can be interesting visually.
    static final int tileBorderWidth = 0;

    //The margin around the entire board.
    static final int boardMargin = 0;

    // Value between 0 and 1; this determines how filled the board is when starting.
    // .1 == 10% filled
    static final double amountFilled = .1;

    // Color of tiles
    static final Color tileColor = new Color(14, 199, 178);
    // Color of background
    static final Color boardColor = Color.DARK_GRAY;
}
