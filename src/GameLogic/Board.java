package GameLogic;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class Board{
    private Set<AbstractMap.SimpleEntry<Integer,Integer>> pos;
    private Cell[][] cells;
    private int rows;
    private int columns;
    private int bombs;
    private int discovered;
    private boolean end;
    private final Game game;
    private boolean init;
    public Timer timer;
    private int minutes;
    private int seconds;

    public Board(int rows, int columns, Game game) {
        this.pos = new HashSet<>();
        this.rows = rows;
        this.columns = columns;
        this.game = game;
        initCells();
        makeBombs();
        this.end = false;
        this.init = false;
        minutes = 0;
        seconds = 0;
        this.timer = new Timer(1000, actionEvent -> {
            seconds++;
            if(seconds == 60){
                seconds = 0;
                minutes++;
            }
            String time = ((minutes>9)?"":"0") + minutes + ":" +((seconds>9)?"":"0") + seconds;
            game.timer.setText(time);
        });
    }

    public void restart() {
        this.pos = new HashSet<>();
        reInitCells();
        makeBombs();
        game.flagCount.setText("0/"+(rows*columns/5));
        timer.restart();
        minutes = 0;
        seconds = 0;
        game.timer.setText("00:00");
        this.end = false;
        this.init = false;
    }

    private void reInitCells() {
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                cells[i][j].restart();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void click(int row, int column){
        if(end) return;
        if(!init){
            init = true;
            startClock();
        }
        if(cells[row][column].isMine() && cells[row][column].isState()) return;
        if (cells[row][column].isMine()) gameOver();
        else {
            makeMovement(row, column);
            verifyWin();
        }
    }

    private void startClock() {
        timer.start();
    }

    private void verifyWin() {
        if (pos.size()+discovered < columns*rows) return;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(cells[i][j].isMine()) cells[i][j].makeMark();
                else cells[i][j].show();
            }
        }
        end = true;
        timer.stop();
        ImageIcon happy = new ImageIcon(getClass().getResource("/assets/win.png"));
        JOptionPane.showMessageDialog(null, "Felecitaciones has ganado", "Ganaste", JOptionPane.PLAIN_MESSAGE, happy);
    }

    public void rightClick(int row, int column){
        if(end) return;
        Cell cell = cells[row][column];
        if(cell.isState()) {
            cell.makeEmpty();
            cell.setState(false);
            bombs--;
        }
        else if(!cell.isState() && bombs<pos.size()) {
            cell.makeMark();
            cell.setState(true);
            bombs++;
        }
        game.flagCount.setText((bombs)+"/"+(rows*columns/5));
    }

    private void gameOver(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j].gameOver();
            }
        }
        end = true;
        timer.stop();
        ImageIcon sad = new ImageIcon(getClass().getResource("/assets/sad.png"));
        JOptionPane.showMessageDialog(null, "Que mala suerte has perdido", "Perdiste", JOptionPane.PLAIN_MESSAGE, sad);
    }

    public void makeMovement(int row, int column) {
        Cell cell = cells[row][column];
        if (cell.isState()) return;
        else if (cell.isMine()) return;
        cell.setState(true);
        cell.show();
        discovered++;
        if(!cell.isMine() && cell.getNumber() == 0) {
            for (int i = row-1; i < row+2; i++) {
                for (int j = column-1; j < column + 2; j++) {
                    if(!(i == row && j == column) && i >= 0 && j >= 0 && i < rows && j < columns)
                        makeMovement(i, j);
                }
            }
            cell.off();
        }
    }

    private void makeBombs() {
        Random r = new Random();
        while(pos.size() < (rows*columns)/5)
            pos.add(new AbstractMap.SimpleEntry<>(r.nextInt(rows), r.nextInt(columns)));
        for (AbstractMap.SimpleEntry<Integer, Integer> i : pos) {
            cells[i.getKey()][i.getValue()].setMine(true);
        }
        this.bombs = 0;
        this.discovered  = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(cells[i][j].isMine()) continue;
                int count = 0;
                for (int k = i-1; k < i+2; k++){
                    for (int l = j-1; l < j+2; l++){
                        if(!(i == k && j == l) && k >= 0 && l >= 0 && k < rows && l < columns)
                            if(cells[k][l].isMine())
                                count++;
                    }
                }
                cells[i][j].setNumber(count);
            }
        }
    }

    private void initCells(){
        cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(i, j, new MouseClicks(this));
            }
        }
    }
}
