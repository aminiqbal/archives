package kmeans;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

// AM1N
public class KMeansAppWindow extends JFrame
{
	private static final long serialVersionUID = 1L;

	private int width, height, winwidth, winheight;
	public static String xaxislabel, yaxislabel;
	private double datapoints[][];

	public KMeansAppWindow(String datafilepath)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		Dimension dispDimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)Math.round(dispDimension.getWidth() / 1.5);
		height = (int)Math.round(dispDimension.getHeight() / 1.5);
		winwidth = (int)Math.round(dispDimension.getHeight() / 2);
		winheight = winwidth;
		setSize(winwidth, winheight);
		setTitle("K-Means");
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				prepDataFile(Paths.get(datafilepath));
			}
		});

		setVisible(true);
	}

	private void prepDataFile(Path filepath)
	{
		try
		{
			List<String> rawtext = Files.readAllLines(filepath);
			int u, n = rawtext.size();
			if(n < 5)
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						JOptionPane.showMessageDialog(null, "Data file invalid:\n    " + filepath, "ERROR", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				});

			}
			String part[] = rawtext.get(0).split(",");
			xaxislabel = part[0];
			yaxislabel = part[1];
			datapoints = new double[n - 1][2];

			for(u = 1; u < n; ++u)
			{
				part = rawtext.get(u).split(",");
				datapoints[u - 1][0] = Double.parseDouble(part[0]);
				datapoints[u - 1][1] = Double.parseDouble(part[1]);
			}

			JLabel progheading = new JLabel("<html><b>K-MEANS<b></html>");
			progheading.setFont(new Font(getFont().getName(), getFont().getStyle(), 24));
			JLabel fileinfo = new JLabel("<html>Datafile ~ <b><i>" + filepath + "</i></b></html>");
			fileinfo.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));

			JButton rawplotButton = new JButton("<html><b>Plot Data File</b></html>");
			rawplotButton.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));
			rawplotButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							new PlotWindow(width, height, 100, 80, datapoints, xaxislabel, yaxislabel);
						}
					});
				}
			});

			JTextField kvalue = new JTextField();
			kvalue.setBorder(new TitledBorder("k"));
			kvalue.addKeyListener(new KeyListener()
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
					if((event.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (event.getKeyCode() == KeyEvent.VK_DELETE))
					{
						kvalue.setEditable(true);
					}
					else if(Character.isDigit(event.getKeyChar()))
					{
						if(kvalue.getText().length() < 1)
						{
							kvalue.setEditable(true);
						}
						else
						{
							kvalue.setEditable(false);
						}
					}
					else
					{
						kvalue.setEditable(false);
					}
				}
			});

			JButton kalgorithmButton = new JButton("<html><b>Run <i>K-means</i> Algorithm</b></html>");
			kalgorithmButton.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));
			kalgorithmButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							String _k = kvalue.getText();
							if(_k.equals(""))
							{
								_k = "0";
							}
							kmeans(datapoints, Integer.parseInt(_k));
						}
					});
				}
			});
			JPanel kgroup = new JPanel();
			kgroup.setLayout(new GridBagLayout());
			GridBagConstraints kgroupConstraints = new GridBagConstraints();
			kgroupConstraints.fill = GridBagConstraints.BOTH;
			kgroupConstraints.weightx = 1;
			kgroupConstraints.weighty = 1;
			kgroupConstraints.gridx = 0;
			kgroupConstraints.gridy = 0;
			kgroup.add(kvalue, kgroupConstraints);
			kgroupConstraints.gridx = 1;
			kgroup.add(kalgorithmButton, kgroupConstraints);

			JButton exitButton = new JButton("<html><b>Exit</b></html>");
			exitButton.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));
			exitButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					System.exit(0);
				}
			});

			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.weightx = 1;
			gc.weighty = 1;
			gc.insets = new Insets(8, 8, 8, 8);
			gc.fill = GridBagConstraints.CENTER;
			add(progheading, gc);
			gc.gridy = 1;
			add(fileinfo, gc);
			gc.gridy = 2;
			gc.fill = GridBagConstraints.HORIZONTAL;
			add(rawplotButton, gc);
			gc.gridy = 3;
			add(kgroup, gc);
			gc.weightx = 1;
			gc.gridx = 0;
			gc.gridy = 4;
			add(exitButton, gc);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void kmeans(double datapoints[][], int k)
	{
		if((k < 2) || (k > (datapoints.length / 2)))
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					JOptionPane.showMessageDialog(null, "<html>The value of <b>k</b> is invalid...<br/>Please make sure:<br/>&nbsp;&nbsp;&nbsp;&nbsp;&#10003; <b>k &ge; 1</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&#10003; <b>k &le; 10</b></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		else
		{
			new KMeans(datapoints, k).kmeans();
		}
	}
}
