package kmeans;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;

// AM1N
public class ClusterPlotComp extends JPanel
{
	private static final long serialVersionUID = 4L;
	private final Color color[] = {Color.red, Color.blue, Color.green, Color.magenta, Color.pink, Color.orange, Color.yellow, Color.gray, Color.lightGray};

	private int w, h, xf, yf;
	private ArrayList<double[]> p[];
	private double cv[][];
	private String xname, yname;

	public ClusterPlotComp(int width, int height, int max_x, int max_y, ArrayList<double[]> centroidGroup[], double centroidValue[][], String xlabel, String ylabel)
	{
		setSize(width, height);
		w = width;
		h = height;
		xf = max_x;
		yf = max_y;
		p = centroidGroup;
		cv = centroidValue;
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
		int u, v, n = p.length;
		int moveby = 20;
		for(u = 0; u < n; ++u)
		{
			int nn = p[u].size();
			g.setColor(color[u]);
			g.drawString("Cluster " + (u + 1), w / 2, (u * 2) + moveby);
			g.drawString("Size: " + nn, w / 2, (u * 2) + moveby + 20);
			g.drawString("Centroid: (" + cv[u][0] + ", " + cv[u][1] + ")", w / 2, (u * 2) + moveby + 40);
			for(v = 0; v < nn; ++v)
			{
				g.fill(new Ellipse2D.Double((p[u].get(v)[0] * xfac), h - (p[u].get(v)[1] * yfac), 4, 4));
			}
			moveby += 80;
		}
		g.setColor(Color.gray);
		g.drawString(xname, w / 2, h - 8);
		g.drawString(xf + "", w - 8, h - 8);
		g.drawString(yname, 8, h / 2);
		g.drawString(yf + "", 8, 8);
	}
}
