package sky.example.ifsSimulator.input;

public class Rule
{
	private Double[] dist;
	
	private Double[][] cx;
	
	private Double[][] cy;

	private String src;
	
	private Integer id;

	public Rule(Double[] dist , Double[][] cx , Double[][] cy , String src , Integer id)
	{
		super();
		this.dist = dist;
		this.cx = cx;
		this.cy = cy;
		this.src = src;
		this.id = id;
	}

	public double[] getDist()
	{
		double[] d = new double[dist.length];
		for(int i = 0 ; i < dist.length ; i++)
		{
			d[i] = dist[i];
		}
		return d;
	}

	public double[][] getCx()
	{
		double[][] d = new double[cx.length][];
		for(int i = 0 ; i < cx.length ; i ++)
		{
			double[] dd = new double[cx[i].length];
			for(int j = 0 ; j < cx[i].length ; j++)
			{
				dd[j] = cx[i][j];
			}
			d[i] = dd;
		}
		return d;
	}

	public double[][] getCy()
	{
		double[][] d = new double[cy.length][];
		for(int i = 0 ; i < cy.length ; i ++)
		{
			double[] dd = new double[cy[i].length];
			for(int j = 0 ; j < cy[i].length ; j++)
			{
				dd[j] = cy[i][j];
			}
			d[i] = dd;
		}
		return d;
	}

	public String getSrc()
	{
		return src;
	}

	public Integer getId()
	{
		return id;
	}
	
	
}
