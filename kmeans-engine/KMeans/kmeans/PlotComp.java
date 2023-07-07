package kmeans;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

// AM1N
public class PlotComp extends JPanel
{
	private static final long serialVersionUID = 2L;

	private int w, h, xf, yf;
	private double p[][];
	private String xname, yname;

	public PlotComp(int width, int height, int max_x, int max_y, double[][] datapoints, String xlabel, String ylabel)
	{
		setSize(width, height);
		w = width;
		h = height;
		xf = max_x;
		yf = max_y;
		p = datapoints;
		xname = xlabel;
		yname = ylabel;
	}

	@Override
	protected void paintComponent(Graphics _g)
	{
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D)_g;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double xfac = w / xf;
		double yfac = h / yf;
		g.draw(new Line2D.Double(0, 0, 0, h));
		g.draw(new Line2D.Double(0, h, w, h));
		int u, n = p.length;
		for(u = 0; u < n; ++u)
		{
			g.fill(new Ellipse2D.Double((p[u][0] * xfac), h - (p[u][1] * yfac), 4, 4));
		}
		g.setColor(Color.blue);
		g.drawString(xname, w / 2, h - 8);
		g.drawString(xf + "", w - 8, h - 8);
		g.drawString(yname, 8, h / 2);
		g.drawString(yf + "", 8, 8);
	}
}
