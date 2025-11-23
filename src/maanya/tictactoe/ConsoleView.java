package maanya.tictactoe;

public class ConsoleView implements View {
  @Override
  public void showBoard(Board board) {
    int size = board.getSize();
    System.out.println();
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        System.out.print(" " + board.getMarkAt(r, c).toString());
        if (c < size - 1) {
          System.out.print(" |");
        }
      }
      System.out.println();
      if (r < size - 1) {
        System.out.println("---+---+---");
      }
    }
    System.out.println();
  }

  @Override
  public void showMessage(String message) {
    System.out.println(message);
  }

}
