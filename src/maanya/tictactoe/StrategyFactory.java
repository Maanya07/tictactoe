package maanya.tictactoe;

public final class StrategyFactory {

  private StrategyFactory() {
    // utility class, no instances
  }

  public static Strategy create(Difficulty difficulty) {
    return switch (difficulty) {
      case EASY -> new RandomStrategy();  // dumb, easy to beat
      case HARD -> new SmartStrategy();   // blocks and tries to win
    };
  }
}
