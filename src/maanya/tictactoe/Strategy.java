package maanya.tictactoe;


public interface Strategy {
  /**
   * Choose a move for the given mark on the given board.
   * Implementations must assume the board is the current game state.
   */
  Move chooseMove(Board board, Mark mark);
}
