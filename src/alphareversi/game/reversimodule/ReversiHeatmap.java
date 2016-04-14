package alphareversi.game.reversimodule;

public class ReversiHeatmap {

    /*
    private int[] heatMapOLd   = { 100,    2, 5, 4, 4, 5, 2,   100,
                                2,      1, 3, 3, 3, 3, 1,   2,
                                5,      3, 6, 5, 5, 6, 3,   5,
                                4,      3, 5, 6, 6, 5, 3,   4,
                                4,      3, 5, 6, 6, 5, 3,   4,
                                5,      3, 6, 5, 5, 6, 3,   5,
                                2,      1, 3, 3, 3, 3, 1,   2,
                                100,    2, 5, 4, 4, 5, 2,   100  };

    */

    private int[] heatMap = {
            700, -20, 20, 5, 5, 20, -20, 700,
            -20, -40, -5, -5, -5, -5, -40, -20,
            20, -5, 15, 3, 3, 15, -5, 20,
            5, -5, 3, 3, 3, 3, -5, 5,
            5, -5, 3, 3, 3, 3, -5, 5,
            20, -5, 15, 3, 3, 15, -5, 20,
            -20, -40, -5, -5, -5, -5, -40, -20,
            700, -20, 20, 5, 5, 20, -20, 700,
    };


    public ReversiHeatmap() {

    }

    public int getHeat(int index) {
        return heatMap[index];
    }

}
