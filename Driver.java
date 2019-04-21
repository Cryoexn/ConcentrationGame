import javax.swing.JFrame;

public class Driver
{
	public static void main(String[]args)
	{
		//local constants

		//local variables
		JFrame frame;
		MemoryGamePanel panel;

		/*******************************************/

		frame = new JFrame("Concentration");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new MemoryGamePanel();

		frame.getContentPane().add(panel);

		frame.setVisible(true);
		frame.pack();

	}//end main

}//end Driver