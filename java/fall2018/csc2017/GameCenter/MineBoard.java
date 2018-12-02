package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;

/**
 * The Mine board.
 */
class MineBoard extends Observable implements Serializable, Iterable<MineTile> {
    /**
     * The board's number of booms.
     */
    private int numBoom;
    /**
     * The board's size.
     */
    private final static int size = 16;
    /**
     * The mark of whether the user tapped for at least once.
     */
    private boolean firstTap;
    /**
     * The 2D array of tiles.
     */
    private MineTile[][] mineTile = new MineTile[size][size];
    /**
     * The randomization of the tiles(booms).
     */
    private Random randomize;
    /**
     * The surrounding_directions.
     */
    private int[][] surrounding_directions = {
            {-1, 1},//upper-left
            {0, 1},//upper
            {1, 1},//upper-right
            {-1, 0},//left
            {1, 0},//right
            {-1, -1},//lower-left
            {0, -1},//lower
            {1, -1}};//lower-right

    /**
     * Getter for the size of the board.
     *
     * @return the size of the board.
     */
    public static int getSize() {
        return size;
    }

    /**
     * Getter for the number of booms.
     *
     * @return the number of booms.
     */
    int getNumBoom() {
        return numBoom;
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row.
     * @param col the tile column.
     * @return the tile at (row, col).
     */
    MineTile getMineTile(int row, int col) {
        return mineTile[row][col];
    }

    /**
     * Getter for firstTap.
     *
     * @return whether tapped once or not.
     */
    boolean isFirstTap() {
        return firstTap;
    }

    /**
     * Setter for the number of booms.
     *
     * @param numBoom the number of booms.
     */
    void setNumBoom(int numBoom) {
        this.numBoom = numBoom;
    }

    /**
     * Set the firstTap to false.
     */
    void setFirstTapToFalse() {
        this.firstTap = false;
    }

    /**
     * Get the iterator for tiles.
     *
     * @return the iterator for tiles.
     */
    @NonNull
    @Override
    public Iterator<MineTile> iterator() {
        return new MineBoard.MineTileIterator();
    }

    /**
     * Iterate over tiles on the slidingTile.
     */
    private class MineTileIterator implements Iterator<MineTile> {

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
            return MineBoard.getSize() * MineBoard.getSize() > next;
        }

        /**
         * Return the next tile.
         *
         * @return the next tile
         */
        @Override
        public MineTile next() {
            if (hasNext()) {
                int row = next / size;
                int col = next % size;
                next++;
                return mineTile[row][col];
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * A new Mine game board.
     * Precondition: len(mineTiles) == level * level
     *
     * @param tiles the tiles for the mine game.
     */
    MineBoard(List<MineTile> tiles, int numBoom, Random randomize) {
        this.numBoom = numBoom;
        this.randomize = randomize;
        this.firstTap = true;
        Iterator<MineTile> iterator = tiles.iterator();

        for (int col = 0; col != size; col++) {
            for (int row = 0; row != size; row++) {
                this.mineTile[row][col] = iterator.next();
                this.mineTile[row][col].setX(row);
                this.mineTile[row][col].setY(col);
            }
        }
    }

    /**
     * Randomly Generate booms.
     *
     * @param exception This position doesn't contain booms.
     */
    private void createBooms(MineTile exception) {
        List<MineTile> allTile = new ArrayList<>();

        //Add all the positions, except the first tapping one.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (mineTile[row][col] != exception) {
                    allTile.add(mineTile[row][col]);
                }
            }
        }
        List<MineTile> boomTile = randomGenerateBoomsList(allTile);
        //Mark the position of booms.
        for (MineTile nextBoomTile : boomTile) {
            mineTile[nextBoomTile.getX()][nextBoomTile.getY()].setValue(-1);
            buildBoom(nextBoomTile.getX(), nextBoomTile.getY());
        }
        //Add number to some tiles.
        setChanged();
        notifyObservers();
    }

    /**
     * randomly Generate BoomsList.
     *
     * @param givenTile a not random BoomsList.
     * @return a random BoomsList.
     */
    private List<MineTile> randomGenerateBoomsList(List<MineTile> givenTile){
        List<MineTile> boomTile = new LinkedList<>();
        for (int i = 0; i < numBoom; i++) {
            int idx = randomize.nextInt(givenTile.size());
            boomTile.add(givenTile.get(idx));
            givenTile.remove(idx);
        }
        return boomTile;
    }

