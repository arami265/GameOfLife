package dev.arami265.GameOfLife;

class Tile {
    private boolean isAlive;
    private boolean willSurvive;
    private Tile nw, n, ne, w, e, sw, s, se;
    private String boardEdge;
    private boolean updated;

    Tile() {
        isAlive = false;
        willSurvive = false;
        nw = null;
        n = null;
        ne = null;
        w = null;
        e = null;
        sw = null;
        s = null;
        se = null;
        boardEdge = "none";
        updated = false;
    }

    //Getters
    boolean isAlive() {
        return isAlive;
    }

    boolean willSurvive() {
        return willSurvive;
    }

    Tile getNw() {
        return nw;
    }

    Tile getN() {
        return n;
    }

    Tile getNe() {
        return ne;
    }

    Tile getW() {
        return w;
    }

    Tile getE() {
        return e;
    }

    Tile getSw() {
        return sw;
    }

    Tile getS() {
        return s;
    }

    Tile getSe() {
        return se;
    }

    String getBoardEdge() {
        return boardEdge;
    }

    boolean isUpdated() {
        return updated;
    }

    //Setters
    void setAlive(boolean alive) {
        isAlive = alive;
    }

    void setWillSurvive(boolean willSurvive) {
        this.willSurvive = willSurvive;
    }

    void setNw(Tile nw) {
        this.nw = nw;
    }

    void setN(Tile n) {
        this.n = n;
    }

    void setNe(Tile ne) {
        this.ne = ne;
    }

    void setW(Tile w) {
        this.w = w;
    }

    void setE(Tile e) {
        this.e = e;
    }

    void setSw(Tile sw) {
        this.sw = sw;
    }

    void setS(Tile s) {
        this.s = s;
    }

    void setSe(Tile se) {
        this.se = se;
    }

    void setBoardEdge(String boardEdge) {
        this.boardEdge = boardEdge;
    }

    void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
