package fall2018.csc2017.GameCenter;

import java.io.Serializable;

/**
 * The Tile for Mine.
 */
class MineTile implements Serializable {
    /**
     * The value of this mineTile.
     * Representing how many booms nearby.
     */
    private int value;
    /**
     * The boolean of whether this mineTile is opened or not.
     */
    private boolean isOpened;
    /**
     * The x coordinate.
     */
    private int x;
    /**
     * The y coordinate.
     */
    private int y;
    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * Get the value of this mineTile.
     *
     * @return the value of this mineTile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the boolean of whether this mineTile is opened or not.
     *
     * @return the boolean of whether this mineTile is opened or not.
     */
    boolean getIsOpened() { return isOpened; }

    /**
     * Getter for the x coordinate.
     *
     * @return the x coordinate.
     */
    int getX() {
        return x;
    }

    /**
     * Getter for the y coordinate.
     *
     * @return the y coordinate.
     */
    int getY() {
        return y;
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the value of this mineTile.
     *
     * @param value the value of this mineTile.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Setter for the x coordinate.
     *
     * @param x the x coordinate.
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for the y coordinate.
     *
     * @param y the y coordinate.
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * Setter for the background of a tile.
     *
     * @param background the background.
     */
    public void setBackground(int background) { this.background = background; }

    /**
     * The constructor for tiles.
     *
     * @param value the value of tile.
     * @param isOpened whether the tile is opened.
     */
    MineTile(int value, boolean isOpened) {
        this.value = value;
        this.isOpened = isOpened;
        if (!isOpened) {
            background = R.drawable.tile_closed;
        } else {
            switch (value) {
                case -1:
                    background = R.drawable.tile_boom;
                    break;
                case 0:
                    background = R.drawable.tile_0;
                    break;
                case 1:
                    background = R.drawable.tile_1;
                    break;
                case 2:
                    background = R.drawable.tile_2;
                    break;
                case 3:
                    background = R.drawable.tile_3;
                    break;
                case 4:
                    background = R.drawable.tile_4;
                    break;
                case 5:
                    background = R.drawable.tile_5;
                    break;
                case 6:
                    background = R.drawable.tile_6;
                    break;
                case 7:
                    background = R.drawable.tile_7;
                    break;
                case 8:
                    background = R.drawable.tile_8;
                    break;
            }
        }
    }
}
