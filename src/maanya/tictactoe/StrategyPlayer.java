package maanya.tictactoe;

import java.util.Objects;

public class StrategyPlayer implements Player {

  private final Strategy strategy;

  public StrategyPlayer(Strategy strategy) {
    this.strategy = Objects.requireNonNull(strategy);
  }

  @Override
  public Move chooseMove(Board board, Mark mark) {
    return strategy.chooseMove(board, mark);
  }
}
