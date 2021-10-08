package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MasterMindTest {
  MasterMind mastermind;

  @BeforeEach
  public void setup() {
    mastermind = new MasterMind();
    var selectedColors = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );
    mastermind.setSelectedColors(selectedColors);
  }

  @Test
  public void allColorsMatchInPosition(){
    var guesses = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(0, actualResult.noMatch);
    assertEquals(6, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
  }

  @Test
  public void firstFourColorsMatchInPosition(){
    var guesses = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.green, Color.orange );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(2, actualResult.noMatch);
    assertEquals(4, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
  }

  @Test
  public void lastFourColorsMatchInPosition(){
    var guesses = List.of( Color.green, Color.orange, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(2, actualResult.noMatch);
    assertEquals(4, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
  }

  @Test
  public void first3InPositionLast2OutofPosition(){
    var guesses = List.of( Color.red, Color.blue, Color.cyan, Color.green, Color.pink, Color.yellow );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(1, actualResult.noMatch);
    assertEquals(3, actualResult.positionalMatch);
    assertEquals(2, actualResult.nonPositionalMatch);
  }

  @Test
  public void allColorOutofPosition(){
    var guesses = List.of( Color.blue, Color.red, Color.darkGray, Color.cyan, Color.pink, Color.yellow );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(0, actualResult.noMatch);
    assertEquals(0, actualResult.positionalMatch);
    assertEquals(6, actualResult.nonPositionalMatch);
  }

  @Test
  public void firstAndThirdMismatch2inPositionRestOutOfPosition(){
    var guesses = List.of( Color.green, Color.blue, Color.orange, Color.yellow, Color.pink, Color.darkGray );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(2, actualResult.noMatch);
    assertEquals(1, actualResult.positionalMatch);
    assertEquals(3, actualResult.nonPositionalMatch);
  }

  @Test
  public void allNonMatching(){
    var guesses = List.of( Color.green, Color.green, Color.orange, Color.orange, Color.lightGray, Color.white );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(6, actualResult.noMatch);
    assertEquals(0, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
  }

  @Test
  public void sixSameColorsAsFirstSecletedColor(){
    var guesses = List.of( Color.red, Color.red, Color.red, Color.red, Color.red, Color.red );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(5, actualResult.noMatch);
    assertEquals(1, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
  }

  @Test
  public void firstSameAsSecondSelectedAnd2To6SameAsFirstSelected(){
    var guesses = List.of( Color.blue, Color.red, Color.red, Color.red, Color.red, Color.red );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(4, actualResult.noMatch);
    assertEquals(0, actualResult.positionalMatch);
    assertEquals(2, actualResult.nonPositionalMatch);
  }

  @Test
  public void firstMismatchAnd2To6SameAsFirstSelected(){
    var guesses = List.of( Color.green, Color.red, Color.red, Color.red, Color.red, Color.red );

    MatchResult actualResult = mastermind.guess(guesses);

    assertEquals(5, actualResult.noMatch);
    assertEquals(0, actualResult.positionalMatch);
    assertEquals(1, actualResult.nonPositionalMatch);
  }

  @Test
  public void guessAfterSuccessfulGuess(){
    var correctGuess = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );
    MatchResult actualResult = mastermind.checkGuessAndUpdateGameWon(correctGuess);
    var badGuesses = List.of( Color.green, Color.red, Color.red, Color.red, Color.red, Color.red );

    try {
      MatchResult actualResult2 = mastermind.checkGuessAndUpdateGameWon(badGuesses);
      fail("test failed: User is able to keep guessing even after win/loose");
    } catch (IllegalArgumentException e) {
      assert(e.getMessage() == "Your have Won the game Already");
    }

  }

  @Test
  public void correctGuessAfter19IncorrectGuesses(){
    mastermind.tries = 19;
    var correctGuess = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    MatchResult actualResult = mastermind.checkGuessAndUpdateGameWon(correctGuess);
    boolean gameWon = mastermind.checkGameWon();

    assertEquals(true, gameWon);
    assertEquals(0, actualResult.noMatch);
    assertEquals(6, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
    assertEquals(20, mastermind.tries);
  }

  @Test
  public void wrongGuessAfter19IncorrectGuesses(){
    mastermind.tries = 19;
    var wrongGuess = List.of( Color.blue, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    MatchResult actualResult = mastermind.checkGuessAndUpdateGameWon(wrongGuess);
    boolean gameWon = mastermind.checkGameWon();

    assertEquals(false, gameWon);
    assertEquals(1, actualResult.noMatch);
    assertEquals(5, actualResult.positionalMatch);
    assertEquals(0, actualResult.nonPositionalMatch);
    assertEquals(20, mastermind.tries);
  }

  @Test
  public void guessAfter20IncorrectGuesses(){
    mastermind.tries = 20;
    var correctGuess = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    try {
      MatchResult actualResult = mastermind.checkGuessAndUpdateGameWon(correctGuess);
      fail("test failed: User is able to keep guessing even after win/loose");
    } catch (IllegalArgumentException e) {
      assert(e.getMessage() == "Your 20 tries are up Game Over");
    }

  }

  @Test
  public void guessAfter21IncorrectGuesses(){
    mastermind.tries = 21;
    var correctGuess = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    try {
      MatchResult actualResult = mastermind.checkGuessAndUpdateGameWon(correctGuess);
      fail("test failed: User is able to keep guessing even after win/loose");
    } catch (IllegalArgumentException e) {
      assert(e.getMessage() == "Your 20 tries are up Game Over");
    }

  }

  @Test
  public void generateRandomColors(){
    var RandomColorList = mastermind.getSelectedColors();
    List<Color> hardCodedList = List.of( Color.red, Color.blue, Color.cyan, Color.darkGray, Color.yellow, Color.pink );

    assertEquals(hardCodedList.getClass(), RandomColorList.getClass() );
    assertEquals(Color.class, RandomColorList.get(0).getClass());
    assertEquals(6, RandomColorList.size());
  }

  @Test
  public void check2Instance(){
    var instance1 = new MasterMind();
    var instance2 = new MasterMind();

    assertNotEquals(instance1.getSelectedColors(), instance2.getSelectedColors());
  }
}

