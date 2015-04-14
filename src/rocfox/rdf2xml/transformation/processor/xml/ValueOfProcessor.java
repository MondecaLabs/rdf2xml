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
import rocfox.rdf2xml.select.dom.SelectExpression;
import rocfox.rdf2xml.select.processor.SelectExpressionProcessor;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.ValueOf;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;

/**
 *
 * @author Nicolas
 */
public class ValueOfProcessor
{
	private ValueOf input;
	
	private Model model;
	
	public ValueOfProcessor(Model model, ValueOf input)
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
		SelectExpression expression = input.getSelect();
		SelectExpressionProcessor processor = new SelectExpressionProcessor(model, expression);
		processor.transform(writer, context);
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		SelectExpression expression = input.getSelect();
		SelectExpressionProcessor processor = new SelectExpressionProcessor(model, expression);
		return processor.transformLiteral(context);
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		SelectExpression expression = input.getSelect();
		SelectExpressionProcessor processor = new SelectExpressionProcessor(model, expression);
		return processor.transformResource(context);
	}
}
