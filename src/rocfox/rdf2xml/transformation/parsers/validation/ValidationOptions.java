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

package rocfox.rdf2xml.transformation.parsers.validation;

import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class ValidationOptions
{
	private ArrayList<String> allowedChildElements = new ArrayList<String>();
	
	public ArrayList<String> getAllowedChildElements()
	{
		return allowedChildElements;
	}
	private ArrayList<String> allowedAttributes = new ArrayList<String>();
	
	public ArrayList<String> getAllowedAttributes()
	{
		return allowedAttributes;
	}
	
	private boolean allowOtherChildElements;
	
	public boolean isAllowOtherChildElements()
	{
		return allowOtherChildElements;
	}
	
	public void setAllowOtherChildElements(boolean allowOtherChildElements)
	{
		this.allowOtherChildElements = allowOtherChildElements;
	}
	
	private boolean allowChildTextNodes;
	
	public boolean isAllowChildTextNodes()
	{
		return allowChildTextNodes;
	}
	
	public void setAllowChildTextNodes(boolean allowChildTextNodes)
	{
		this.allowChildTextNodes = allowChildTextNodes;
	}
	
	private boolean allowOtherAttributes;
	
	public boolean isAllowOtherAttributes()
	{
		return allowOtherAttributes;
	}
	
	public void setAllowOtherAttributes(boolean allowOtherAttributes)
	{
		this.allowOtherAttributes = allowOtherAttributes;
	}
	
	public boolean isAllowed(Node node)
	{
		if(node instanceof Element)
		{
			return isAllowed((Element)node);
		}
		else if(node instanceof Text)
		{
			return isAllowed((Text)node);
		}
		else if(node instanceof Attr)
		{
			return isAllowed((Attr)node);
		}
		else
		{
			return false;
		}
	}
	
	public boolean isAllowed(Attr node)
	{
		if("http://www.w3.org/2001/XMLSchema-instance".equals(node.getNamespaceURI()))
		{
			return true;
		}
		else if("http://www.w3.org/XML/1998/namespace".equals(node.getNamespaceURI()))
		{
			return true;
		}
		else if(node.getNodeName().startsWith("xmlns"))
		{
			return true;
		}
		else if(isRtlNamespace(node))
		{
			boolean result = false;
			for(String name : getAllowedAttributes())
			{
				if(name.equals(node.getLocalName()))
				{
					result = true;
				}
			}
			return result;
		}
		else if(isAllowOtherChildElements())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isAllowed(Text node)
	{
		if(!isAllowChildTextNodes())
		{
			String value = node.getWholeText();
			if(value != null)
			{
				return StringUtility.isNullOrEmpty(value.trim());
			}
			return false;
		}
		return true;
	}
	
	public boolean isAllowed(Element node)
	{
		if(isRtlNamespace(node))
		{
			boolean result = false;
			for(String name : getAllowedChildElements())
			{
				if(name.equals(node.getLocalName()))
				{
					result = true;
				}
			}
			return result;
		}
		else if(isAllowOtherChildElements())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isRtlNamespace(Element node)
	{
		return Transform.NamespaceUri.equals(node.getNamespaceURI());
	}
	
	public boolean isRtlNamespace(Attr node)
	{
		if(StringUtility.isNullOrEmpty(node.getNamespaceURI()))
		{
			return true;
		}
		return Transform.NamespaceUri.equals(node.getNamespaceURI());
	}
	
	public void validate(Element node)
	{
		NodeList childNodes = node.getChildNodes();
		for(int index = 0; index < childNodes.getLength(); index ++)
		{
			Node child = childNodes.item(index);
			if(!isAllowed(child))
			{
				showError(child);
			}
		}
		NamedNodeMap attributes = node.getAttributes();
		for(int index = 0; index < attributes.getLength(); index ++)
		{
			Node child = attributes.item(index);
			if(!isAllowed(child))
			{
				showError(child);
			}
		}
	}
	
	private void showError(Node node)
	{
		if(node instanceof Text)
		{
			Text text = (Text)node;
			throw new TransformException(String.format("The text '%s' is not allowed in this location.", text.getWholeText()));
		}
		else if(node instanceof Element)
		{
			Element elem = (Element)node;
			throw new TransformException(String.format("The element '%s' is not allowed in this location.", elem.getNodeName()));
		}
		else if(node instanceof Attr)
		{
			Attr attr = (Attr)node;
			throw new TransformException(String.format("The attribute '%s' is not allowed in this location.", attr.getNodeName()));
		}
		else
		{
			throw new TransformException(String.format("The node '%s' is not allowed in this location.", node.getNodeName()));
		}
	}
}
