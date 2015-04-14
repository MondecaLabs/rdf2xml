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
import com.hp.hpl.jena.rdf.model.Resource;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.CallTemplate;
import rocfox.rdf2xml.transformation.dom.Choose;
import rocfox.rdf2xml.transformation.dom.Comment;
import rocfox.rdf2xml.transformation.dom.If;
import rocfox.rdf2xml.transformation.dom.RtlText;
import rocfox.rdf2xml.transformation.dom.TransformNode;
import rocfox.rdf2xml.transformation.dom.ValueOf;
import rocfox.rdf2xml.transformation.dom.XmlElement;
import rocfox.rdf2xml.transformation.dom.XmlText;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;

/**
 *
 * @author Nicolas
 */
public class NodeProcessor
{
	private TransformNode input;
	
	private Model model;
	
	public NodeProcessor(Model model, TransformNode input)
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
		if(writer == null)
		{
			throw ExceptionBuilder.createNullArgumentException("writer");
		}
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(input instanceof XmlText)
		{
			XmlTextProcessor processor = new XmlTextProcessor(model, (XmlText)input);
			processor.transform(writer, context);
		}
		else if(input instanceof ValueOf)
		{
			ValueOfProcessor processor = new ValueOfProcessor(model, (ValueOf)input);
			processor.transform(writer, context);
		}
		else if(input instanceof XmlElement)
		{
			XmlElementProcessor processor = new XmlElementProcessor(model, (XmlElement)input);
			processor.transform(writer, context);
		}
		else if(input instanceof CallTemplate)
		{
			CallTemplateProcessor processor = new CallTemplateProcessor(model, (CallTemplate)input);
			processor.transform(writer, context);
		}
		else if(input instanceof Comment)
		{
			CommentProcessor processor = new CommentProcessor(model, (Comment)input);
			processor.transform(writer, context);
		}
		else if(input instanceof RtlText)
		{
			RtlTextProcessor processor = new RtlTextProcessor(model, (RtlText)input);
			processor.transform(writer, context);
		}
		else if(input instanceof If)
		{
			IfProcessor processor = new IfProcessor(model, (If)input);
			processor.transform(writer, context);
		}
		else if(input instanceof Choose)
		{
			ChooseProcessor processor = new ChooseProcessor(model, (Choose)input);
			processor.transform(writer, context);
		}
		else
		{
			throw ExceptionBuilder.createUnsupportedOperationException("The support for " + input.getClass().getName() + " has not been implemented.");
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(input instanceof XmlText)
		{
			XmlTextProcessor processor = new XmlTextProcessor(model, (XmlText)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof ValueOf)
		{
			ValueOfProcessor processor = new ValueOfProcessor(model, (ValueOf)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof XmlElement)
		{
			XmlElement xmlElement = (XmlElement) input;
			throw new TransformException("Incorrect value string: " + xmlElement.getNamespaceUri() + ':' + xmlElement.getName());
		}
		else if(input instanceof CallTemplate)
		{
			CallTemplateProcessor processor = new CallTemplateProcessor(model, (CallTemplate)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof Comment)
		{
			throw new TransformException("Incorrect value string: comment");
		}
		else if(input instanceof RtlText)
		{
			RtlTextProcessor processor = new RtlTextProcessor(model, (RtlText)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof If)
		{
			IfProcessor processor = new IfProcessor(model, (If)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof Choose)
		{
			ChooseProcessor processor = new ChooseProcessor(model, (Choose)input);
			return processor.transformLiteral(context);
		}
		else
		{
			throw ExceptionBuilder.createUnsupportedOperationException("The support for " + input.getClass().getName() + " has not been implemented.");
		}
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(input instanceof XmlText)
		{
			throw new TransformException("An XML text node cannot be a resource.");
		}
		else if(input instanceof ValueOf)
		{
			ValueOfProcessor processor = new ValueOfProcessor(model, (ValueOf)input);
			return processor.transformResource(context);
		}
		else if(input instanceof XmlElement)
		{
			throw new TransformException("An XML element cannot be a resource.");
		}
		else if(input instanceof CallTemplate)
		{
			CallTemplateProcessor processor = new CallTemplateProcessor(model, (CallTemplate)input);
			return processor.transformResource(context);
		}
		else if(input instanceof Comment)
		{
			throw new TransformException("An XML comment cannot be a resource.");
		}
		else if(input instanceof RtlText)
		{
			throw new TransformException("An XML text node cannot be a resource.");
		}
		else if(input instanceof If)
		{
			IfProcessor processor = new IfProcessor(model, (If)input);
			return processor.transformResource(context);
		}
		else if(input instanceof Choose)
		{
			ChooseProcessor processor = new ChooseProcessor(model, (Choose)input);
			return processor.transformResource(context);
		}
		else
		{
			throw ExceptionBuilder.createUnsupportedOperationException("The support for " + input.getClass().getName() + " has not been implemented.");
		}
	}
}
