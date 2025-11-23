package maanya.tictactoe;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GuiMain {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Board board = new Board();

      // 👇 ask the user which difficulty they want
      Difficulty difficulty = askDifficultyFromUser();

      Strategy aiStrategy = StrategyFactory.create(difficulty);
      StrategyPlayer aiPlayer = new StrategyPlayer(aiStrategy);

      TicTacToeFrame frame = new TicTacToeFrame();
      GuiController controller = new GuiController(board, aiPlayer, frame);
      frame.setCellClickListener(controller);

      frame.showMessage("Difficulty: " + difficulty + ". Your turn (X). Click a cell.");
      controller.start();
    });
  }

  private static Difficulty askDifficultyFromUser() {
    String[] options = {"Easy", "Hard"};

    int choice = JOptionPane.showOptionDialog(
        null,
        "Choose difficulty:",
        "Tic Tac Toe – Difficulty",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );

    // if user closes dialog or clicks cancel, default to EASY
    if (choice == 1) {
      return Difficulty.HARD;
    } else {
      return Difficulty.EASY;
    }
  }
}
