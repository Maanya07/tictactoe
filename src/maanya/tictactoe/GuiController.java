package maanya.tictactoe;

import javax.swing.Timer;

public class GuiController implements CellClickListener {

  private final Board board;
  private final StrategyPlayer aiPlayer;        // plays as O
  private final TicTacToeFrame view;

  private Mark currentTurn = Mark.X;            // human starts

  // timers
  private Timer aiMoveTimer;
  private Timer playerTimer;
  private int playerTimeLeftSeconds;

  // how long the human has for each move
  private static final int TURN_TIME_SECONDS = 10;
  // how long the AI "thinks" before playing (ms)
  private static final int AI_DELAY_MS = 800;

  public GuiController(Board board, StrategyPlayer aiPlayer, TicTacToeFrame view) {
    this.board = board;
    this.aiPlayer = aiPlayer;
    this.view = view;
  }

  public void start() {
    view.showBoard(board);
    startPlayerTurn();   // show message + start countdown
  }

  @Override
  public void onCellClicked(int row, int col) {
    // ignore if game already over
    if (board.hasWinner() || board.isFull()) {
      return;
    }

    // only allow human move when it's X's turn
    if (currentTurn != Mark.X) {
      view.showMessage("Wait for your turn!");
      return;
    }

    // invalid move
    if (!board.isCellEmpty(row, col)) {
      view.showMessage("Cell is not empty. Choose another.");
      return;
    }

    // stop the countdown timer because the human played
    stopPlayerTimer();

    // Human move (X)
    board.placeMark(row, col, Mark.X);
    view.showBoard(board);

    if (checkGameOver()) {
      return;
    }

    // AI's turn, with a delay
    currentTurn = Mark.O;
    view.showMessage("AI (O) is thinking...");
    scheduleAiMove();
  }

  // ---------------------- helpers ------------------------

  /** Start a new human turn with a fresh countdown timer. */
  private void startPlayerTurn() {
    currentTurn = Mark.X;
    playerTimeLeftSeconds = TURN_TIME_SECONDS;

    // ensure board is up to date + message shows time
    view.showBoard(board);
    view.showMessage("Your turn (X). Time left: " + playerTimeLeftSeconds + "s");

    stopPlayerTimer();  // in case there was an old one

    playerTimer = new Timer(1000, e -> {
      playerTimeLeftSeconds--;
      if (playerTimeLeftSeconds > 0) {
        view.showMessage("Your turn (X). Time left: " + playerTimeLeftSeconds + "s");
      } else {
        // time's up
        stopPlayerTimer();
        handlePlayerTimeout();
      }
    });
    playerTimer.start();
  }

  /** Stop the human countdown timer if running. */
  private void stopPlayerTimer() {
    if (playerTimer != null && playerTimer.isRunning()) {
      playerTimer.stop();
    }
  }

  /** Schedule the AI move after a small delay. */
  private void scheduleAiMove() {
    if (aiMoveTimer != null && aiMoveTimer.isRunning()) {
      aiMoveTimer.stop();
    }

    aiMoveTimer = new Timer(AI_DELAY_MS, e -> {
      aiMoveTimer.stop();

      // safety: game may have ended while waiting
      if (board.hasWinner() || board.isFull()) {
        return;
      }

      Move aiMove = aiPlayer.chooseMove(board, Mark.O);
      board.placeMark(aiMove.getRow(), aiMove.getCol(), Mark.O);
      view.showBoard(board);

      if (!checkGameOver()) {
        // hand control back to human with new timer
        startPlayerTurn();
      }
    });
    aiMoveTimer.setRepeats(false);
    aiMoveTimer.start();
  }

  /** Called when the human lets the timer run out. */
  private void handlePlayerTimeout() {
    // if game already over, do nothing
    if (board.hasWinner() || board.isFull()) {
      return;
    }

    // AI wins by timeout
    if (view instanceof TicTacToeFrame frame) {
      frame.highlightWinner(false); // false => AI win (red)
    }
    view.showMessage("Time's up! AI wins (O).");
    view.disableAllButtons();
  }

  // ---------------------------------------------------------
  // existing game-over logic, with colour highlight
  // ---------------------------------------------------------
  private boolean checkGameOver() {
    if (board.hasWinner()) {
      Mark winner = board.getWinner();
      view.showBoard(board);

      boolean playerWon = (winner == Mark.X); // human = X

      if (view instanceof TicTacToeFrame frame) {
        frame.highlightWinner(playerWon);   // green if X, red if O
      }

      if (playerWon) {
        view.showMessage("You win! (X)");
      } else {
        view.showMessage("AI wins! (" + winner + ")");
      }

      view.disableAllButtons();
      stopPlayerTimer();
      return true;

    } else if (board.isFull()) {
      view.showBoard(board);
      view.showMessage("It's a draw!");
      view.disableAllButtons();
      stopPlayerTimer();
      return true;
    }

    return false;
  }
}
