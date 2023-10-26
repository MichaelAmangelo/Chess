package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;


import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
     protected final int tileCoordinate;
     private static final Map<Integer,EmptyTile> EMPTY_TILE_Cache = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i=0;BoardUtils.NUM_TILES>i;i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
    public static Tile createTile(final int tileCoordinate,final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate,piece): EMPTY_TILE_Cache.get(tileCoordinate);
    }


     private Tile( final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(final int coordinate) {
            super(coordinate);
        }
        @Override
        public boolean isTileOccupied() {
            return false;
        }
        @Override
        public Piece getPiece(){
            return null;
        }

    }
    public static final class OccupiedTile extends Tile {
       private final Piece pieceOnTile;

        public OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }


    }

}
