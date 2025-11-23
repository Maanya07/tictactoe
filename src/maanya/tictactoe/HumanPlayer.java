package maanya.tictactoe;

import java.util.Scanner;

public class HumanPlayer implements Player {

  private final Scanner in;

  public HumanPlayer(Scanner in) {
    this.in = in;
  }

  @Override
  public Move chooseMove(Board board, Mark mark) {
    while (true) {
      System.out.println("Player " + mark + ", enter your move as 'row col' (0-2): ");
      String line = in.nextLine().trim();
      String[] parts = line.split("\\s+");
      if (parts.length != 2) {
        System.out.println("Please enter exactly two numbers.");
        continue;
      }
      try {
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize()) {
          System.out.println("Row/col out of bounds. Try again.");
          continue;
        }
        if (!board.isCellEmpty(row, col)) {
          System.out.println("Cell is not empty. Try again.");
          continue;
        }
        return new Move(row, col);
      } catch (NumberFormatException e) {
        System.out.println("Not a number. Try again.");
      }
    }
  }
}
