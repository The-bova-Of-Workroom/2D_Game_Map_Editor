package tile;


public class tile {
    public static tStructure tsRoad, tsTile, ts;
    public static int ch;
    public static int[][] m;


    public tile() {
        tsRoad = new tStructure(32, 32, 16, 6, "/ROAD_32x32-16x6.png");
        tsTile = new tStructure(16, 16, 16, 6, "/TILE_16x16-16x6.png");
        ts = tsTile;
    }
}
