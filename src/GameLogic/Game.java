package GameLogic;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Game{
    private JFrame game;
    private final Board board;
    private final int rows;
    private final int columns;
    public JLabel flagCount;
    public JLabel timer;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        ImageIcon difficulty = new ImageIcon(getClass().getResource("/assets/difficulty.png"));
        String[] options = {"Facil(10x12)", "Medio(13x20)", "Dificil(15x30)"};
        String dificultad = (String) JOptionPane.showInputDialog(null, "Selecciona la dificultad con la que deseas juagar", "Dificultad",JOptionPane.PLAIN_MESSAGE, difficulty, options, options[0]);
        if(dificultad==null) System.exit(0);
        rows = (dificultad.equals(options[0])) ? 10 : ((dificultad.equals(options[1])) ? 13 : 15);
        columns = (dificultad.equals(options[0])) ? 12 : ((dificultad.equals(options[1])) ? 20 : 30);;
        flagCount = new JLabel("0/"+(rows*columns/5));
        timer = new JLabel("00:00");
        board =  new Board(rows, columns, this);
        initWindow();
    }

    private void initWindow() {
        game = new JFrame("Buscaminas");
        JPanel panel = new JPanel();
        JPanel grid = new JPanel();
        JLabel flag = new JLabel(new ImageIcon(getClass().getResource("/assets/flag_indicator.png")));
        JLabel clock = new JLabel(new ImageIcon(getClass().getResource("/assets/reloj.png")));
        JButton start = new JButton("Reiniciar");
        JButton exit = new JButton("Salir");
        start.addActionListener(actionEvent -> board.restart());
        exit.addActionListener(actionEvent -> game.dispose());
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        grid.setLayout(new GridLayout(rows, columns));
        grid.setPreferredSize(new Dimension(30*columns,30*rows));
        Cell[][] cells = board.getCells();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid.add(cells[i][j].button);
            }
        }
        panel.setLayout(new BorderLayout());
        panel.add(grid, BorderLayout.CENTER);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        flag.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(flag);
        flagCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(flagCount);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        clock.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(clock);
        timer.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(timer);
        rightPanel.add(Box.createVerticalGlue());
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(start);
        rightPanel.add(Box.createRigidArea(new Dimension(140, 30)));
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(exit);
        rightPanel.add(Box.createVerticalGlue());
        panel.add(rightPanel, BorderLayout.LINE_END);
        game.add(panel);
        game.pack();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
