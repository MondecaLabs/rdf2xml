/*
 *  Copyright 2008 Nicolas Cochard
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package rocfox.rdf2xml.utilities.xml.unitTest;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.Assert;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author Nicolas
 */
public class XmlAssert
{
	public static void assertEquals(Document x, Document y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			assertEquals(x.getDocumentElement(), y.getDocumentElement());
		}
	}
	
	private static void assertEquals(Element x, Element y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("An element is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.getLocalName(), y.getLocalName());
			Assert.assertEquals(x.getNamespaceURI(), y.getNamespaceURI());
			assertEquals(prepare(x.getAttributes()), prepare(y.getAttributes()));
			assertEquals(prepare(x.getChildNodes()), prepare(y.getChildNodes()));
		}
	}
	
	private static TreeMap<String, Attr> prepare(NamedNodeMap attributes)
	{
		if(attributes == null)
		{
			return null;
		}
		TreeMap<String, Attr> result = new TreeMap<String, Attr>();
		for(int index = 0; index < attributes.getLength(); index++)
		{
			Node node = attributes.item(index);
			if(node instanceof Attr)
			{
				Attr attr = (Attr)node;
				if(!attr.getName().startsWith("xmlns"))
				{
					result.put(attr.getLocalName(), attr);
				}
			}
		}
		return result;
	}
	
	private static ArrayList<Node> prepare(NodeList nodes)
	{
		if(nodes == null)
		{
			return null;
		}
		ArrayList<Node> result = new ArrayList<Node>();
		for(int index = 0; index < nodes.getLength(); index++)
		{
			Node node = nodes.item(index);
			boolean include = true;
			if(node instanceof Text)
			{
				Text text = (Text)node;
				String value = text.getWholeText();
				if(value != null)
				{
					value = value.trim();
				}
				include = ((value != null) && (value.length() > 0));
			}
			if(include)
			{
				result.add(node);
			}
		}
		return result;
	}
	
	private static void assertEquals(Node x, Node y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			if((x instanceof Element) && (y instanceof Element))
			{
				assertEquals((Element) x, (Element) y);
			}
			else if((x instanceof Text) && (y instanceof Text))
			{
				assertEquals((Text) x, (Text) y);
			}
			else if((x instanceof Comment) && (y instanceof Comment))
			{
				assertEquals((Comment) x, (Comment) y);
			}
			else
			{
				throw new AssertionError("The two nodes are diferent.");
			}
		}
	}
	
	private static void assertEquals(TreeMap<String, Attr> x, TreeMap<String, Attr> y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.size(), y.size());
			for(String name : x.keySet())
			{
				Attr attrX = x.get(name);
				Attr attrY = y.get(name);
				assertEquals(attrX, attrY);
			}
		}
	}
	
	private static void assertEquals(Attr x, Attr y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.getLocalName(), y.getLocalName());
			Assert.assertEquals(x.getNamespaceURI(), y.getNamespaceURI());
			Assert.assertEquals(x.getValue(), y.getValue());
		}
	}
	
	private static void assertEquals(Text x, Text y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.getWholeText(), y.getWholeText());
		}
	}
	
	private static void assertEquals(Comment x, Comment y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.getTextContent(), y.getTextContent());
		}
	}
	
	public static void assertEquals(ArrayList<Node> x, ArrayList<Node> y)
	{
		if(((x == null) && (y != null)) || ((x != null) && (y == null)))
		{
			throw new AssertionError("A document is null but not the other one.");
		}
		else if(x != null)
		{
			Assert.assertEquals(x.size(), y.size());
			for(int index = 0; index < x.size(); index++)
			{
				Node childX = x.get(index);
				Node childY = y.get(index);
				assertEquals(childX, childY);
			}
		}
	}
}
