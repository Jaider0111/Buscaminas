package GameLogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClicks implements MouseListener {
    private Board board;
    private Cell celda;

    public MouseClicks(Board board) {
        this.board = board;
    }

    public Cell getCelda() {
        return celda;
    }

    public void setCelda(Cell celda) {
        this.celda = celda;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
            board.click(celda.getRow(), celda.getColumn());
        else
            board.rightClick(celda.getRow(), celda.getColumn());
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
