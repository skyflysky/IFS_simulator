
package sky.example.ifsSimulator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import sky.example.ifsSimulator.input.Draw;
import sky.example.ifsSimulator.input.Rule;

public class Painter
{
	public void pain(Rule rule, Draw draw)
	{
		// 迭代次数*（需输入在命令行中，或者直接赋值）*
		int trials = draw.getIterationTime();

		// 每个变换的执行概率
		double[] dist = rule.getDist();

		// 矩阵值
		double[][] cx = rule.getCx();
		double[][] cy = rule.getCy();

		// 初始值 (x, y)
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
			// 根据概率分布随机选择变换
			int r = StdRandom.discrete(dist);

			// 迭代
			double x0 = cx[r][0] * x + cx[r][1] * y + cx[r][2];
			double y0 = cy[r][0] * x + cy[r][1] * y + cy[r][2];
			x = x0;
			y = y0;

			// 绘制结果
			StdDraw.point(x, y);
			// 每迭代100次显示1次
			if (t % 1000 == 0)
			{
				StdDraw.show();

				StdDraw.pause(10);
			}
		}

		StdDraw.show();
	}
}
