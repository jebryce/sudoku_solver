public class Tile {
    private int        value;
    private TileStatus tileStatus = TileStatus.UNSET;

    public boolean setFinalValue( final int finalValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        value = finalValue;
        tileStatus = TileStatus.SET_FINAL;
        return true;
    }

    public boolean setValue( final int newValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        value = newValue;
        tileStatus = TileStatus.SET;
        return true;
    }

    public boolean clearValue() {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        tileStatus = TileStatus.UNSET;
        return true;
    }

    public int getValue() {
        if ( tileStatus != TileStatus.UNSET ) {
            return value;
        }
        return -1;
    }

}
