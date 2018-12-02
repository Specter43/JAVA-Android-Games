package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SudokuTest {

    /**
     * The Sudoku for test.
     */
    private Sudoku sudoku;

    /**
     * Create a initial list of Tiles for test.
     *
     * @return list of Tiles.
     */
    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 81;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(0));
            } else {
                tiles.add(new Tile(tileNum + 1));
            }
        }
        return tiles;
    }

    /**
     * Set up a sudoku for tests
     */
    @Before
    public void setUp(){
        sudoku = new Sudoku(createTiles());
    }

    /**
     * Tear down after test
     */
    @After
    public void tearDown(){
        sudoku = null;
    }

    /**
     * Test iterator method
     */
    @Test
    public void iterator(){
        assertTrue(sudoku.iterator().hasNext());
        assertEquals(1, sudoku.iterator().next().getId());
    }

    /**
     * Test numTiles method
     */
    @Test
    public void numTiles() {
        assertEquals(81, sudoku.numTiles());
    }

    /**
     * Test getSize method
     */
    @Test
    public void getSize() {
        assertEquals(9, sudoku.getSize());
    }

    /**
     * Test getTile method
     */
    @Test
    public void getTile() {
        assertEquals(11, sudoku.getTile(1,1).getId());
    }

    /**
     * Test writeNum method
     */
    @Test
    public void writeNum() {
        sudoku.writeNum(1, 1, 1);
        assertEquals(1, sudoku.getTile(1,1).getId());
        sudoku.writeNum(8, 8, 7);
        assertEquals(7, sudoku.getTile(8,8).getId());

    }

    /**
     * Test toString method
     */
    @Test
    public void testtoString() {
        assertEquals("Sudoku{" +
                "tiles=" + Arrays.toString(sudoku.getTileList()) +
                '}', sudoku.toString());
    }
}