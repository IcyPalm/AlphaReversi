package alphareversi.game.reversimodule;

public class ReversiHeatmap {

    
    private int[] heatMap   = { 100, 2, 5, 4, 4, 5, 2, 100,
                                2, 1, 3, 3, 3, 3, 1, 2,
                                5, 3, 6, 5, 5, 6, 3, 5,
                                4, 3, 5, 6, 6, 5, 3, 4,
                                4, 3, 5, 6, 6, 5, 3, 4,
                                5, 3, 6, 5, 5, 6, 3, 5,
                                2, 1, 3, 3, 3, 3, 1, 2,
                                100, 2, 5, 4, 4, 5, 2, 100  };
    
    public ReversiHeatmap() {
        
    }
    
    public int getHeat(int i) {
        return heatMap[i];
    }
    
}
