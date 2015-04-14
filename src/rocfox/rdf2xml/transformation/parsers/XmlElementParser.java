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

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.CallTemplate;
import rocfox.rdf2xml.transformation.dom.Choose;
import rocfox.rdf2xml.transformation.dom.Comment;
import rocfox.rdf2xml.transformation.dom.If;
import rocfox.rdf2xml.transformation.dom.RtlText;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.TransformNode;
import rocfox.rdf2xml.transformation.dom.ValueOf;
import rocfox.rdf2xml.transformation.dom.XmlElement;

/**
 *
 * @author Nicolas
 */
public class XmlElementParser
{
	public XmlElementParser(Transform transform)
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
	
	public TransformNode parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		String namespaceUri = source.getNamespaceURI();
		if(Transform.NamespaceUri.equals(namespaceUri))
		{
			String name = source.getLocalName();
			if(CallTemplate.ElementName.equals(name))
			{
				CallTemplateParser parser = new CallTemplateParser(transform);
				return parser.parse(source);
			}
			else if(Choose.ElementName.equals(name))
			{
				ChooseParser parser = new ChooseParser(transform);
				return parser.parse(source);
			}
			else if(Comment.ElementName.equals(name))
			{
				CommentParser parser = new CommentParser();
				return parser.parse(source);
			}
			else if(If.ElementName.equals(name))
			{
				IfParser parser = new IfParser(transform);
				return parser.parse(source);
			}
			else if(RtlText.ElementName.equals(name))
			{
				RtlTextParser parser = new RtlTextParser();
				return parser.parse(source);
			}
			else if(ValueOf.ElementName.equals(name))
			{
				ValueOfParser parser = new ValueOfParser();
				return parser.parse(source);
			}
			else
			{
				throw new TransformException("Unknown element name '" + name + "'.");
			}
		}
		else
		{
			XmlElement result = new XmlElement();
			result.setName(source.getLocalName());
			result.setPrefix(source.getPrefix());
			result.setNamespaceUri(source.getNamespaceURI());
			NamedNodeMap attributes = source.getAttributes();
			for(int index = 0; index < attributes.getLength(); index++)
			{
				Attr attr = (Attr) attributes.item(index);
				result.getAttributes().add(XmlAttributeParser.parse(attr));
			}
			XmlNodeParser parser = new XmlNodeParser(transform);
			parser.parseChildren(source, result);
			return result;
		}
	}
}
