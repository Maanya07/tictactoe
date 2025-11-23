package maanya.tictactoe;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class TicTacToeFrame extends JFrame implements View {

  private final JButton[][] buttons = new JButton[3][3];
  private final JLabel statusLabel = new JLabel("Welcome to Tic Tac Toe!");
  private CellClickListener listener;

  // 🌸 Pink theme colours
  private static final Color BOARD_PINK = new Color(255, 240, 245);      // light blush pink
  private static final Color TILE_PINK = new Color(255, 228, 235);       // soft tile pink
  private static final Color X_PINK = new Color(230, 60, 120);           // hot rose
  private static final Color O_PINK = new Color(200, 0, 150);            // deep magenta/purple pink

  public TicTacToeFrame() {
    super("Tic Tac Toe (GUI)");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    getContentPane().setBackground(BOARD_PINK);

    // Center grid panel
    JPanel gridPanel = new JPanel(new GridLayout(3, 3));
    gridPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
    gridPanel.setBackground(BOARD_PINK);

    Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);

    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        JButton btn = new JButton("");
        btn.setFont(buttonFont);
        btn.setFocusPainted(false);
        btn.setBackground(TILE_PINK);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(240, 200, 210), 3));

        final int row = r;
        final int col = c;
        btn.addActionListener(e -> {
          if (listener != null) {
            listener.onCellClicked(row, col);
          }
        });

        buttons[r][c] = btn;
        gridPanel.add(btn);
      }
    }

    add(gridPanel, BorderLayout.CENTER);

    // Status bar
    statusLabel.setHorizontalAlignment(JLabel.CENTER);
    statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    statusLabel.setOpaque(true);
    statusLabel.setBackground(Color.WHITE);
    statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    add(statusLabel, BorderLayout.SOUTH);

    setResizable(true);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void setCellClickListener(CellClickListener listener) {
    this.listener = listener;
  }

  @Override
  public void showBoard(Board board) {
    SwingUtilities.invokeLater(() -> {
      int size = board.getSize();
      for (int r = 0; r < size; r++) {
        for (int c = 0; c < size; c++) {
          Mark mark = board.getMarkAt(r, c);

          // reset to base pink every render (clears old win colours)
          buttons[r][c].setBackground(TILE_PINK);

          if (mark == Mark.EMPTY) {
            buttons[r][c].setText("");
            buttons[r][c].setForeground(Color.BLACK);
          } else if (mark == Mark.X) {
            buttons[r][c].setText("X");
            buttons[r][c].setForeground(X_PINK);
          } else {
            buttons[r][c].setText("O");
            buttons[r][c].setForeground(O_PINK);
          }

          buttons[r][c].setEnabled(board.isCellEmpty(r, c));
        }
      }
    });
  }

  @Override
  public void showMessage(String message) {
    SwingUtilities.invokeLater(() -> statusLabel.setText(message));
  }

  public void disableAllButtons() {
    SwingUtilities.invokeLater(() -> {
      for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
          buttons[r][c].setEnabled(false);
        }
      }
    });
  }

  // 🌟 highlight winner
  public void highlightWinner(boolean playerWon) {
    SwingUtilities.invokeLater(() -> {
      Color winColor = playerWon ? new Color(200, 255, 200) : new Color(255, 200, 200);

      for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
          buttons[r][c].setBackground(winColor);
          buttons[r][c].setOpaque(true);
        }
      }
    });
  }


}
