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

import com.hp.hpl.jena.rdf.model.Resource;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.select.dom.SelectVariable;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.transformation.processor.variable.Variable;
import rocfox.rdf2xml.transformation.processor.variable.VariableLiteral;
import rocfox.rdf2xml.transformation.processor.variable.VariableNull;
import rocfox.rdf2xml.transformation.processor.variable.VariableResource;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
/**
 *
 * @author Nicolas
 */
public class SelectVariableProcessor
{
	public SelectVariableProcessor(SelectVariable input)
	{
		if(input == null)
		{
			throw ExceptionBuilder.createNullArgumentException("input");
		}
		this.input = input;
	}
	
	private SelectVariable input;
	
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
		String value = transformLiteral(context);
		if (!StringUtility.isNullOrEmpty(value))
		{
			writer.writeText(value);
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		Variable variable = context.getVariables().getValue(input.getName());
		if(variable instanceof VariableLiteral)
		{
			VariableLiteral variableLiteral = (VariableLiteral)variable;
			String value = variableLiteral.getValue();
			if (!StringUtility.isNullOrEmpty(value))
			{
				return value;
			}
		}
		else if(variable instanceof VariableResource)
		{
			rocfox.rdf2xml.transformation.processor.variable.VariableResource variableResource = (rocfox.rdf2xml.transformation.processor.variable.VariableResource) variable;
			return variableResource.getValue().getURI();
		}
		else if(variable instanceof VariableNull)
		{
			//do nothing
		}
		else
		{
			throw new TransformException("Unsupported variable type");
		}
		return null;
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		Variable variable = context.getVariables().getValue(input.getName());
		if(variable instanceof VariableLiteral)
		{
			throw new TransformException("Literal found instead of resource.");
		}
		else if(variable instanceof VariableResource)
		{
			VariableResource variableResource = (VariableResource) variable;
			return variableResource.getValue();
		}
		else if(variable instanceof VariableNull)
		{
			return null;
		}
		else
		{
			throw new TransformException("Unsupported variable type");
		}
	}
}
