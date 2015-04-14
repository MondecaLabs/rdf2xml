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
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.Choose;
import rocfox.rdf2xml.transformation.dom.Otherwise;
import rocfox.rdf2xml.transformation.dom.When;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;

/**
 *
 * @author Nicolas
 */
public class ChooseProcessor
{
	private Choose input;
	
	private Model model;
	
	public ChooseProcessor(Model model, Choose input)
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
		boolean stop = false;
		for(When when : input.getWhen())
		{
			if(stop)
			{
				break;
			}
			if(IfProcessor.testSucceed(context, when.getTest()))
			{
				stop = true;
				NodesProcessor processor = new NodesProcessor(model, when.getChildren());
				processor.transform(writer, context);
			}
		}
		Otherwise otherwise = input.getOtherwise();
		if(!stop && otherwise != null)
		{
			NodesProcessor processor = new NodesProcessor(model, otherwise.getChildren());
			processor.transform(writer, context);
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		boolean stop = false;
		for(When when : input.getWhen())
		{
			if(IfProcessor.testSucceed(context, when.getTest()))
			{
				NodesProcessor processor = new NodesProcessor(model, when.getChildren());
				return processor.transformLiteral(context);
			}
		}
		Otherwise otherwise = input.getOtherwise();
		if(!stop && otherwise != null)
		{
			NodesProcessor processor = new NodesProcessor(model, otherwise.getChildren());
			return processor.transformLiteral(context);
		}
		return null;
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		for(When when : input.getWhen())
		{
			if(IfProcessor.testSucceed(context, when.getTest()))
			{
				NodesProcessor processor = new NodesProcessor(model, when.getChildren());
				return processor.transformResource(context);
			}
		}
		Otherwise otherwise = input.getOtherwise();
		NodesProcessor processor = new NodesProcessor(model, otherwise.getChildren());
		return processor.transformResource(context);
	}
}
