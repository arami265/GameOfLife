package dev.arami265.GameOfLife;

public class Tile {
    boolean isAlive;
    boolean willSurvive;
    Tile nw, n, ne, w, e, sw, s, se;
    String boardEdge;
    boolean updated;

    public Tile() {
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
    public boolean isAlive() {
        return isAlive;
    }

    public boolean willSurvive() {
        return willSurvive;
    }

    public Tile getNw() {
        return nw;
    }

    public Tile getN() {
        return n;
    }

    public Tile getNe() {
        return ne;
    }

    public Tile getW() {
        return w;
    }

    public Tile getE() {
        return e;
    }

    public Tile getSw() {
        return sw;
    }

    public Tile getS() {
        return s;
    }

    public Tile getSe() {
        return se;
    }

    public String getBoardEdge() {
        return boardEdge;
    }

    public boolean isUpdated() {
        return updated;
    }

    //Setters
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setWillSurvive(boolean willSurvive) {
        this.willSurvive = willSurvive;
    }

    public void setNw(Tile nw) {
        this.nw = nw;
    }

    public void setN(Tile n) {
        this.n = n;
    }

    public void setNe(Tile ne) {
        this.ne = ne;
    }

    public void setW(Tile w) {
        this.w = w;
    }

    public void setE(Tile e) {
        this.e = e;
    }

    public void setSw(Tile sw) {
        this.sw = sw;
    }

    public void setS(Tile s) {
        this.s = s;
    }

    public void setSe(Tile se) {
        this.se = se;
    }

    public void setBoardEdge(String boardEdge) {
        this.boardEdge = boardEdge;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
