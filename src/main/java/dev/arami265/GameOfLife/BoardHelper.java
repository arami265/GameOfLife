package dev.arami265.GameOfLife;

public class BoardHelper {
    public boolean boardWasClicked(int clickX, int clickY, int boardXOffset, int boardWidth, int boardYOffset, int boardHeight) {
        return (clickX >= boardXOffset && clickX < (boardXOffset + boardWidth)) && (clickY >= boardYOffset && clickY < (boardYOffset + boardHeight));
    }

    public boolean borderWasClicked(int checkX, int checkY, int borderWidth, int tileDimension) {
        for (int i = 1; i <= borderWidth; i++) {
            if (checkX % (tileDimension + i) == 0)
                return true;
            else if (checkY % (tileDimension + i) == 0)
                return true;
        }

        return false;
    }

    void setNeighbors(Tile[][] tiles, int numOfColumns, int numOfRows) {
        //Check neighbors for every tile
        for (int col = 0; col < numOfColumns; col++) {
            for (int row = 0; row < numOfRows; row++) {
                //If north edge
                if (isNorthEdge(row)) {
                    tiles[row][col].setS(tiles[row + 1][col]);
                    if (isWestEdge(col)) {
                        tiles[row][col].setBoardEdge("nw");
                        tiles[row][col].setE(tiles[row][col + 1]);
                        tiles[row][col].setSe(tiles[row + 1][col + 1]);
                    } else if (isEastEdge(col, numOfColumns)) {
                        tiles[row][col].setBoardEdge("ne");
                        tiles[row][col].setW(tiles[row][col - 1]);
                        tiles[row][col].setSw(tiles[row + 1][col - 1]);
                    } else {
                        tiles[row][col].setBoardEdge("n");
                        tiles[row][col].setE(tiles[row][col + 1]);
                        tiles[row][col].setSe(tiles[row + 1][col + 1]);
                        tiles[row][col].setW(tiles[row][col - 1]);
                        tiles[row][col].setSw(tiles[row + 1][col - 1]);
                    }
                }

                //Else If west
                else if (isWestEdge(col)) {
                    tiles[row][col].setE(tiles[row][col + 1]);

                    if (isSouthEdge(row, numOfRows)) {
                        tiles[row][col].setBoardEdge("sw");
                        tiles[row][col].setN(tiles[row - 1][col]);
                        tiles[row][col].setNe(tiles[row - 1][col + 1]);
                    } else {
                        tiles[row][col].setBoardEdge("w");
                        tiles[row][col].setS(tiles[row + 1][col]);
                        tiles[row][col].setSe(tiles[row + 1][col + 1]);
                        tiles[row][col].setN(tiles[row - 1][col]);
                        tiles[row][col].setNe(tiles[row - 1][col + 1]);
                    }
                }

                //Else if east
                else if (isEastEdge(col, numOfColumns)) {
                    tiles[row][col].setW(tiles[row][col - 1]);
                    if (isSouthEdge(row, numOfRows)) {
                        tiles[row][col].setBoardEdge("se");
                        tiles[row][col].setN(tiles[row - 1][col]);
                        tiles[row][col].setNw(tiles[row - 1][col - 1]);
                    } else {
                        tiles[row][col].setBoardEdge("e");
                        tiles[row][col].setS(tiles[row + 1][col]);
                        tiles[row][col].setSw(tiles[row + 1][col - 1]);
                        tiles[row][col].setN(tiles[row - 1][col]);
                        tiles[row][col].setNw(tiles[row - 1][col - 1]);
                    }
                }

                //Else if south
                else if (isSouthEdge(row, numOfRows)) {
                    tiles[row][col].setBoardEdge("s");
                    tiles[row][col].setN(tiles[row - 1][col]);
                    tiles[row][col].setE(tiles[row][col + 1]);
                    tiles[row][col].setNe(tiles[row - 1][col + 1]);
                    tiles[row][col].setW(tiles[row][col - 1]);
                    tiles[row][col].setNw(tiles[row - 1][col - 1]);
                } else {
                    tiles[row][col].setNw(tiles[row - 1][col - 1]);
                    tiles[row][col].setN(tiles[row - 1][col]);
                    tiles[row][col].setNe(tiles[row - 1][col + 1]);
                    tiles[row][col].setW(tiles[row][col - 1]);
                    tiles[row][col].setE(tiles[row][col + 1]);
                    tiles[row][col].setSw(tiles[row + 1][col - 1]);
                    tiles[row][col].setS(tiles[row + 1][col]);
                    tiles[row][col].setSe(tiles[row + 1][col + 1]);
                }
            }
        }
    }

    void updateBoardState(Tile[][] tiles, int numOfColumns, int numOfRows) {
        boolean boardHasChanged = false;
        int liveNeighbors;

        //Check outcome for every tile
        for (int col = 0; col < numOfColumns; col++) {
            for (int row = 0; row < numOfRows; row++) {
                liveNeighbors = 0;

                switch (tiles[row][col].getBoardEdge()) {
                    case "nw":
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSe().isAlive())
                            liveNeighbors++;
                        break;
                    case "n":
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSw().isAlive())
                            liveNeighbors++;
                        break;
                    case "ne":
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSw().isAlive())
                            liveNeighbors++;
                        break;
                    case "w":
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        break;
                    case "e":
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNw().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSw().isAlive())
                            liveNeighbors++;
                        break;
                    case "sw":
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNe().isAlive())
                            liveNeighbors++;
                        break;
                    case "s":
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNw().isAlive())
                            liveNeighbors++;
                        break;
                    case "se":
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNw().isAlive())
                            liveNeighbors++;
                        break;
                    case "none":
                        if (tiles[row][col].getS().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getE().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getW().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getSw().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getN().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNe().isAlive())
                            liveNeighbors++;
                        if (tiles[row][col].getNw().isAlive())
                            liveNeighbors++;
                        break;
                }

                //If the current tile is alive
                if (tiles[row][col].isAlive()) {
                    if (liveNeighbors < 2) {
                        tiles[row][col].setWillSurvive(false);
                        tiles[row][col].setUpdated(true);
                    } else if (liveNeighbors == 2 || liveNeighbors == 3) {
                        tiles[row][col].setWillSurvive(true);
                        tiles[row][col].setUpdated(false);
                    } else {
                        tiles[row][col].setWillSurvive(false);
                        tiles[row][col].setUpdated(true);
                    }
                } else {
                    if (liveNeighbors == 3) {
                        //Dead cell becomes a live cell through reproduction
                        tiles[row][col].setWillSurvive(true);
                        tiles[row][col].setUpdated(true);
                    } else
                        tiles[row][col].setUpdated(false);
                }
            }
        }
    }

    private boolean isNorthEdge(int row) {
        return row == 0;
    }

    private boolean isSouthEdge(int row, int numOfRows) {
        return row == (numOfRows - 1);
    }

    private boolean isWestEdge(int col) {
        return col == 0;
    }

    private boolean isEastEdge(int col, int numOfColumns) {
        return col == (numOfColumns - 1);
    }
}
