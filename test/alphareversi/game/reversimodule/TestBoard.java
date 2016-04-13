package alphareversi.game.reversimodule;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Collection;

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
        Collection<Integer> available = b.getAvailableMoves(W);
        int[] expected = new int[] {
            2 * 8 + 4,
            3 * 8 + 5,
            4 * 8 + 2,
            5 * 8 + 3,
        };

        assertEquals(available.size(), expected.length);
        for (int move : expected) {
            assertTrue(available.contains(move));
        }
    }

    @Test
    public void testPlacePiece() throws InvalidMoveException {
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
