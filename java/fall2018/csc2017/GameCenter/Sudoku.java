package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The sudoku game board.
 */
public class Sudoku extends Observable implements Serializable, Iterable<Tile> {

    /**
     * Iterator of sudoku game.
     *
     * @return the iterator of sudoku game.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new Sudoku.SudokuIterator();
    }

    /**
     * Iterate over tiles on the sudoku
     */
    private class SudokuIterator implements Iterator<Tile> {

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
                int row = next / size;
                int col = next % size;
                next++;
                return tiles[row][col];
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * The length of the side of sudoku board.
     */
    static final int size = 9;

    /**
     * The tiles on the sudoku board.
     */
    protected Tile[][] tiles = new Tile[size][size];

    /**
     * Return the tiles
     *
     * @return the tiles
     */
    Tile[][] getTileList() {
        return tiles;
    }

    /**
     * A new sudoku of tiles.
     * Precondition: len(tiles) == level * level
     *
     * @param tiles the tiles for the sudoku
     */
    Sudoku(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the size of sudoku.
     *
     * @return the size of sudoku
     */
    int getSize() {
        return size;
    }

    /**
     * Return the number of tiles on the sudoku.
     *
     * @return the number of tiles on the sudoku
     */
    int numTiles() {
        return size * size;
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
     * Write the num into the tiles at (row1, col1)
     *
     * @param row the tile row
     * @param col the tile col
     * @param move the number wanted to be written into the tile
     */
    void writeNum(int row, int col, int move) {
        tiles[row][col] = new Tile(move);
        setChanged();
        notifyObservers();
    }

    /**
     * Convert a sudoku to a String.
     *
     * @return a string representation of a sudoku.
     */
    @Override
    public String toString() {
        return "Sudoku{" +
                "tiles=" + Arrays.toString(getTileList()) +
                '}';
    }
}

