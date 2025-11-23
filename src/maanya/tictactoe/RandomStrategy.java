package maanya.tictactoe;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy {
  private final Random rand = new Random();

  @Override
  public Move chooseMove(Board board, Mark mark) {
    List<Move> moves = board.getAvailableMoves();
    if (moves.isEmpty()) {
      throw new IllegalStateException("No moves available");
    }
    int index = rand.nextInt(moves.size());
    return moves.get(index);}
}
