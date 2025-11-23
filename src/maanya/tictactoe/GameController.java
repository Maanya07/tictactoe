package maanya.tictactoe;

import java.util.Objects;

public class GameController {

  private final Board board;
  private final Player xPlayer;
  private final Player oPlayer;
  private final View view;

  public GameController(Board board, Player xPlayer, Player oPlayer, View view) {
    this.board = Objects.requireNonNull(board);
    this.xPlayer = Objects.requireNonNull(xPlayer);
    this.oPlayer = Objects.requireNonNull(oPlayer);
    this.view = Objects.requireNonNull(view);
  }

  public void play() {
    Mark current = Mark.X;

    while (!board.hasWinner() && !board.isFull()) {
      view.showBoard(board);
      view.showMessage("Player " + current + "'s turn.");

      Player currentPlayer = (current == Mark.X) ? xPlayer : oPlayer;
      Move move = currentPlayer.chooseMove(board, current);
      board.placeMark(move.getRow(), move.getCol(), current);

      // swap turn
      current = (current == Mark.X) ? Mark.O : Mark.X;
    }

    view.showBoard(board);

    if (board.hasWinner()) {
      view.showMessage("Player " + board.getWinner() + " wins!");
    } else {
      view.showMessage("It's a draw!");
    }
  }
}

