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

package rocfox.rdf2xml.transformation.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.transformation.dom.NodeContainer;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.TransformNode;

/**
 *
 * @author Nicolas
 */
public class XmlNodeParser
{
	public XmlNodeParser(Transform transform)
	{
		if(transform == null)
		{
			throw ExceptionBuilder.createNullArgumentException("transform");
		}
		this.transform = transform;
	}
	
	private Transform transform;
	
	public Transform getTransform()
	{
		return transform;
	}
	
	public void parseChildren(Element source, NodeContainer destination)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		if(destination == null)
		{
			throw ExceptionBuilder.createNullArgumentException("destination");
		}
		NodeList nodes = source.getChildNodes();
		for(int index = 0; index < nodes.getLength(); index++)
		{
			TransformNode child = parse(nodes.item(index));
			if(child != null)
			{
				destination.getChildren().add(child);
			}
		}
	}
	
	private TransformNode parse(Node source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		else if(source instanceof Element)
		{
			XmlElementParser parser = new XmlElementParser(transform);
			return parser.parse((Element)source);
		}
		else if(source instanceof Text)
		{
			XmlTextParser parser = new XmlTextParser();
			return parser.parse((Text)source);
		}
		return null;
	}
}
