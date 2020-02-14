package tile;


public class tile {
    public static tStructure tsRoad, tsTile, ts;
    public static int ch;
    public static int[][] m;


    public tile() {
        tsRoad = new tStructure(32, 32, 16, 6, "/ROAD_32x32-16x6.png");
        tsTile = new tStructure(8, 8, 16, 6, "/TILE_8x8-16x6.png");
        ts = tsTile;
    }
}
