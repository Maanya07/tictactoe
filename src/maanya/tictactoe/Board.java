package maanya.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
  public final int size = 3; //fixed because tictactoe
  public final Mark[][] grid; //2D array of enum Marks

  public Board() {
    this.grid = new Mark[size][size]; //allocate 3x3 size
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        grid[r][c] = Mark.EMPTY; //fill everything with Mark.Empty (EMPTY)
      }
    }
  }

  public int getSize() { return size; } //incase I want to change the size later?

  /**Returns the current mark at (row, col)
   * Used for:
   * - printing the board
   * -getting wins
   * -strategies AI.*/
  public Mark getMarkAt(int row, int col) {
    checkBounds(row, col);
    return grid[row][col];
  }
  public boolean isCellEmpty(int row, int col) {
    checkBounds(row, col);
    return grid[row][col] == Mark.EMPTY;
  }

  public void placeMark(int row, int col, Mark mark) {
    checkBounds(row, col);
    Objects.requireNonNull(mark);
    if (!isCellEmpty(row, col)) throw new IllegalArgumentException("Cell not empty");
    if (mark == Mark.EMPTY) throw new IllegalArgumentException("Cannot place EMPTY");
    grid[row][col] = mark;
  }

  /**Returns a list of all empty cells where a player could legally move.*/
  public List<Move> getAvailableMoves() {
    List<Move> moves = new ArrayList<>();
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (grid[r][c] == Mark.EMPTY) {
          moves.add(new Move(r, c));
        }
      }
    }
    return moves;
  }
  public boolean isFull() {
    return getAvailableMoves().isEmpty();
  }

  //win condition logic

  public boolean hasWinner() {
    return getWinner() != Mark.EMPTY;
  }

  public Mark getWinner() {
    // Check rows
    for (int r = 0; r < size; r++) {
      Mark rowWinner = checkRow(r);
      if (rowWinner != Mark.EMPTY) return rowWinner;
    }

    // Check columns
    for (int c = 0; c < size; c++) {
      Mark colWinner = checkCol(c);
      if (colWinner != Mark.EMPTY) return colWinner;
    }

    // Check diagonals
    return checkDiagonals();
  }

  private Mark checkRow(int r) {
    Mark first = grid[r][0];
    if (first == Mark.EMPTY) return Mark.EMPTY;
    for (int c = 1; c < size; c++) {
      if (grid[r][c] != first) return Mark.EMPTY;
    }
    return first;
  }

  private Mark checkCol(int c) {
    Mark first = grid[0][c];
    if (first == Mark.EMPTY) return Mark.EMPTY;
    for (int r = 1; r < size; r++) {
      if (grid[r][c] != first) return Mark.EMPTY;
    }
    return first;
  }

  private Mark checkDiagonals() {
    // Major diagonal (0,0) -> (1,1) -> (2,2)
    Mark first = grid[0][0];
    if (first != Mark.EMPTY &&
        grid[1][1] == first &&
        grid[2][2] == first) {
      return first;
    }

    // Minor diagonal (0,2) -> (1,1) -> (2,0)
    first = grid[0][2];
    if (first != Mark.EMPTY &&
        grid[1][1] == first &&
        grid[2][0] == first) {
      return first;
    }

    return Mark.EMPTY;
  }


  /** No negative indexes or indexes outside 3x3 grid*/
  private void checkBounds(int row, int col) {
    if (row < 0 || row >= size || col < 0 || col >= size) {
      throw new IllegalArgumentException("Row/col out of bounds");
    }




  }



}
