package sky.example.ifsSimulator.input;

import java.util.List;
import java.util.Optional;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sky.example.ifsSimulator.exception.InputDataFomatException;
import sky.example.ifsSimulator.exception.NoRuleFoundException;
import sky.example.ifsSimulator.exception.NoSpecifiedElementFoundException;

@SuppressWarnings("unchecked")
public class InputReader
{
	public Draw getDraw() throws NumberFormatException , NullPointerException
	{
		Element root = getRootElemet(),iterationTime = root.element("iterationTime"),canvasSize = root.element("canvasSize"),xScale = root.element("xScale"),yScale = root.element("yScale"),penRadius = root.element("penRadius"),penColor = root.element("penColor");
		return new Draw(iterationTime.attributeValue("value"), canvasSize.attributeValue("width"), canvasSize.attributeValue("high"), xScale.attributeValue("min"), xScale.attributeValue("max"), yScale.attributeValue("min"), yScale.attributeValue("max"), penRadius.attributeValue("value"), penColor.attributeValue("r"), penColor.attributeValue("g"), penColor.attributeValue("b"));
	}
	
	public Rule getRule(Integer id) throws NoRuleFoundException , InputDataFomatException , NumberFormatException , NoSpecifiedElementFoundException , NullPointerException
	{
		Element rules = getRootElemet().element("rules");
		Element rule = null;
		try
		{
			rule = getElementByAttribute(rules, Optional.of("rule"), Optional.of("id"), Optional.of(id.toString()));
		}
		catch (NoSpecifiedElementFoundException e)
		{
			throw new NoRuleFoundException("can not find rule with id " + id);
		}
		return getRuleFromRule(rule);
	}
	/**
	 * 根据rule节点 生成需要的rule对象
	 * @param rule
	 * @return
	 * @throws InputDataFomatException
	 * @throws NumberFormatException
	 */

	private Rule getRuleFromRule(Element rule) throws InputDataFomatException , NumberFormatException , NoSpecifiedElementFoundException
	{
		//取 本规则的来源src 和规则id
		String src = rule.attributeValue("src");
		Integer id = Integer.valueOf(rule.attributeValue("id"));
		
		//取 概率节点元素 和 矩阵X节点元素 和矩阵Y节点元素
		Element probabilities = rule.element("probabilities");
		Element matrices = rule.element("matrices");
		Element matrixX = getElementByAttribute(matrices, Optional.of("matrix"), Optional.of("dimension"), Optional.of("1"));
		Element matrixY = getElementByAttribute(matrices, Optional.of("matrix"), Optional.of("dimension"), Optional.of("2"));
		
		//验证 概率的个数 和 矩阵的个数一致
		int probabilityCount = probabilities.elements().size();
		if(probabilityCount != matrixX.elements().size() || probabilityCount != matrixY.elements().size())
		{
			throw new InputDataFomatException("The count of probabilities and matrices are different");
		}
		//建立接受数组
		Double[] distArray = new Double[probabilityCount];
		Double[][] cxArray = new Double[probabilityCount][3] , cyArray = new Double[probabilityCount][3];
		//计算概率之和
		Double probailitySum = 0.0;
		//每次循环是添加一个变换规则的内容
		for(Integer c = 0 ; c < probabilityCount ; c++)
		{
			//取变换规则的概率
			Element probability = getElementByAttribute(probabilities, Optional.of("probability"), Optional.of("index"), Optional.of(c.toString()));
			distArray[c] = Double.valueOf(probability.getTextTrim());
			probailitySum += distArray[c];
			
			//取矩阵的x值和y值的一行
			Element matrixXTr = getElementByAttribute(matrixX, Optional.of("tr"), Optional.of("index"), Optional.of(c.toString()));
			Element matrixYTr = getElementByAttribute(matrixY, Optional.of("tr"), Optional.of("index"), Optional.of(c.toString()));
			
			for(Integer cc = 0 ; cc < 3 ; cc ++)
			{
				//取矩阵 x值和y值的列
				Element matrixXTd = getElementByAttribute(matrixXTr, Optional.of("td"), Optional.of("index"), Optional.of(cc.toString()));
				Element matrixYTd = getElementByAttribute(matrixYTr, Optional.of("td"), Optional.of("index"), Optional.of(cc.toString()));
				
				cxArray[c][cc] = Double.valueOf(matrixXTd.getTextTrim());
				cyArray[c][cc] = Double.valueOf(matrixYTd.getTextTrim());
			}
		}
		if(probailitySum - Double.valueOf("1") != 0)
		{
			throw new InputDataFomatException("The sum of probabilities is not 1");
		}
		return new Rule(distArray, cxArray, cyArray, src, id);
	}
	
	/**
	 * 取根节点
	 * @return
	 */
	private Element getRootElemet()
	{
		Document document = null;
		try
		{
			document = new SAXReader().read(getClass().getClassLoader().getResourceAsStream("ifs.xml"));
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return document.getRootElement();
	}
	
	/**
	 * 取节点的子节点
	 * @param element 被取值的节点
	 * @param nodeName 子节点的类型名
	 * @param attrName 子节点的属性名
	 * @param attrValue 子节点的属性值
	 * @return 取得的子节点
	 * @throws InputDataFomatException
	 */
	public static Element getElementByAttribute(Element element , Optional<String>nodeName , Optional<String> attrName , Optional<String> attrValue) throws NoSpecifiedElementFoundException
	{
		List<Element> list = (List<Element>) element.elements();
		for(Element e : list)
		{
			if(nodeName.get().equals(e.getName()))
			{
				if(attrValue.get().equals(e.attributeValue(attrName.get())))
				{
					return e;
				}
			}
		}
		throw new NoSpecifiedElementFoundException("The specified attribute '" + attrName.get() + "=" + attrValue.get() + "' is missing from the node:'" + element.getNodeTypeName());
	}
}
