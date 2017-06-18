package GUI;

import java.awt.event.*;
import java.util.List;

import logic.*;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {

    private List<GuiPiece> guiPieces;
    private PlayWithComputer chessGui;

    private int dragOffsetX;
    private int dragOffsetY;


    public PiecesDragAndDropListener(List<GuiPiece> guiPieces, PlayWithComputer chessGui) {
            this.guiPieces = guiPieces;
            this.chessGui = chessGui;
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if( !this.chessGui.isDraggingGamePiecesEnabled()){
                return;
        }
        int x = evt.getPoint().x;
        int y = evt.getPoint().y;

        for (int i = this.guiPieces.size()-1; i >= 0; i--) {
            GuiPiece guiPiece = this.guiPieces.get(i);
            if(mouseOverPiece(guiPiece,x,y)){
                if( (this.chessGui.getGameState() == ChessGame.GAME_STATE_WHITE&& guiPiece.getColor() == Piece.COLOR_WHITE) ||
                    (this.chessGui.getGameState() == ChessGame.GAME_STATE_BLACK&& guiPiece.getColor() == Piece.COLOR_BLACK)){
                    this.dragOffsetX = x - guiPiece.getX();
                    this.dragOffsetY = y - guiPiece.getY();
                    this.chessGui.setDragPiece(guiPiece);
                    this.chessGui.repaint();
                    break;
                }
            }
        }
    }

    //Kieem tra co phai dang nhan vo hinh nay hay k
    private boolean mouseOverPiece(GuiPiece guiPiece, int x, int y) {

            return guiPiece.getX() <= x 
                && guiPiece.getX()+guiPiece.getWidth() >= x
                && guiPiece.getY() <= y
                && guiPiece.getY()+guiPiece.getHeight() >= y;
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if( this.chessGui.getDragPiece() != null){
            int x = evt.getPoint().x - this.dragOffsetX;
            int y = evt.getPoint().y - this.dragOffsetY;

            // set game piece to the new location if possible
            //
            chessGui.setNewPieceLocation(this.chessGui.getDragPiece(), x, y);
            this.chessGui.repaint();
            this.chessGui.setDragPiece(null);
        }
    }

    //khi keo chuot
    @Override
    public void mouseDragged(MouseEvent evt) {
        if(this.chessGui.getDragPiece() != null){
            int x = evt.getPoint().x - this.dragOffsetX;
            int y = evt.getPoint().y - this.dragOffsetY;

            //tien hanh set lai x,y cho hinh anh
            GuiPiece dragPiece = this.chessGui.getDragPiece();
            dragPiece.setX(x);
            dragPiece.setY(y);

            this.chessGui.repaint();
        }	
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {}

    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}
}
