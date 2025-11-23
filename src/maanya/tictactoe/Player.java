package maanya.tictactoe;

public interface Player {
  /**
   * Ask this player to choose a move for the given mark on the given board.
   */
  Move chooseMove(Board board, Mark mark);
}
