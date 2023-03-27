public class Box {
    private final int boxXCord; // 0, 1, 2
    private final int boxYCord; // 0, 1, 2

    public Box( final int xCord, final int yCord ) {
        boxXCord = xCord / 3;
        boxYCord = yCord / 3;
    }

    public boolean isCordInBox( final int tileXCord, final int tileYCord ) {
        if ( tileXCord < boxXCord * 3 ) {
            return false;
        }
        if ( tileXCord >= ( boxXCord + 1 ) * 3 ) {
            return false;
        }
        if ( tileYCord < boxYCord * 3 ) {
            return false;
        }
        if ( tileYCord >= ( boxYCord + 1 ) * 3 ) {
            return false;
        }
        return true;
    }
}
