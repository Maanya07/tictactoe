package maanya.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmartStrategy implements Strategy {

  private final Random rand = new Random();

  @Override
  public Move chooseMove(Board board, Mark me) {
    int size = board.getSize();
    Mark opponent = (me == Mark.X) ? Mark.O : Mark.X;

    // 1. Try to win this turn
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (board.isCellEmpty(r, c) && isWinningMove(board, r, c, me)) {
          return new Move(r, c);
        }
      }
    }

    // 2. Block opponent's winning move
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (board.isCellEmpty(r, c) && isWinningMove(board, r, c, opponent)) {
          return new Move(r, c);
        }
      }
    }

    // 3. Take centre if free
    int centre = size / 2; // for 3x3, this is 1
    if (board.isCellEmpty(centre, centre)) {
      return new Move(centre, centre);
    }

    // 4. Take a corner if free
    int[][] corners = { {0, 0}, {0, size - 1}, {size - 1, 0}, {size - 1, size - 1} };
    List<Move> freeCorners = new ArrayList<>();
    for (int[] corner : corners) {
      int r = corner[0];
      int c = corner[1];
      if (board.isCellEmpty(r, c)) {
        freeCorners.add(new Move(r, c));
      }
    }
    if (!freeCorners.isEmpty()) {
      return freeCorners.get(rand.nextInt(freeCorners.size()));
    }

    // 5. Fallback: any random empty cell
    List<Move> emptyCells = new ArrayList<>();
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (board.isCellEmpty(r, c)) {
          emptyCells.add(new Move(r, c));
        }
      }
    }

    // Shouldn't happen if game-over is checked correctly
    if (emptyCells.isEmpty()) {
      return new Move(0, 0);
    }

    return emptyCells.get(rand.nextInt(emptyCells.size()));
  }

  /**
   * Checks if placing `mark` at (row, col) would immediately create a 3-in-a-row.
   * We don't mutate the board; we just "pretend" that cell is mark while
   * checking lines.
   */
  private boolean isWinningMove(Board board, int row, int col, Mark mark) {
    int size = board.getSize();

    // 1. Check row
    boolean rowWin = true;
    for (int c = 0; c < size; c++) {
      Mark m = (c == col) ? mark : board.getMarkAt(row, c);
      if (m != mark) {
        rowWin = false;
        break;
      }
    }
    if (rowWin) return true;

    // 2. Check column
    boolean colWin = true;
    for (int r = 0; r < size; r++) {
      Mark m = (r == row) ? mark : board.getMarkAt(r, col);
      if (m != mark) {
        colWin = false;
        break;
      }
    }
    if (colWin) return true;

    // 3. Main diagonal
    if (row == col) {
      boolean diagWin = true;
      for (int i = 0; i < size; i++) {
        Mark m = (i == row) ? mark : board.getMarkAt(i, i);
        if (m != mark) {
          diagWin = false;
          break;
        }
      }
      if (diagWin) return true;
    }

    // 4. Anti-diagonal
    if (row + col == size - 1) {
      boolean antiWin = true;
      for (int i = 0; i < size; i++) {
        int r = i;
        int c = size - 1 - i;
        Mark m = (r == row && c == col) ? mark : board.getMarkAt(r, c);
        if (m != mark) {
          antiWin = false;
          break;
        }
      }
      if (antiWin) return true;
    }

    return false;
  }
}
