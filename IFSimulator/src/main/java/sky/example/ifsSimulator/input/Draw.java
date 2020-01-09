package sky.example.ifsSimulator.input;

import java.awt.Color;

public class Draw
{
	private Integer iterationTime , canvasWidth, canvasHigh , xScaleMin , xScaleMax , yScaleMin , yScaleMax;
	
	private Color penColor;
	
	private Double penRadius;

	public Draw(String iterationTime , String canvasWidth , String canvasHigh , String xScaleMin ,
			String xScaleMax , String yScaleMin , String yScaleMax , String penRadius , String colorR , 
			String colorG , String colorB) throws NumberFormatException
	{
		super();
		this.iterationTime = Integer.valueOf(iterationTime);
		this.canvasWidth = Integer.valueOf(canvasWidth);
		this.canvasHigh = Integer.valueOf(canvasHigh);
		this.xScaleMin = Integer.valueOf(xScaleMin);
		this.xScaleMax = Integer.valueOf(xScaleMax);
		this.yScaleMin = Integer.valueOf(yScaleMin);
		this.yScaleMax = Integer.valueOf(yScaleMax);
		this.penRadius = Double.valueOf(penRadius);
		this.penColor = new Color(Integer.valueOf(colorR), Integer.valueOf(colorG), Integer.valueOf(colorB));
	}

	public Integer getIterationTime()
	{
		return iterationTime;
	}

	public Integer getCanvasWidth()
	{
		return canvasWidth;
	}

	public Integer getCanvasHigh()
	{
		return canvasHigh;
	}

	public Integer getxScaleMin()
	{
		return xScaleMin;
	}

	public Integer getxScaleMax()
	{
		return xScaleMax;
	}

	public Integer getyScaleMin()
	{
		return yScaleMin;
	}

	public Integer getyScaleMax()
	{
		return yScaleMax;
	}

	public Color getPenColor()
	{
		return penColor;
	}

	public Double getPenRadius()
	{
		return penRadius;
	}
}
