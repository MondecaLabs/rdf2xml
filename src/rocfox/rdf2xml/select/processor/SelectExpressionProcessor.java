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

package rocfox.rdf2xml.select.processor;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.select.dom.SelectExpression;
import rocfox.rdf2xml.select.dom.SelectFunction;
import rocfox.rdf2xml.select.dom.SelectVariable;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.xml.XmlWriter;

/**
 *
 * @author Nicolas
 */
public class SelectExpressionProcessor
{
	public SelectExpressionProcessor(Model model, SelectExpression input)
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
	
	private SelectExpression input;
	
	private Model model;
	
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
		if(input instanceof SelectFunction)
		{
			SelectFunctionProcessor processor = new SelectFunctionProcessor(model, (SelectFunction)input);
			processor.transform(writer, context);
		}
		else if(input instanceof SelectVariable)
		{
			SelectVariableProcessor processor = new SelectVariableProcessor((SelectVariable)input);
			processor.transform(writer, context);
		}
		else
		{
			throw new TransformException("Unsupported type of select expression.");
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(input instanceof SelectFunction)
		{
			SelectFunctionProcessor processor = new SelectFunctionProcessor(model, (SelectFunction)input);
			return processor.transformLiteral(context);
		}
		else if(input instanceof SelectVariable)
		{
			SelectVariableProcessor processor = new SelectVariableProcessor((SelectVariable)input);
			return processor.transformLiteral(context);
		}
		else
		{
			throw new TransformException("Unsupported type of select expression.");
		}
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(input instanceof SelectFunction)
		{
			SelectFunctionProcessor processor = new SelectFunctionProcessor(model, (SelectFunction)input);
			return processor.transformResource(context);
		}
		else if(input instanceof SelectVariable)
		{
			SelectVariableProcessor processor = new SelectVariableProcessor((SelectVariable)input);
			return processor.transformResource(context);
		}
		else
		{
			throw new TransformException("Unsupported type of select expression.");
		}
	}
}
