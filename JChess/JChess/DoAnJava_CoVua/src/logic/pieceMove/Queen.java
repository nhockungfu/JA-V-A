/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.pieceMove;

/**
 *
 * @author PhucRed
 */
public class Queen {
    private boolean isValidQueenMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
            // The queen combines the power of the rook and bishop and can move any number
            // of squares along rank, file, or diagonal, but it may not leap over other pieces.
            //
            boolean result = isValidBishopMove(sourceRow, sourceColumn, targetRow, targetColumn);
            result |= isValidRookMove(sourceRow, sourceColumn, targetRow, targetColumn);
            return result;
	}
}
