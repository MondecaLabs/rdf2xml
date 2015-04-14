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
import java.util.ArrayList;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.TransformNode;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class NodesProcessor
{
	private ArrayList<TransformNode> input;
	
	private Model model;
	
	public NodesProcessor(Model model, ArrayList<TransformNode> input)
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
		for(TransformNode node : input)
		{
			NodeProcessor processor = new NodeProcessor(model, node);
			processor.transform(writer, context);
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		StringBuilder builder = new StringBuilder();
		for(TransformNode node : input)
		{
			NodeProcessor processor = new NodeProcessor(model, node);
			String value = processor.transformLiteral(context);
			if(!StringUtility.isNullOrEmpty(value))
			{
				builder.append(value);
			}
		}
		return builder.toString();
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		Resource result = null;
		for(TransformNode node : input)
		{
			NodeProcessor processor = new NodeProcessor(model, node);
			Resource tempResult = processor.transformResource(context);
			if(result == null)
			{
				result = tempResult;
			}
			else
			{
				throw new TransformException("Several resource URI were found.");
			}
		}
		return result;
	}
}
