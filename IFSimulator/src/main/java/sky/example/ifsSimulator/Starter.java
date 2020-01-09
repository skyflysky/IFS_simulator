
package sky.example.ifsSimulator;

import sky.example.ifsSimulator.exception.InputDataFomatException;
import sky.example.ifsSimulator.exception.NoRuleFoundException;
import sky.example.ifsSimulator.exception.NoSpecifiedElementFoundException;
import sky.example.ifsSimulator.input.Draw;
import sky.example.ifsSimulator.input.InputReader;
import sky.example.ifsSimulator.input.Rule;

public class Starter
{
	public static void main(String[] args)
	{
		InputReader reader = new InputReader();
		Rule rule = null;
		try
		{
			//在这里 输入你的 规则Id
			rule = reader.getRule(4);
		}
		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSpecifiedElementFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoRuleFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InputDataFomatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Draw draw = null;
		try
		{
			draw = reader.getDraw();
		}
		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Painter().pain(rule, draw);
		System.out.println("画完了");
	}
}
