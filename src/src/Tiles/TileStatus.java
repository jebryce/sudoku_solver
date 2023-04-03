package Tiles;

public enum TileStatus {
    UNSET,          // empty tile
    SET,            // user-entered number in tile
    SET_FINAL,      // preset number that can't be changed
    DUPLICATE       // user-entered number that is already in row/col/box
}
