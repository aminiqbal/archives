package kmeans;

import java.util.ArrayList;

// AM1N
public class KMeans
{
	private double data[][];
	private int k, n;

	public KMeans(double dataset[][], int kvalue)
	{
		data = dataset;
		k = kvalue;
		n = dataset.length;
	}

	public void kmeans()
	{
		@SuppressWarnings("unchecked")
		ArrayList<double[]> centroidGroup[] = new ArrayList[k];
		double centroidValue[][] = new double[k][2];
		double prevCentroidValue[][] = new double[k][2];
		int u;
		for(u = 0; u < k; ++u)
		{
			centroidGroup[u] = new ArrayList<double[]>();
			centroidValue[u] = data[u].clone();
		}
		recordCurrentCentroids(prevCentroidValue, centroidValue);
		centroidAssign(centroidGroup, centroidValue);
		recalculateCentroids(centroidGroup, centroidValue);
		centroidGroupClear(centroidGroup);
		while(!stopCondition(prevCentroidValue, centroidValue))
		{
			centroidGroupClear(centroidGroup);
			recordCurrentCentroids(prevCentroidValue, centroidValue);
			centroidAssign(centroidGroup, centroidValue);
			recalculateCentroids(centroidGroup, centroidValue);
		}
		new PlotWindow(100, 80, centroidGroup, centroidValue, KMeansAppWindow.xaxislabel, KMeansAppWindow.yaxislabel);
	}

	private void centroidAssign(ArrayList<double[]> centroidGroup[], double centroidValue[][])
	{
		ArrayList<Double> centroidDistances = new ArrayList<Double>();
		int u, v;
		for(u = 0; u < n; ++u)
		{
			centroidDistances.clear();
			for(v = 0; v < k; ++v)
			{
				centroidDistances.add(shortestDist(centroidValue[v], data[u]));
			}
			int minIndex = 0;
			double minValue = centroidDistances.get(0);
			for(v = 1; v < k; ++v)
			{
				if(minValue > centroidDistances.get(v))
				{
					minValue = centroidDistances.get(v);
					minIndex = v;
				}
			}
			centroidGroup[minIndex].add(data[u]);
		}
	}

//	private String[] getCentroidContents(ArrayList<double[]> centroids[])
//	{
//		String centroidData[] = new String[k];
//		int u, v;
//		for(u = 0; u < k; ++u)
//		{
//			StringBuilder stringcontain = new StringBuilder();
//			stringcontain.append("Centroid " + (u + 1) + ": ");
//			int n = centroids[u].size();
//			if(n > 0)
//			{
//				stringcontain.append("(" + centroids[u].get(0)[0] + ", " + centroids[u].get(0)[1] + ")");
//			}
//			for(v = 1; v < n; ++v)
//			{
//				stringcontain.append(", (" + centroids[u].get(v)[0] + ", " + centroids[u].get(v)[1] + ")");
//			}
//			centroidData[u] = stringcontain.toString();
//		}
//		return centroidData;
//	}

	private double shortestDist(double a[], double c[])
	{
		return Math.sqrt(((c[0] - a[0]) * (c[0] - a[0])) + ((c[1] - a[1]) * (c[1] - a[1])));
	}

	private void recordCurrentCentroids(double prevCentroidValue[][], double centroidValue[][])
	{
		int u;
		for(u = 0; u < k; ++u)
		{
			prevCentroidValue[u] = centroidValue[u].clone();
		}
	}

	private void centroidGroupClear(ArrayList<double[]> centroidGroup[])
	{
		for(ArrayList<double[]> group: centroidGroup)
		{
			group.clear();
		}
	}

	private void recalculateCentroids(ArrayList<double[]> centroidGroup[], double centroidValue[][])
	{
		int u;
		for(u = 0; u < k; ++u)
		{
			double xtotal = 0, ytotal = 0, xmean, ymean;
			for(double datapoint[]: centroidGroup[u])
			{
				xtotal += datapoint[0];
				ytotal += datapoint[1];
			}
			xmean = xtotal / centroidGroup[u].size();
			ymean = ytotal / centroidGroup[u].size();
			centroidValue[u][0] = xmean;
			centroidValue[u][1] = ymean;
		}
	}

	private boolean stopCondition(double prevCentroidValue[][], double centroidValue[][])
	{
		boolean terminate = true;
		int u;
		for(u = 0; u < k; ++u)
		{
			if((prevCentroidValue[u][0] != centroidValue[u][0]) || (prevCentroidValue[u][1] != centroidValue[u][1]))
			{
				return false;
			}
		}
		return terminate;
	}
}
