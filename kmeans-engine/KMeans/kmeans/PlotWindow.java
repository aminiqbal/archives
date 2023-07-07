package kmeans;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

// AM1N
public class PlotWindow extends JFrame
{
	private static final long serialVersionUID = 3L;
	private JPanel plotview;

	public PlotWindow(int width, int height, int max_x, int max_y, double[][] datapoints, String xlabel, String ylabel)
	{
		plotview = new PlotComp(((int)(width / 1.1)), ((int)(height / 1.1)), max_x, max_y, datapoints, xlabel, ylabel);
		render(width, height, max_x, max_y, xlabel, ylabel);
	}

	public PlotWindow(int max_x, int max_y, ArrayList<double[]> centroidGroup[], double centroidValue[][], String xlabel, String ylabel)
	{
		Dimension dispDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)Math.round(dispDimension.getWidth() / 1.5);
		int height = (int)Math.round(dispDimension.getHeight() / 1.5);
		plotview = new ClusterPlotComp(((int)(width / 1.1)), ((int)(height / 1.1)), max_x, max_y, centroidGroup, centroidValue, xlabel, ylabel);
		render(width, height, max_x, max_y, xlabel, ylabel);
	}

	private void render(int width, int height, int max_x, int max_y, String xlabel, String ylabel)
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(width, height);
		setTitle("Data File Plot");
		setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
		setResizable(false);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(4, 4, 4, 4);
		add(plotview, gc);
		addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent event)
			{
			}

			@Override
			public void keyReleased(KeyEvent event)
			{
			}

			@Override
			public void keyPressed(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					dispose();
				}
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
