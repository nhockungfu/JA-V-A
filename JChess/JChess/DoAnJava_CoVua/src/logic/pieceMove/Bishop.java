/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.pieceMove;

import logic.Piece;

/**
 *
 * @author PhucRed
 */
public class Bishop {
    public static boolean isValidMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        //The bishop can move any number of squares diagonally, but may not leap
        //over other pieces.

        // target location possible?
        if( isTargetLocationFree() || isTargetLocationCaptureable()){
                //ok
        }else{
            //target location not free and not captureable
            return false;
        }

        boolean isValid = false;
        // first lets check if the path to the target is diagonally at all
        int diffRow = targetRow - sourceRow;
        int diffColumn = targetColumn - sourceColumn;

        if( diffRow==diffColumn && diffColumn > 0){
                // moving diagonally up-right
                isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,+1);

        }else if( diffRow==-diffColumn && diffColumn > 0){
                // moving diagnoally down-right
                isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,+1);

        }else if( diffRow==diffColumn && diffColumn < 0){
                // moving diagnoally down-left
                isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,-1);

        }else if( diffRow==-diffColumn && diffColumn < 0){
                // moving diagnoally up-left
                isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,-1);

        }else{
                // not moving diagonally
                isValid = false;
        }
        return isValid;
    }
    private boolean isTargetLocationFree() {
        return targetPiece == null;
    }
    private boolean isTargetLocationCaptureable() {
        if( targetPiece == null ){
            return false;
        }else if( targetPiece.getColor() != sourcePiece.getColor()){
            return true;
        }else{
            return false;
        }
    }
    	private boolean arePiecesBetweenSourceAndTarget(int sourceRow, int sourceColumn,
			int targetRow, int targetColumn, int rowIncrementPerStep, int columnIncrementPerStep) {
		
		int currentRow = sourceRow + rowIncrementPerStep;
		int currentColumn = sourceColumn + columnIncrementPerStep;
		while(true){
			if(currentRow == targetRow && currentColumn == targetColumn){
				break;
			}
			if( currentRow < Piece.ROW_1 || currentRow > Piece.ROW_8
				|| currentColumn < Piece.COLUMN_A || currentColumn > Piece.COLUMN_H){
				break;
			}

			if( this.chessGame.isNonCapturedPieceAtLocation(currentRow, currentColumn)){
				// pieces in between source and target
				return true;
			}

			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}
}
