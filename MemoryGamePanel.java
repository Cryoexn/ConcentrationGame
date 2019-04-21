import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class MemoryGamePanel extends JLayeredPane
{
	//class constants
	private final int DELAY = 3000;

	//class variables
	private ScorePanel pnlScoring;
	private JPanel buttons;
	private JPanel images;
	private JButton btnReset;
	private JButton btnTemp;
	private JButton[]btnChoices;
	private ImageIcon[][]icons;
	private String[]imgChoices;
	private String[]iconPaths;
	private int count;
	private Font font;
	private Timer timer;

	/*****************************************/

	public MemoryGamePanel()
	{
		//Set defaults for LayeredPane.
		setPreferredSize(new Dimension(800,850));

		//Create GUI components.
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(4,4));
		buttons.setBounds(0, 0, 800, 800);
		icons = new ImageIcon[4][4];
		images = new JPanel();
		images.setBounds(0, 0, 800, 800);
		images.setLayout(new GridLayout(4,4));
		pnlScoring = new ScorePanel();
		timer = new Timer(DELAY, new TimerListener());
		btnReset = new JButton("Reset Game");
		btnReset.addActionListener(new ResetButtonListener());
		btnReset.setBounds(10, 812, 150, 25);

		//Create arrays for holding choices.
		btnChoices = new JButton[2];
		imgChoices = new String[2];
		count = 0;

		//Instanciate paths array.
		iconPaths = new String[16];

		//Initialize array of ImageIcon paths.
		for(int i = 1; i < 9; i++)
		{
			//Set the path for each image.
			iconPaths[i-1] = "images/" + i + ".jpg";

		}//end for

		//Font for the buttons.
		font = new Font("Arial", Font.BOLD, 30);

		//Initialize all buttons for grid.
		for(int i = 1; i < 17; i++)
		{
			btnTemp = new JButton("");
			btnTemp.addActionListener(new ButtonListener());
			btnTemp.setFont(font);
			btnTemp.setText(i+"");
			btnTemp.setName(i+"");
			buttons.add(btnTemp);

		}//end for

		//Scramble the images in random order.
		scrambleImages();

		//Add panels to the layered Pane.
		add(images, JLayeredPane.DEFAULT_LAYER);
    	add(buttons, JLayeredPane.PALETTE_LAYER);
    	add(pnlScoring, JLayeredPane.MODAL_LAYER);
    	add(btnReset, JLayeredPane.POPUP_LAYER);

		//Make buttons background transparent.
    	buttons.setOpaque(false);

	}//end Constructor

	public void scrambleImages()
	{
		//local constants

		//local variables
		int x;
		int y;
		int numSpots;
		int count;
		ImageIcon tempImg;

		/***********************************/
		numSpots = 1;
		count = 1;

		//While there are sill spots.
		while(numSpots < 17)
		{
			//Create random indexes.
			x = (int)(Math.random() * 4);
			y = (int)(Math.random() * 4);

			//If the value at the index isnt set.
			if(icons[x][y] == null)
			{
				//Set the spot to an ImageIcon.
				tempImg = new ImageIcon(iconPaths[count-1]);
				icons[x][y] = tempImg;
				icons[x][y].setDescription(count+"");

				//If the num spots has cycled 2 times.
				if(numSpots % 2 == 0)
					count++;

				//Increment number of spots taken.
				numSpots++;

			}//end if

		}//end while

		for(int i = 0; i < icons.length; i++)
		{
			for(int j = 0; j < icons.length; j++)
			{
				//Add the images at their random indexes.
				images.add(new JLabel(icons[i][j]));

			}//end inner for

		}//end outer for

		displayIconArray();

	}//end scrambleImages

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//local constants

			//local variables
			JButton button;
			int x = 0;
			int y = 0;

			/*******************************************/

			//Get the button that was clicked.
			button = (JButton)event.getSource();

			if(!button.getText().equals("") && count < 2)
			{
				//If two buttons havent been pressed
				if(count < 2)
				{
					//Which button was pushed sets the coords for the icon.
					switch(Integer.parseInt(button.getText()))
					{
						case 1:
							x = 0;
							y = 0;
							break;
						case 2:
							x = 1;
							y = 0;
							break;
						case 3:
							x = 2;
							y = 0;
							break;
						case 4:
							x = 3;
							y = 0;
							break;
						case 5:
							x = 0;
							y = 1;
							break;
						case 6:
							x = 1;
							y = 1;
							break;
						case 7:
							x = 2;
							y = 1;
							break;
						case 8:
							x = 3;
							y = 1;
							break;
						case 9:
							x = 0;
							y = 2;
							break;
						case 10:
							x = 1;
							y = 2;
							break;
						case 11:
							x = 2;
							y = 2;
							break;
						case 12:
							x = 3;
							y = 2;
							break;
						case 13:
							x = 0;
							y = 3;
							break;
						case 14:
							x = 1;
							y = 3;
							break;
						case 15:
							x = 2;
							y = 3;
							break;
						case 16:
							x = 3;
							y = 3;
							break;

					}//end switch

					//Hide the button.
					button.setText("");
					button.setOpaque(false);
					button.setContentAreaFilled(false);
					button.setBorderPainted(false);

					//Add the clicked button to the array.
					btnChoices[count] = button;
					imgChoices[count] = icons[y][x].getDescription();
					count++;

					//if the images under the buttons match.
					if(imgChoices[0].equals(imgChoices[1]) && imgChoices[0] != null && count == 2)
					{
						//Update the score.
						pnlScoring.updateScore(1);

						//Reset the choices
						btnChoices[0] = null;
						btnChoices[1] = null;

						//Reset the count;
						count = 0;
					}

					//If it is the second click.
					if(count % 2 == 0)
						pnlScoring.updateTries(1);

				}//end if

				//If the choices were not correct
				if(count == 2 && btnChoices[0] != null)
				{
					//Start the timer.
					timer.start();

				}//end if

			}//end if

		}//end actionPerformed

	}//end ButtonListener

	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(btnChoices[0] != null)
			{
				//For each button thats been pressed.
				for(JButton btn : btnChoices)
				{
					//Unhide the buttons.
					btn.setText(btn.getName());
					btn.setOpaque(true);
					btn.setContentAreaFilled(true);
					btn.setBorderPainted(true);

				}//end for

				//Reset number of buttons clicked.
				count = 0;

			}//end if

			//Stop the timer.
			timer.stop();

		}//end actionPerformed

	}//end TimerListener

	public void displayIconArray()
	{
		//For each row.
		for(int i = 0; i < icons.length; i++)
		{
			//Used for visual.
			System.out.print("[");

			//For each column.
			for(int j = 0; j < icons.length; j++)
			{
				//Used for visual.
				if(j == 3)
					System.out.print(icons[i][j].getDescription()+"");
				else
					System.out.print(icons[i][j].getDescription() + ", ");

			}//end inner for

			//Used for visual.
			System.out.println("]");

		}//end outer for

		//Seperate board displays.
		System.out.println("~");

	}//end displayIconArray

	public void clearImagesAndBtns()
	{

		images.removeAll();

		for(int i = 0; i < icons.length; i++)
		{
			for(int j = 0; j < icons.length; j++)
			{
				//Add the images at their random indexes.
				icons[i][j] = null;

			}//end inner for

		}//end outer for

		buttons.removeAll();

		//Initialize all buttons for grid.
		for(int i = 1; i < 17; i++)
		{
			btnTemp = new JButton("");
			btnTemp.addActionListener(new ButtonListener());
			btnTemp.setFont(font);
			btnTemp.setText(i+"");
			btnTemp.setName(i+"");
			buttons.add(btnTemp);

		}//end for

	}

	private class ResetButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			clearImagesAndBtns();

			scrambleImages();

			pnlScoring.reset();

			revalidate();

		}//end actionPerformed

	}//end ResetButtonListener

}//end MemoryGamePanel