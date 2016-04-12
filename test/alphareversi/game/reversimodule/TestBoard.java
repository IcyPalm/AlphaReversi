package alphareversi.game.reversimodule;

import static junit.framework.TestCase.assertEquals;

import junit.framework.TestCase;
import org.junit.Test;

public class TestBoard extends TestCase {
    private static final int O = Board.EMPTY;
    private static final int B = Board.SELF;
    private static final int W = Board.OPPONENT;

    private void assertBoard(int[][] a, int[][] b) {
        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < a.length; col++) {
                assertEquals(a[row][col], b[row][col]);
            }
        }
    }

    @Test
    public void testCreateBoard() {
        Board b = new Board();
        assertBoard(b.get(), new int[][] {
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, W, B, O, O, O },
            new int[] { O, O, O, B, W, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
        });
    }

    @Test
    public void testAvailableMoves() {
        Board b = new Board();
        assertEquals(b.getAvailableMoves(W), new int[] {
            2 * 8 + 4,
            3 * 8 + 5,
            4 * 8 + 2,
            5 * 8 + 3,
        });
    }

    @Test
    public void testPlacePiece() {
        Board b = new Board();
        b.place(B, 4 * 8 + 5);
        assertBoard(b.get(), new int[][] {
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, W, B, O, O, O },
            new int[] { O, O, O, B, B, B, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
        });

        b.place(W, 5 * 8 + 5);
        assertBoard(b.get(), new int[][] {
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, W, B, O, O, O },
            new int[] { O, O, O, B, W, B, O, O },
            new int[] { O, O, O, O, O, W, O, O },
            new int[] { O, O, O, O, O, O, O, O },
            new int[] { O, O, O, O, O, O, O, O },
        });
    }

    @Test
    public void testScores() {
        Board b = new Board(new int[][] {
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W },
            new int[] { O, W, B, O, W, B, O, W }
        });

        assertEquals(b.getScore(W), 24);
        assertEquals(b.getScore(B), 16);
    }
}
