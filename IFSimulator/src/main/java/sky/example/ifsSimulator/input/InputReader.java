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
	 * ����rule�ڵ� ������Ҫ��rule����
	 * @param rule
	 * @return
	 * @throws InputDataFomatException
	 * @throws NumberFormatException
	 */

	private Rule getRuleFromRule(Element rule) throws InputDataFomatException , NumberFormatException , NoSpecifiedElementFoundException
	{
		//ȡ ���������Դsrc �͹���id
		String src = rule.attributeValue("src");
		Integer id = Integer.valueOf(rule.attributeValue("id"));
		
		//ȡ ���ʽڵ�Ԫ�� �� ����X�ڵ�Ԫ�� �;���Y�ڵ�Ԫ��
		Element probabilities = rule.element("probabilities");
		Element matrices = rule.element("matrices");
		Element matrixX = getElementByAttribute(matrices, Optional.of("matrix"), Optional.of("dimension"), Optional.of("1"));
		Element matrixY = getElementByAttribute(matrices, Optional.of("matrix"), Optional.of("dimension"), Optional.of("2"));
		
		//��֤ ���ʵĸ��� �� ����ĸ���һ��
		int probabilityCount = probabilities.elements().size();
		if(probabilityCount != matrixX.elements().size() || probabilityCount != matrixY.elements().size())
		{
			throw new InputDataFomatException("The count of probabilities and matrices are different");
		}
		//������������
		Double[] distArray = new Double[probabilityCount];
		Double[][] cxArray = new Double[probabilityCount][3] , cyArray = new Double[probabilityCount][3];
		//�������֮��
		Double probailitySum = 0.0;
		//ÿ��ѭ�������һ���任���������
		for(Integer c = 0 ; c < probabilityCount ; c++)
		{
			//ȡ�任����ĸ���
			Element probability = getElementByAttribute(probabilities, Optional.of("probability"), Optional.of("index"), Optional.of(c.toString()));
			distArray[c] = Double.valueOf(probability.getTextTrim());
			probailitySum += distArray[c];
			
			//ȡ�����xֵ��yֵ��һ��
			Element matrixXTr = getElementByAttribute(matrixX, Optional.of("tr"), Optional.of("index"), Optional.of(c.toString()));
			Element matrixYTr = getElementByAttribute(matrixY, Optional.of("tr"), Optional.of("index"), Optional.of(c.toString()));
			
			for(Integer cc = 0 ; cc < 3 ; cc ++)
			{
				//ȡ���� xֵ��yֵ����
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
	 * ȡ���ڵ�
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
	 * ȡ�ڵ���ӽڵ�
	 * @param element ��ȡֵ�Ľڵ�
	 * @param nodeName �ӽڵ��������
	 * @param attrName �ӽڵ��������
	 * @param attrValue �ӽڵ������ֵ
	 * @return ȡ�õ��ӽڵ�
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
