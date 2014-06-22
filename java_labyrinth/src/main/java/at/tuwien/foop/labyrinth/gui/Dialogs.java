package at.tuwien.foop.labyrinth.gui;

import javax.swing.JOptionPane;

import at.tuwien.foop.labyrinth.model.Mouse;

public class Dialogs
{
	public static boolean showServerOrClientDialog()
	{
		int result = JOptionPane
				.showConfirmDialog(
						null,
						"Would you like to host a game? If not you can connect to a server.",
						"Network", JOptionPane.YES_NO_OPTION);

		return (result == 0);
	}

	public static String showIPDialog()
	{
		String s = (String) JOptionPane.showInputDialog(null,
				"Please enter the host's network address or IP:", "Network",
				JOptionPane.QUESTION_MESSAGE);

		if (s == null)
			s = "";

		return s;
	}

	public static void showGameEndedDialog(Mouse wonMouse)
	{
		JOptionPane.showMessageDialog(null,
				"Game ended, player <" + wonMouse.getColor() + "> won!",
				"Game ended", JOptionPane.PLAIN_MESSAGE);
	}
}
