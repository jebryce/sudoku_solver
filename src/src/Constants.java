public class Constants {
    public static final int  UPDATES_PER_SECOND = 60;
    public static final int  TILE_SIZE          = 64;
    public static final int  NUM_TILES          = 9;
    public static final int  SCREEN_HEIGHT      = TILE_SIZE * NUM_TILES;
    public static final int  SCREEN_WIDTH       = TILE_SIZE * NUM_TILES;
    public static final long NANO_SEC_PER_SEC   = 1_000_000_000L;
    public static final int  CELL_BORDER_SIZE   = 1;
    public static final int  BOX_BORDER_SIZE    = 3;
    public static final int  TEXT_SIZE          = TILE_SIZE / 2;
    public static final int  TILE_TEXT_X_OFFSET = TEXT_SIZE / 2;
    public static final int  TILE_TEXT_Y_OFFSET = TILE_SIZE - TILE_TEXT_X_OFFSET;
}
