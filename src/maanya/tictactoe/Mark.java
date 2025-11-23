package maanya.tictactoe;

public enum Mark { X, O, EMPTY;


@Override
  public String toString() {
  return switch (this) {
    case X -> "X";
    case O -> "O";
    case EMPTY -> "EMPTY"; //adding override so later if i want it to be empty i can just do ""
  };
}
}
