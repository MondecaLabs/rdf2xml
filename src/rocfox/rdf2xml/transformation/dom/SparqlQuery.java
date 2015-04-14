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

package rocfox.rdf2xml.transformation.dom;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.transformation.processor.variable.Variable;
import rocfox.rdf2xml.transformation.processor.variable.VariableLiteral;
import rocfox.rdf2xml.transformation.processor.variable.VariableNull;
import rocfox.rdf2xml.transformation.processor.variable.VariableResource;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlNamespaceList;

/**
 *
 * @author Nicolas
 */
public class SparqlQuery
{
	public SparqlQuery(String query)
	{
		if(StringUtility.isNullOrEmpty(query))
		{
			throw ExceptionBuilder.createNullArgumentException("query");
		}
		this.query = query;
	}
	
	private String query;
	
	public String getQuery()
	{
		return query;
	}
	
	public String getQuery(TransformContext context)
	{
		String result = query;
		for(String variableName : getVariables())
		{
			if(!context.getVariables().hasVariable(variableName))
			{
				throw new TransformException(String.format("The variable '%s' does not exist in this context.", variableName));
			}
			StringBuilder regex = new StringBuilder();
			regex.append("\\$");
			regex.append(variableName);
			Variable value = context.getVariables().getValue(variableName);
			if(value instanceof VariableLiteral)
			{
				VariableLiteral variableLiteral = (VariableLiteral)value;
				result = result.replaceAll(regex.toString(), String.format("\"%s\"", variableLiteral.getValue()));
			}
			else if(value instanceof VariableResource)
			{
				VariableResource variableResource = (VariableResource)value;
				result = result.replaceAll(regex.toString(), String.format("<%s>", variableResource.getValue().getURI()));
			}
			else if(value instanceof VariableNull)
			{
				throw new TransformException(String.format("The variable '%s' has a no value.", value.getName()));
			}
			else
			{
				throw new TransformException("Unsuported variable type.");
			}
		}
		return result;
	}
	
	private XmlNamespaceList namespaces = null;
	
	private XmlNamespaceList getNamespaces()
	{
		if(namespaces == null)
		{
			namespaces = new XmlNamespaceList();
			prepareNamespaces();
		}
		return namespaces;
	}
	
	private ArrayList<String> variables = null;
	
	private ArrayList<String> getVariables()
	{
		if(variables == null)
		{
			variables = new ArrayList<String>();
			prepareVariables();
		}
		return variables;
	}
	
	private void prepareVariables()
	{
		String reg = "\\$(\\w+)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher =  pattern.matcher(query);
		while (matcher.find())
		{
			final int nameIndex = 1;
			variables.add(matcher.group(nameIndex));
		}
	}
	
	private void prepareNamespaces()
	{
		String reg = "PREFIX\\s*+(\\w+)?\\s*+:\\s*+<(\\S+)>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher =  pattern.matcher(query);
		while (matcher.find())
		{
			final int prefixIndex = 1;
			final int uriIndex = 2;
			String prefix = matcher.group(prefixIndex);
			String uri = matcher.group(uriIndex);
			namespaces.add(prefix, uri);
		}
	}
	
	public ArrayList<String> getUndeclaredPrefixes()
	{
		ArrayList<String> result = new ArrayList<String>();
		
		String reg = "\\s(\\w+):";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher =  pattern.matcher(query);
		while (matcher.find())
		{
			final int prefixIndex = 1;
			String prefix = matcher.group(prefixIndex);
			if(prefix == null)
			{
				prefix = "";
			}
			if(!result.contains(prefix))
			{
				result.add(prefix);
			}
		}
		
		return result;
	}
	
	public boolean containsPrefix(String prefix)
	{
		if(StringUtility.isNullOrEmpty(prefix))
		{
			prefix = "";
		}
		return getNamespaces().containsPrefix(prefix);
	}
	
	public boolean containsVariable(String variableName)
	{
		if(StringUtility.isNullOrEmpty(variableName))
		{
			throw ExceptionBuilder.createNullArgumentException("variableName");
		}
		return getVariables().contains(variableName);
	}
	
	public void addPrefix(String prefix, String namespaceUri)
	{
		if(prefix == null)
		{
			prefix = "";
		}
		if(namespaceUri == null)
		{
			namespaceUri = "";
		}
		if(getNamespaces().containsPrefix(prefix))
		{
			throw new TransformException(String.format("The prefix '%s' is already defined in the query.", prefix));
		}
		StringBuilder result = new StringBuilder();
		result.append(String.format("PREFIX %s: <%s>\n", prefix, namespaceUri));
		result.append(query);
		query = result.toString();
		getNamespaces().add(prefix, namespaceUri);
	}
}
