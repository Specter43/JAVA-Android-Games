package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The sliding tiles slidingTile.
 */
public class SlidingTile extends Observable implements Serializable, Iterable<Tile> {
    /**
     * The iterator of sliding tile game.
     *
     * @return the iterator of tiles.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }
    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = 806771937776446582L;

    /**
     * Iterate over tiles on the slidingTile
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The next tile.
         */
        private int next;

        /**
         * Check whether there is a next tile.
         *
         * @return a boolean that represent whether there is a next tile.
         */
        @Override
        public boolean hasNext() {
            return numTiles() > next;
        }

        /**
         * Return the next tile.
         *
         * @return the next tile
         */
        @Override
        public Tile next() {
            if (hasNext()) {
                int row = next / level;
                int col = next % level;
                next++;
                return tiles[row][col];
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * The number of level.
     */
    private int level;

    /**
     * The tiles on the slidingTile in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new slidingTile of tiles in row-major order.
     * Precondition: len(tiles) == level * level
     *
     * @param tiles the tiles for the slidingTile.
     * @param level the level.
     */
    SlidingTile(List<Tile> tiles, int level) {
        this.level = level;
        this.tiles = new Tile[level][level];
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != level; row++) {
            for (int col = 0; col != level; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the level.
     *
     * @return the size of slidingTile.
     */
    int getLevel() {
        return level;
    }

    /**
     * Return the number of tiles on the slidingTile.
     *
     * @return the number of tiles on the slidingTile
     */
    int numTiles() {
        return level * level;
    }

    /**
     * Return the tiles
     *
     * @return the tiles
     */
    Tile[][] getTileList() {
        return tiles;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tile_first = getTile(row1, col1);
        Tile tile_second = getTile(row2, col2);
        tiles[row1][col1] = tile_second;
        tiles[row2][col2] = tile_first;
        setChanged();
        notifyObservers();
    }

    /**
     * Convert a slidingTile to a String.
     *
     * @return a string representation of a slidingTile.
     */
    @Override
    public String toString() {
        return "SlidingTile{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}