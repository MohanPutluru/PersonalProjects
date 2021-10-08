package game;

public class MatchResult {
  public final int noMatch;
  public final int positionalMatch;
  public final int nonPositionalMatch;

  public MatchResult(int noMatchs, int positionalMatchs, int nonPositionalMatchs) {
    noMatch = noMatchs;
    positionalMatch = positionalMatchs;
    nonPositionalMatch = nonPositionalMatchs;
  }
}
