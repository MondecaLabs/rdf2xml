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
import rocfox.rdf2xml.select.dom.SelectVariable;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.If;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.transformation.processor.variable.Variable;
import rocfox.rdf2xml.transformation.processor.variable.VariableLiteral;
import rocfox.rdf2xml.transformation.processor.variable.VariableNull;
import rocfox.rdf2xml.transformation.processor.variable.VariableResource;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class IfProcessor
{
	private If input;
	
	private Model model;
	
	public IfProcessor(Model model, If input)
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
		if(testSucceed(context, input.getTest()))
		{
			NodesProcessor processor = new NodesProcessor(model, input.getChildren());
			processor.transform(writer, context);
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(testSucceed(context, input.getTest()))
		{
			NodesProcessor processor = new NodesProcessor(model, input.getChildren());
			return processor.transformLiteral(context);
		}
		return null;
	}
	
	static boolean testSucceed(TransformContext context, SelectVariable test)
	{
		//System.out.println("IF testSucceed? enter");
		if(StringUtility.isNullOrEmpty(test.getName()))
		{
			throw new TransformException("The test expression can not be empty");
		}
		else
		{
			if(context.getVariables().hasVariable(test.getName()))
			{
				//System.out.println("a variable 1 : "+test.getName());
				Variable variable = context.getVariables().getValue(test.getName());
				if(variable instanceof VariableLiteral)
				{
					//System.out.println("a VariableLiteral 2 : "+test.getName());
					VariableLiteral variableLiteral = (VariableLiteral)variable;
					return !StringUtility.isNullOrEmpty(variableLiteral.getValue());
				}
				else if(variable instanceof VariableResource)
				{
					//System.out.println("a VariableResource 3 : "+test.getName());
					VariableResource variableResource = (VariableResource)variable;
					return variableResource.getValue() != null;
				}
				else if(variable instanceof VariableNull)
				{
					//System.out.println("a VariableNull 4 : "+test.getName());
					return false;
				}
				else
				{
					throw new TransformException("Unsupported variable type.");
				}
			}
			else
			{
				//throw new TransformException("The variable '" + test.getName() + "' does not exist in this context.");
				return false;//Martin : l'exécution des OPTIONAL en version Jena pour SPARQL 1.1 ne donnent plus accès a la variable non bindée. 
				//a la place de retourner une erreur, retourner false si on ne trouve pas la variable!
			}
		}
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		if(testSucceed(context, input.getTest()))
		{
			NodesProcessor processor = new NodesProcessor(model, input.getChildren());
			return processor.transformResource(context);
		}
		return null;
	}
}
