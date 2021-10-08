package game.ui;

import game.MasterMind;
import game.MatchResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaserMindFrame extends JFrame {
  public MasterMind masterMind = new MasterMind();
  JPanel welcomePanel;
  JPanel optionsPanel;
  JPanel selectedPanel;
  JPanel historyPanel;
  int selectedColorCount = 0;
  int tries = 0;

  @Override
  protected void frameInit(){
    super.frameInit();
    setLayout(new GridLayout(6,1));

    welcomePanel = new JPanel(new GridLayout(1,1));
    JLabel WelcomeLabel = new JLabel("Welcome to MindMaster, Please guess 6 of the below 10 Colors You have "+ (20-tries) +"  tries Left");
    welcomePanel.add(WelcomeLabel);
    super.getContentPane().add(welcomePanel, "Center");

    optionsPanel = new JPanel(new GridLayout(1,11));
    setOptionsPlane();
    super.getContentPane().add(optionsPanel, "Center");

    selectedPanel = new JPanel(new GridLayout(1,7));
    selectedPanel.add(new JLabel("Selected"));
    super.getContentPane().add(selectedPanel, "Center");

    JButton submit = new JButton("Submit");
    submit.addActionListener(new submitClickHandler());
    super.getContentPane().add(submit);
    super.getContentPane().add(new JLabel("Previous Try History"));

    historyPanel = new JPanel(new GridLayout(1,8));
    //historyPanel.add(new JLabel("Previous Try history"));
    super.getContentPane().add(historyPanel, "Center");

  }


  public void setOptionsPlane(){
    optionsPanel.removeAll();
    optionsPanel.add(new JLabel("Options"));
    List<Color> colorToChoose = List.of(Color.gray, Color.red, Color.lightGray, Color.pink, Color.orange, Color.green, Color.magenta, Color.yellow, Color.cyan, Color.blue);
    for(Color color:colorToChoose){
      guessCell cell = new guessCell();
      optionsPanel.add(cell);
      cell.setBackground(color);
      cell.addActionListener(new CellClickHandler());
    }

    optionsPanel.revalidate();
  }



  private class CellClickHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent actionEvent){
      guessCell cell = (guessCell) actionEvent.getSource();
      if(selectedColorCount != 6){
        selectedPanel.add(cell);
        selectedPanel.revalidate();
        selectedColorCount++;
      }
    }
  }

  private class submitClickHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent actionEvent){
      selectedColorCount = 0;
      tries++;
      historyPanel.setLayout(new GridLayout(tries,15));
      historyPanel.add(new JLabel("TryNumber"));
      historyPanel.add(new JButton(""+tries));
      List<Color> randomColorsList = new ArrayList<>(Arrays.asList());
      for (Component component: selectedPanel.getComponents()){
        if(component instanceof guessCell){
          historyPanel.add(component);
          randomColorsList.add(component.getBackground());
        }
      }

      checkResultsAndAddHistory(randomColorsList);
      historyPanel.revalidate();
      selectedPanel.revalidate();
      setOptionsPlane();
      optionsPanel.revalidate();
      JLabel intro = (JLabel) welcomePanel.getComponent(0);
      intro.setText("Welcome to MindMaster, Please guess 6 of the below 10 Colors You have "+ (20-tries) +"  tries Left");
      welcomePanel.revalidate();

    }
  }

  public void checkGameEnd(MatchResult thisTryResut){
    if(thisTryResut.positionalMatch == 6){
      JOptionPane.showMessageDialog(new guessCell(),"Congrats You Won");
    } else if (tries == 20){
      JOptionPane.showMessageDialog(new guessCell(),"You Loose");
    }
  }

  public void checkResultsAndAddHistory(List<Color> randomColorsList){
    var thisTryResut = masterMind.checkGuessAndUpdateGameWon(List.copyOf(randomColorsList));
    checkGameEnd(thisTryResut);
    historyPanel.add(new JLabel("Result"));
    for(int i = 0; i < thisTryResut.positionalMatch; i++){
      guessCell cell = new guessCell();
      historyPanel.add(cell);
      cell.setBackground(Color.black);
      cell.setText("positionalMatch");
    }

    for(int i = 0; i < thisTryResut.nonPositionalMatch; i++){
      guessCell cell = new guessCell();
      historyPanel.add(cell);
      cell.setBackground(new Color(192, 192, 192));
      cell.setText("nonPositionalMatch");
    }

    for(int i =0; i < thisTryResut.noMatch; i++){
      guessCell cell = new guessCell();
      historyPanel.add(cell);
      cell.setBackground(Color.white);
      cell.setText("noMatch");
    }

  }

  public static void main(String[] args){
    JFrame frame = new MaserMindFrame();
    frame.setSize(400,400);
    frame.setVisible(true);
  }
}
