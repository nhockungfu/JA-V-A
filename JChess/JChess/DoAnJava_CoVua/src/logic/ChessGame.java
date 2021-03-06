package logic;

import GUI.PlayWithComputer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ChessGame implements Runnable{

    public int gameState = GAME_STATE_WHITE;
    public static final int GAME_STATE_WHITE = 0;
    public static final int GAME_STATE_BLACK = 1;
    public static final int GAME_STATE_END_BLACK_WON = 2;
    public static final int GAME_STATE_END_WHITE_WON = 3;

    // 0 = bottom, size = top
    public List<Piece> pieces = new ArrayList<Piece>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();

    private MoveValidator moveValidator;
    private IPlayerHandler blackPlayerHandler;
    private IPlayerHandler whitePlayerHandler;
    private IPlayerHandler activePlayerHandler;

    /**
     * initialize game
     */
    public ChessGame() {

        this.moveValidator = new MoveValidator(this);

        // create and place pieces
        // rook, knight, bishop, queen, king, bishop, knight, and rook
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1,
                        Piece.COLUMN_B);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1,
                        Piece.COLUMN_C);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_1,
                        Piece.COLUMN_D);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1,
                        Piece.COLUMN_F);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1,
                        Piece.COLUMN_G);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);

        // pawns
        int currentColumn = Piece.COLUMN_A;
        for (int i = 0; i < 8; i++) {
                createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2,
                                currentColumn);
                currentColumn++;
        }

        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8,
                        Piece.COLUMN_B);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8,
                        Piece.COLUMN_C);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, Piece.ROW_8,
                        Piece.COLUMN_D);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_8, Piece.COLUMN_E);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8,
                        Piece.COLUMN_F);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8,
                        Piece.COLUMN_G);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);

        // pawns
        currentColumn = Piece.COLUMN_A;
        for (int i = 0; i < 8; i++) {
                createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7,
                                currentColumn);
                currentColumn++;
        }
    }

    public void startGame(){
       if(this.blackPlayerHandler != null && this.whitePlayerHandler != null){
           // bên trắng đi trước
           this.activePlayerHandler = this.whitePlayerHandler;
           // start game flow
           System.out.println("ChessGame: starting game flow");
           while(!isGameEndConditionReached()){
               waitForMove();
               swapActivePlayer();
           }
           
       }    
    }

    public void setPlayer(int pieceColor, IPlayerHandler playerHandler){
        if(pieceColor==Piece.COLOR_BLACK){
            this.blackPlayerHandler = playerHandler;
        }else{
            this.whitePlayerHandler = playerHandler;
        }
    }

    private void swapActivePlayer() {
            if( this.activePlayerHandler == this.whitePlayerHandler ){
                    this.activePlayerHandler = this.blackPlayerHandler;
            }else{
                    this.activePlayerHandler = this.whitePlayerHandler;
            }
            this.changeGameState();
    }

    /**
     * Wait for client/player move and execute it.
     * Notify all clients/players about successful execution of move.
     */
    private void waitForMove() {
        Move move = null;
        // wait for a valid move
        do{
            move = this.activePlayerHandler.getMove();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            if( move != null && this.moveValidator.isMoveValid(move, false) ){
                    break;
            }else if( move != null && !this.moveValidator.isMoveValid(move,true)){
                System.out.println("provided move was invalid: "+move);
                move=null;
                System.exit(0);
            }
        }while(move == null);

        //execute move
        boolean success = this.movePiece(move);
        if(success){
            this.blackPlayerHandler.moveSuccessfullyExecuted(move);
            this.whitePlayerHandler.moveSuccessfullyExecuted(move);
        }else{
            throw new IllegalStateException("move was valid, but failed to execute it");
        }
    }

    public void createAndAddPiece(int color, int type, int row, int column) {
        Piece piece = new Piece(color, type, row, column);
        this.pieces.add(piece);
    }

    /**
     * Move piece to the specified location. If the target location is occupied
     * by an opponent piece, that piece is marked as 'captured'. If the move
     * could not be executed successfully, 'false' is returned and the game
     * state does not change.
     * 
     * @param move to execute
     * @return true, if piece was moved successfully
     */
    public boolean movePiece(Move move) {
        //set captured piece in move
        // this information is needed in the undoMove() method.
        move.capturedPiece = this.getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);

        Piece piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

        // check if the move is capturing an opponent piece
        int opponentColor = (piece.getColor() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE
                        : Piece.COLOR_BLACK);
        if (isNonCapturedPieceAtLocation(opponentColor, move.targetRow, move.targetColumn)) {
                // handle captured piece
                Piece opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
                this.pieces.remove(opponentPiece);
                this.capturedPieces.add(opponentPiece);
                opponentPiece.isCaptured(true);
        }

        // move piece to new position
        piece.setRow(move.targetRow);
        piece.setColumn(move.targetColumn);

        // reset game state
        //if(piece.getColor() == Piece.COLOR_BLACK){
        //	this.gameState = ChessGame.GAME_STATE_BLACK;
        //}else{
        //	this.gameState = ChessGame.GAME_STATE_WHITE;
        //}

        return true;
    }

    /**
     * Undo the specified move. It will also adjust the game state appropriately.
     * @param move
     */
    public void undoMove(Move move){
        Piece piece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);

        piece.setRow(move.sourceRow);
        piece.setColumn(move.sourceColumn);

        if(move.capturedPiece != null){
                move.capturedPiece.setRow(move.targetRow);
                move.capturedPiece.setColumn(move.targetColumn);
                move.capturedPiece.isCaptured(false);
                this.capturedPieces.remove(move.capturedPiece);
                this.pieces.add(move.capturedPiece);
        }

        if(piece.getColor() == Piece.COLOR_BLACK){
                this.gameState = ChessGame.GAME_STATE_BLACK;
        }else{
                this.gameState = ChessGame.GAME_STATE_WHITE;
        }
    }

    /**
     * check if the games end condition is met: One color has a captured king
     * 
     * @return true if the game end condition is met
     */
    private boolean isGameEndConditionReached() {
        for (Piece piece : this.capturedPieces) {
            if (piece.getType() == Piece.TYPE_KING ) {// nếu là King thì true=kết thúc
                    return true;
            } else {
                    // continue iterating
            }
        }

        return false;
    }



    /**
     * Checks whether there is a piece at the specified location that is not
     * marked as 'captured' and has the specified color.
     * 
     * @param color one of Piece.COLOR_..
     * @param row one of Piece.ROW_..
     * @param column on of Piece.COLUMN_..
     * @return true, if the location contains a not-captured piece of the
     *         specified color
     */
    boolean isNonCapturedPieceAtLocation(int color, int row, int column) {
            for (Piece piece : this.pieces) {
                    if (piece.getRow() == row && piece.getColumn() == column
                                    && piece.getColor() == color) {
                            return true;
                    }
            }
            return false;
    }

    //Hàm kiểm tra Piece có bị bắt tại vị trí row, column không
    boolean isNonCapturedPieceAtLocation(int row, int column) {
        for (Piece piece : this.pieces) {
                if (piece.getRow() == row && piece.getColumn() == column) {
                        return true;
                }
        }
        return false;
    }
        /**
     * returns the first piece at the specified location that is not marked as
     * 'captured'.
     * 
     * @param row one of Piece.ROW_..
     * @param column one of Piece.COLUMN_..
     * @return the first not captured piece at the specified location
     */
    public Piece getNonCapturedPieceAtLocation(int row, int column) {
        for (Piece piece : this.pieces) {
            if (piece.getRow() == row && piece.getColumn() == column) {
                    return piece;
            }
        }
        return null;
    }
    
    public void changeGameState() {
        if (this.isGameEndConditionReached()) {

            if (this.gameState == ChessGame.GAME_STATE_BLACK) {
                    this.gameState = ChessGame.GAME_STATE_END_BLACK_WON;
            } else if(this.gameState == ChessGame.GAME_STATE_WHITE){
                    this.gameState = ChessGame.GAME_STATE_END_WHITE_WON;
            }
            return;
        }

        switch (this.gameState) {
            case GAME_STATE_BLACK:
                this.gameState = GAME_STATE_WHITE;
                break;
            case GAME_STATE_WHITE:
                this.gameState = GAME_STATE_BLACK;
                break;
            case GAME_STATE_END_WHITE_WON:
            case GAME_STATE_END_BLACK_WON:// don't change anymore
                    break;
            default:
                    throw new IllegalStateException("unknown game state:" + this.gameState);
        }
    }
    public int getGameState() {
            return this.gameState;
    }
    public MoveValidator getMoveValidator(){
        return this.moveValidator;
    }
    public List<Piece> getPieces() {
        return this.pieces;
    }
    @Override
    public void run() {
        this.startGame();
    }
}
