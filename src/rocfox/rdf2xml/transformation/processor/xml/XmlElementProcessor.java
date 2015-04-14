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

package rocfox.rdf2xml.transformation.processor.xml;

import com.hp.hpl.jena.rdf.model.Model;
import java.util.ArrayList;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.TransformNode;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;
import rocfox.rdf2xml.transformation.dom.XmlElement;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XmlElementProcessor
{
	private XmlElement input;
	
	private Model model;
	
	public XmlElementProcessor(Model model, XmlElement input)
	{
		if(input == null)
		{
			throw ExceptionBuilder.createNullArgumentException("input");
		}
		this.input = input;
		if(model == null)
		{
			throw ExceptionBuilder.createNullArgumentException("model");
		}
		this.model = model;
	}
	
	public void transform(XmlWriter writer, TransformContext context)
	{
		//Arguments
		if(writer == null)
		{
			throw ExceptionBuilder.createNullArgumentException("writer");
		}
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		//Get children
		ArrayList<TransformNode> children = input.getChildren();
		boolean isEmpty = children.isEmpty();
//		TransformContext childContext = context.clone();
//		//namespace
//		String prefix = StringUtility.ensureNotNull(input.getPrefix());
//		String namespaceUri = StringUtility.ensureNotNull(input.getNamespaceUri());
//		if(StringUtility.isNullOrEmpty(namespaceUri) && !StringUtility.isNullOrEmpty(prefix))
//		{
//			namespaceUri =  StringUtility.ensureNotNull(childContext.getNamespaces().GetUri(prefix));
//		}
//		if(StringUtility.isNullOrEmpty(namespaceUri) && !StringUtility.isNullOrEmpty(prefix))
//		{
//			//What we are trying to output here is <ns:node xmlsn:ns=""/>.
//			//This is an invalid case and will (in theory) never happen.
//			//As a fallback option, I decide to output this node
//			//with no prefix.
//			//As a concequence this node will be defined
//			//(possibility 1) under the default namespace of the docuent
//			//(possibility 2) under no namespace if there is no default namespace 
//			//The possibility 1 would be the invalid case...
//			prefix = "";
//		}
//		if(!StringUtility.isNullOrEmpty(namespaceUri))
//		{
//			prefix = childContext.getNamespaces().ensureNamespace(prefix, namespaceUri);
//		}
		//start creating the node
		if(isEmpty)
		{
			writer.writeEmptyElement(input.getPrefix(), input.getName(), input.getNamespaceUri());
		}
		else
		{
			writer.writeStartElement(input.getPrefix(), input.getName(), input.getNamespaceUri());
		}
		//recurse through the children
		for(XmlAttribute child : input.getAttributes())
		{
			XmlAttributeProcessor processor = new XmlAttributeProcessor(model, child);
			processor.transform(writer, context);
		}
		for(TransformNode child : children)
		{
			NodeProcessor processor = new NodeProcessor(model, child);
			processor.transform(writer, context);
		}
		//close the node
		if(!isEmpty)
		{
			writer.writeEndElement();
		}
	}
}
