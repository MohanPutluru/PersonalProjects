package game;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MasterMind {
  private List<Color> selectedColors;
  public int tries = 0;
  public int numberOfSelectedColors = 6;
  private boolean gameWon = false;


  public MasterMind(){
    selectedColors = generateRandomColors();
    gameWon = false;
    this.tries = 0;
  }

  public void setSelectedColors(List<Color> ColorList){
    selectedColors=ColorList;
  }

  public List<Color> getSelectedColors(){
    return selectedColors;
  }

  public void checkGameOverDueToTries() {
    if (tries > 20) {
      throw new IllegalArgumentException("Your 20 tries are up Game Over");
    }
  }

  public void checkGameOverDueToWon() {
    if (gameWon == true) {
      throw new IllegalArgumentException("Your have Won the game Already");
    }
  }

  public boolean checkGameWon() { return this.gameWon; }


  private List<Color> generateRandomColors(){
    List<Color> colorToChoose = List.of(Color.white, Color.red, Color.lightGray, Color.pink, Color.orange, Color.green, Color.magenta, Color.yellow, Color.cyan, Color.blue);
    List<Color> randomColorsList = new ArrayList<Color>(Arrays.asList());
    Random rand = new Random();
    int randomIndex;

    for (int i = 0; i < 6; i++){
      randomIndex = rand.nextInt(10);
      while (randomColorsList.contains(colorToChoose.get(randomIndex))){
        randomIndex = (randomIndex+1)%10;
      }
      randomColorsList.add(colorToChoose.get(randomIndex));
    }

    return List.copyOf(randomColorsList);
  }


  public MatchResult guess(List<Color> guessColors) {
    int positionalMatch = 0;
    int nonPositionalMatch = 0;
    int noMatch = 0;

    for (int i = 0; i < selectedColors.size(); i++) {
      if (selectedColors.get(i) == guessColors.get(i)) {
        positionalMatch++;
      } else if (guessColors.contains(selectedColors.get(i))) {
        nonPositionalMatch++;
      } else {
        noMatch++;
      }
    }

    return new MatchResult(noMatch, positionalMatch, nonPositionalMatch);
  }

  public MatchResult checkGuessAndUpdateGameWon(List<Color> guessColors) {
    var matchResult = guess(guessColors);
    tries++;

    checkGameOverDueToWon();
    checkGameOverDueToTries();
    if (matchResult.positionalMatch == numberOfSelectedColors) { gameWon=true; }

    return matchResult;
  }

}
