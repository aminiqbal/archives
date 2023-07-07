package kmeans;

import javax.swing.JOptionPane;

// AM1N
public class Driver
{
	public static void main(String arg[])
	{
		if(arg.length > 0)
		{
			new KMeansAppWindow(arg[0]);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No program argument provided...\nPlease ensure full path to a data file is provided as program argument.\nUsage:\n        java -jar kmeans.jar \"full/path/to/datafile.csv\"", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
