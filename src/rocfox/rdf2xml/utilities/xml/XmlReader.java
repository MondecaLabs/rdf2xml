/*
 * Copyright 2008 Nicolas Cochard
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rocfox.rdf2xml.utilities.xml;

import java.util.ArrayList;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XmlReader
{
	public static Element selectSingleChildElement(Element source, String elementName)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("Source");
		}
		if(elementName == null)
		{
			throw ExceptionBuilder.createNullArgumentException("elementName");
		}
		try
		{
			XPathExpression expression = XPathExpressionProvider.create(rocfox.rdf2xml.transformation.dom.Transform.NamespacePreferredPrefix + ":" + elementName);
			NodeList nodes = (NodeList)expression.evaluate(source, XPathConstants.NODESET);
			int lenght = nodes.getLength();
			if(lenght <= 0)
			{
				return null;
			}
			else if(lenght == 1)
			{
				return (Element) nodes.item(0);
			}
			else
			{
				throw new TransformException("More than one child element '" + elementName + "' was found.");
			}
		}
		catch (XPathExpressionException ex)
		{
			throw new TransformException("Error querrying the list of children elements '" + elementName + "'.", ex);
		}
	}
	
	public static ArrayList<Element> selectChildElements(Element source, String elementName)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("Source");
		}
		if(elementName == null)
		{
			throw ExceptionBuilder.createNullArgumentException("elementName");
		}
		try
		{
			XPathExpression expression = XPathExpressionProvider.create(rocfox.rdf2xml.transformation.dom.Transform.NamespacePreferredPrefix + ":" + elementName);
			NodeList nodes = (NodeList)expression.evaluate(source, XPathConstants.NODESET);
			ArrayList<Element> result = new ArrayList<Element>();
			for(int index = 0; index < nodes.getLength(); index++)
			{
				Node node = nodes.item(index);
				if(node instanceof Element)
				{
					result.add((Element)node);
				}
			}
			return result;
		}
		catch (XPathExpressionException ex)
		{
			throw new TransformException("Error querrying the list of children elements '" + elementName + "'.", ex);
		}
	}
	
	public static String selectAttribute(Element source, String attributeName)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("Source");
		}
		if(attributeName == null)
		{
			throw ExceptionBuilder.createNullArgumentException("attributeName");
		}
		String result = source.getAttributeNS(Transform.NamespaceUri, attributeName);
		if(StringUtility.isNullOrEmpty(result))
		{
			result = source.getAttribute(attributeName);
		}
		return result;
	}
	
	public static String readTextContent(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("Source");
		}
		return readTextContent(source.getTextContent());
	}
	
	public static String readTextContent(Text source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("Source");
		}
		return readTextContent(source.getTextContent());
	}
	
	private static String readTextContent(String source)
	{
		if(StringUtility.isNullOrEmpty(source))
		{
			return null;
		}
		if(source.trim().length() > 0)
		{
			return source;
		}
		return null;
	}
}
