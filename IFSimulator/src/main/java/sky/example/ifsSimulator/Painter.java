
package sky.example.ifsSimulator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import sky.example.ifsSimulator.input.Draw;
import sky.example.ifsSimulator.input.Rule;

public class Painter
{
	public void pain(Rule rule, Draw draw)
	{
		// ��������*�����������������У�����ֱ�Ӹ�ֵ��*
		int trials = draw.getIterationTime();

		// ÿ���任��ִ�и���
		double[] dist = rule.getDist();

		// ����ֵ
		double[][] cx = rule.getCx();
		double[][] cy = rule.getCy();

		// ��ʼֵ (x, y)
		double x = 0.0 , y = 0.0;

		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(draw.getCanvasWidth(), draw.getCanvasHigh());
		/*
		StdDraw.setXscale(draw.getxScaleMin(), draw.getxScaleMax());
		StdDraw.setYscale(draw.getyScaleMin(), draw.getyScaleMax());
		StdDraw.setPenRadius(draw.getPenRadius());
		*/
		StdDraw.setPenColor(draw.getPenColor());

		for (int t = 0; t < trials; t++)
		{
			// ���ݸ��ʷֲ����ѡ��任
			int r = StdRandom.discrete(dist);

			// ����
			double x0 = cx[r][0] * x + cx[r][1] * y + cx[r][2];
			double y0 = cy[r][0] * x + cy[r][1] * y + cy[r][2];
			x = x0;
			y = y0;

			// ���ƽ��
			StdDraw.point(x, y);
			// ÿ����100����ʾ1��
			if (t % 1000 == 0)
			{
				StdDraw.show();

				StdDraw.pause(10);
			}
		}

		StdDraw.show();
	}
}
