package org.improving;

import java.util.Random;

public class Ship {
    int xLength;
    int orientation;

    public Ship(int xLength) {
        this.xLength = xLength;
        this.orientation = new Random().nextInt(3);
        this.orientation = 0;
    }

}
