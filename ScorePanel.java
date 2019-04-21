import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class ScorePanel extends JPanel
{
	//class constants

	//class variables
	private int score;
	private int tries;
	private JLabel lblScore;
	private JLabel lblTries;
	private Font font;

	/***************************************/

	public ScorePanel()
	{
		score = 0;
		tries = 0;

		font = new Font("Arial", Font.BOLD, 30);
		setBounds(0,800, 800, 50);

		lblScore = new JLabel("Score : " + score+"");
		lblScore.setFont(font);
		lblTries = new JLabel("Tries : " + tries+"");
		lblTries.setFont(font);

		add(lblScore);
		add(lblTries);

	}//end Constructor

	public void updateScore(int inPoint)
	{
		score += inPoint;

		lblScore.setText("Score : " + score);

	}//end updateScore

	public void updateTries(int inTry)
	{
		tries += inTry;

		lblTries.setText("Tries : " + tries);

	}//end updateTries

	public int getScore()
	{
		return score;

	}//end getScore

	public void reset()
	{
		score = 0;
		tries = 0;

		lblScore.setText("Score : " + score);
		lblTries.setText("Tries : " + tries);

	}//end reset

}//end ScorePanel