package com.chess.engine.pieces;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8,16,7,9};
    public Pawn(final Alliance pieceAlliance,
                final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            int candidateCoordinateDestination = this.piecePosition+(this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateCoordinateDestination)){
                continue;
            }
            if (currentCandidateOffset==8 && board.getTile(candidateCoordinateDestination).isTileOccupied()){
                // more work to do
                legalMoves.add(new Move.MajorMove(board,this,candidateCoordinateDestination));
            } else if (currentCandidateOffset==16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {

                final int behindCandidateDestinationCoordinate=this.piecePosition+(this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateCoordinateDestination).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this,candidateCoordinateDestination));

                } else if (currentCandidateOffset==7 &&
                !(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()||
                (BoardUtils.FIRST_COLUMN [this.piecePosition] && this.pieceAlliance.isBlack())))
                {
                    if(board.getTile(candidateCoordinateDestination).isTileOccupied()){
                        final Piece pieceOnCandidate = board.getTile(candidateCoordinateDestination).getPiece();
                        if (this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                            //For Attacking
                            legalMoves.add(new Move.MajorMove(board,this,candidateCoordinateDestination));
                        }
                    }

                } else if (currentCandidateOffset==9 && !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()||
                        (BoardUtils.EIGHTH_COLUMN [this.piecePosition] && this.pieceAlliance.isBlack()))) {
                    if(board.getTile(candidateCoordinateDestination).isTileOccupied()){
                        final Piece pieceOnCandidate = board.getTile(candidateCoordinateDestination).getPiece();
                        if (this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                            //For Attacking
                            legalMoves.add(new Move.MajorMove(board,this,candidateCoordinateDestination));
                        }
                    }
                }


            }

        }
        return ImmutableList.copyOf(legalMoves);
    }
}
