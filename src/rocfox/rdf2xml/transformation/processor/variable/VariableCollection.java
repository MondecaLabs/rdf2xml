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
package rocfox.rdf2xml.transformation.processor.variable;

import com.hp.hpl.jena.rdf.model.Resource;
import java.util.TreeMap;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 * Manages the list of variables.
 * 
 * @author Nicolas
 */
public class VariableCollection
{
	/**
	 * Thei internal list of variable
	 */
	private TreeMap<String, Variable> variables = new TreeMap<String, Variable>();
	
	/**
	 * Adds a variable to the current context
	 * with a null value.
	 * 
	 * @param name The name of the variable
	 */
	public void addVariable(String name)
	{
		if(variables.containsKey(name))
		{
			variables.remove(name);
		}
		variables.put(name, new VariableNull(name));
	}

	/**
	 * Adds a variable to the current context with a specific value.
	 * 
	 * @param name The name of the variable
	 * @param value The value of the variable.
	 */
	public void addVariable(String name, String value)
	{
		if(variables.containsKey(name))
		{
			variables.remove(name);
		}
		variables.put(name, new VariableLiteral(name, value));
	}

	/**
	 * Add a variable to the current context
	 * with a resource value.
	 * 
	 * @param name The name of the variable
	 * @param value The value of the variable
	 */
	public void addVariable(String name, Resource value)
	{
		if(variables.containsKey(name))
		{
			variables.remove(name);
		}
		variables.put(name, new VariableResource(name, value));
	}
	
	/**
	 * Gets a variable from its name.
	 * @param name The name of the variable
	 * @return The variable stored in the current context.
	 */
	public Variable getValue(String name)
	{
		if(StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		if(variables.containsKey(name))
		{
			return variables.get(name);
		}
		else
		{
			throw new TransformException("The variable '" + name + "' does not exist in this context.");
		}
	}
	
	/**
	 * Indicates whether a variable has a null value.
	 * 
	 * @param name The name of the variable.
	 * @return A boolean indicating whether the variable is null.
	 */
	public boolean hasNullValue(String name)
	{
		Variable result = getValue(name);
		if(result instanceof VariableNull)
		{
			return true;
		}
		else
		{
			throw new TransformException("The variable '" + name + "' is not a Literal.");
		}
	}
	
	/**
	 * Returns the literal value of a variable.
	 * 
	 * @param name The name of the variable
	 * @return The literal value of the variable
	 */
	public String getLiteralValue(String name)
	{
		Variable result = getValue(name);
		if(result instanceof VariableLiteral)
		{
			return ((VariableLiteral)result).getValue();
		}
		else if(result instanceof VariableResource)
		{
			return ((VariableResource)result).getValue().getURI();
		}
		else if(result instanceof VariableNull)
		{
			return null;
		}
		else
		{
			throw new TransformException("The variable '" + name + "' is not a Literal.");
		}
	}
	
	/**
	 * Returns the resource value of a variable in the current context.
	 * 
	 * @param name The name of the variable
	 * @return The resource value of the variable in the current context.
	 */
	public Resource getResourceValue(String name)
	{
		Variable result = getValue(name);
		if(result instanceof VariableLiteral)
		{
			return ((VariableResource)result).getValue();
		}
		else if(result instanceof VariableResource)
		{
			return ((VariableResource)result).getValue();
		}
		else if(result instanceof VariableNull)
		{
			return null;
		}
		else
		{
			throw new TransformException("The variable '" + name + "' is not a Resource.");
		}
	}
	
	/**
	 * Indicates whether a variable is present in the current context.
	 * 
	 * @param name The name of the variable
	 * @return A boolean indicating whether the variable is present in the
	 *	current context
	 */
	public boolean hasVariable(String name)
	{
		if(StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		return variables.containsKey(name);
	}
	
	/**
	 * Returns a copy of this instance.
	 * 
	 * @return A copy of this instance.
	 */
	@Override
	public VariableCollection clone()
	{
		VariableCollection result = new VariableCollection();
		result.variables = (TreeMap<String, Variable>)this.variables.clone();
		return result;
	}
}
