package maanya.tictactoe;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Board board = new Board();
    View view = new ConsoleView();
    Scanner scanner = new Scanner(System.in);

    Player xPlayer = new HumanPlayer(scanner);              // Human as X
    Player oPlayer = new StrategyPlayer(new RandomStrategy()); // AI as O

    GameController controller = new GameController(board, xPlayer, oPlayer, view);
    controller.play();
  }
}