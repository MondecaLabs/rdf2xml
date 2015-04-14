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

import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.select.dom.SelectVariable;
import rocfox.rdf2xml.select.processor.SelectVariableProcessor;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.xml.XmlWriter;

import com.hp.hpl.jena.rdf.model.Model;

/**
 *
 * @author Nicolas
 */
public class XmlAttributeProcessor
{
	private XmlAttribute input;
	
	private Model model;
	
	public XmlAttributeProcessor(Model model, XmlAttribute input)
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
		if (writer == null)
		{
			throw ExceptionBuilder.createNullArgumentException("writer");
		}
		if (context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
//		TransformContext childContext = context.clone();
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
		
//		System.out.println("XMLATTRIBUTE : "+input.getValue());
		if (input.getValue()!=null && input.getValue().contains("$")) {
//			System.out.println("XMLATTRIBUTE found dollar");

			SelectVariable selectVariable  = new SelectVariable();
			selectVariable.setName(input.getValue().substring(1)/*retirer $*/);
			SelectVariableProcessor processor = new SelectVariableProcessor(selectVariable);
			
			String result = processor.transformLiteral(context);
			writer.writeAttribute(input.getPrefix(), input.getName(), input.getNamespaceUri(),
					result);
		}
		else {
			writer.writeAttribute(input.getPrefix(), input.getName(), input.getNamespaceUri(), input.getValue());
		}
		
	}
}
