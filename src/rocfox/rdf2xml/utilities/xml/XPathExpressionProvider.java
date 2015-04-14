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

import java.util.TreeMap;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.xml.TransformNamespaceContext;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XPathExpressionProvider
{
	private static XPath xPath = null;
	
	private static XPath getXPath()
	{
		if(xPath == null)
		{
			XPathFactory xpf = XPathFactory.newInstance();
			xPath = xpf.newXPath();
			xPath.setNamespaceContext(new TransformNamespaceContext());
		}
		return xPath;
	}
	
	private static TreeMap<String, XPathExpression> expressions = new TreeMap<String, XPathExpression>();
	
	public static XPathExpression create(String xpath)
	{
		if(StringUtility.isNullOrEmpty(xpath))
		{
			throw ExceptionBuilder.createNullArgumentException("xpath");
		}
		if(expressions.containsKey(xpath))
		{
			return expressions.get(xpath);
		}
		else
		{
			try
			{
				XPathExpression result = getXPath().compile(xpath);
				expressions.put(xpath, result);
				return result;
			}
			catch (XPathExpressionException exception)
			{
				throw new TransformException(exception.getMessage(), exception);
			}
		}
	}
}
