package GameLogic;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Cell {
    private final ImageIcon mina = new ImageIcon(getClass().getResource("/assets/mina.png"));
    private final ImageIcon flag = new ImageIcon(getClass().getResource("/assets/flag_game.png"));
    private int number;
    private int row;
    private int column;
    private boolean state;
    private boolean isMine;
    public JButton button;

    public Cell(int row, int column, MouseClicks listener){
        this.row = row;
        this.column = column;
        this.state = false;
        this.isMine = false;
        this.button = new JButton();
        button.setBorder(new LineBorder(Color.GRAY, 1));
        button.setFont(new Font("Calibri", Font.BOLD, 15));
        listener.setCelda(this);
        this.button.addMouseListener(listener);
        this.button.setEnabled(true);
        this.button.setVisible(true);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void makeEmpty() {
        this.button.setIcon(null);
    }

    public void makeMark() {
        this.button.setIcon(flag);
    }

    public void off() {
        button.setEnabled(false);
    }

    public void gameOver() {
        this.off();
        if(isMine) button.setIcon(mina);
    }

    public void show() {
        if(isMine())
            button.setIcon(mina);
        else if(number > 0)
            button.setText(String.valueOf(number));
        off();
        button.setBackground(Color.LIGHT_GRAY);
    }

    public void restart() {
        this.state = false;
        this.isMine = false;
        this.button.setEnabled(true);
        this.button.setVisible(true);
        button.setText("");
        button.setIcon(null);
        button.setBackground(null);
    }
}