    /**
     * Build boom on given row and col.
     */
    private void buildBoom(int row, int col){
        for (int k = 0; k < 8; k++) {
            int surroundingX = row + surrounding_directions[k][0],
                    surroundingY = col + surrounding_directions[k][1];
            if (surroundingX >= 0 && surroundingX < size && surroundingY >= 0 &&
                    surroundingY < size) {
                int currentValue = this.mineTile[surroundingX][surroundingY].getValue();
                if (currentValue != -1)
                    currentValue += 1;
                this.mineTile[surroundingX][surroundingY].setValue(currentValue);
            }
        }
    }

    /**
     * Tap to open some position.
     *
     */
    void touchOpen(int position) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        if (firstTap) {
            createBooms(mineTile[row][col]);
            setFirstTapToFalse();
        }
        replaceToTrue(row, col);
        if (mineTile[row][col].getValue() == -1 && mineTile[row][col].getIsOpened()) {
            displayBoom();
        }
        //tap the mineTile with a number.
        else if (mineTile[row][col].getValue() == 0) {
            Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
            queue = putSurroundingOnQueue(row, col, queue);
            recursiveSurroundingOnQueue(queue);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Open a tile with a replacement that contains all the information of the tile.
     *
     * @param row The row of the mine tile that needs to be replaced.
     * @param col The col of the mine tile that needs to be replaced.
     */
    void replaceToTrue(int row, int col) {
        mineTile[row][col] = new MineTile(mineTile[row][col].getValue(), true);
    }

    /**
     * Replace a closed tile to flag tile to mark booms.
     *
     * @param row the row of the tile.
     * @param col the col of the tile.
     */
    void replaceToFlag(int row, int col) {
        if (mineTile[row][col].getBackground() == R.drawable.tile_flagged) {
            mineTile[row][col].setBackground(R.drawable.tile_closed);
        }
        else {
            mineTile[row][col].setBackground(R.drawable.tile_flagged);}
        setChanged();
        notifyObservers();
    }

    /**
     * Display all boom when the user failed.
     */
    private void displayBoom() {
        for (int boomRow = 0; boomRow < size; boomRow++) {
            for (int boomCol = 0; boomCol < size; boomCol++) {
                if (mineTile[boomRow][boomCol].getValue() == -1) {
                    replaceToTrue(boomRow, boomCol);
                }
            }
        }
    }

    /**
     * Recursively put surrounding empty mine tiles into the queue.
     *
     * @param queue the queue that stores all the surrounding empty mine tiles.
     *              Note: the "surrounding" might recursively gets bigger until there is a number
     *              tile border.
     */
    private void recursiveSurroundingOnQueue
    (Queue<Pair<Integer, Integer>> queue) {
        if (queue.size() != 0) {
            Pair<Integer, Integer> pointPair = queue.poll();
            if (pointPair.first != null && pointPair.second != null) {
                int row = pointPair.first;
                int col = pointPair.second;
                replaceToTrue(row, col);
                putSurroundingOnQueue(row, col, queue);
                recursiveSurroundingOnQueue(queue);
            }
        }
    }

    /**
     * Put the surrounding empty mine tiles in the queue.
     * Helper for recursiveSurroundingOnQueue.
     *
     * @param row   The row of the mine tile.
     * @param col   The col of the mine tile.
     * @param queue The queue of empty mine tiles.
     * @return The modified queue.
     */
    private Queue<Pair<Integer, Integer>> putSurroundingOnQueue
    (int row, int col, Queue<Pair<Integer, Integer>> queue) {
        for (int i = 0; i < 8; i++) {
            Integer surroundingX = row + surrounding_directions[i][0],
                    surroundingY = col + surrounding_directions[i][1];
            //Check given minePoint's surroundings and whether they are opened or not.
            boolean isOpenable = surroundingX >= 0 && surroundingX < size &&
                    surroundingY >= 0 && surroundingY < size;
            if (isOpenable) {
                //Make all surroundings empty tiles appear inside a number tile border.
                if (mineTile[surroundingX][surroundingY].getValue() == 0 &&
                        !mineTile[surroundingX][surroundingY].getIsOpened()) {
                    replaceToTrue(surroundingX, surroundingY);
                    queue.offer(new Pair<>(surroundingX, surroundingY));
                    //Show the number tile.
                } else if (mineTile[surroundingX][surroundingY].getValue() > 0) {
                    replaceToTrue(surroundingX, surroundingY);
                }
            }
        }
        return queue;
    }
}
