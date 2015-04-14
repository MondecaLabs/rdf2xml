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

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.Iterator;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.CallTemplate;
import rocfox.rdf2xml.transformation.dom.Match;
import rocfox.rdf2xml.transformation.dom.Param;
import rocfox.rdf2xml.transformation.dom.ParamLiteral;
import rocfox.rdf2xml.transformation.dom.ParamResource;
import rocfox.rdf2xml.transformation.dom.SparqlQuery;
import rocfox.rdf2xml.transformation.dom.Template;
import rocfox.rdf2xml.transformation.dom.WithParam;
import rocfox.rdf2xml.transformation.dom.WithParamList;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class CallTemplateProcessor
{
	private CallTemplate input;
	
	private Model model;
	
	public CallTemplateProcessor(Model model, CallTemplate input)
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
		TransformContext templateCommonContext = prepareContext(context, input.getWithParams());
		Template template = getTemplate();
		ResultSet results = prepareResultSet(templateCommonContext);
		if(results == null)
		{
			TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
			processor.transform(writer, templateCommonContext);
		}
		else
		{
			while (results.hasNext())
			{
				TransformContext templateContext = templateCommonContext.clone();
				prepareContext(templateContext, results.nextSolution());
				TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
				processor.transform(writer, templateContext);
			}
		}
	}
	
	public String transformLiteral(TransformContext context)
	{
		TransformContext templateCommonContext = prepareContext(context, input.getWithParams());
		Template template = getTemplate();
		ResultSet results = prepareResultSet(templateCommonContext);
		StringBuilder result = new StringBuilder();
		if(results == null)
		{
			TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
			String value = processor.transformLiteral(templateCommonContext);
			if(!StringUtility.isNullOrEmpty(value))
			{
				result.append(value);
			}
		}
		else
		{
			while (results.hasNext())
			{
				TransformContext templateContext = templateCommonContext.clone();
				prepareContext(templateContext, results.nextSolution());
				TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
				result.append(processor.transformLiteral(templateContext));
			}
		}
		return result.toString();
	}
	
	public Resource transformResource(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		TransformContext templateCommonContext = prepareContext(context, input.getWithParams());
		Template template = getTemplate();
		ResultSet results = prepareResultSet(templateCommonContext);
		if(results == null)
		{
			TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
			return processor.transformResource(templateCommonContext);
		}
		else
		{
			Resource result = null;
			while (results.hasNext())
			{
				TransformContext templateContext = templateCommonContext.clone();
				prepareContext(templateContext, results.nextSolution());
				TemplateOutputProcessor processor = new TemplateOutputProcessor(model, template.getOutput());
				Resource foundResult = processor.transformResource(templateContext);
				if(result == null)
				{
					result = foundResult;
				}
				else
				{
					if(foundResult != null)
					{
						throw new TransformException("The template returned several resource URIs.");
					}
				}
			}
			return result;
		}
	}
	
	private Template getTemplate()
	{
		String templateName = input.getName();
		Template template = input.getTransform().getTemplates().get(templateName);
		if (template == null)
		{
			throw new TransformException("The template '" + templateName + "' could not be found.");
		}
		return template;
	}
	
	private ResultSet prepareResultSet(TransformContext context)
	{
		if(context == null)
		{
			throw ExceptionBuilder.createNullArgumentException("context");
		}
		String templateName = input.getName();
		Template template = input.getTransform().getTemplates().get(templateName);
		if (template == null)
		{
			throw new TransformException("The template '" + templateName + "' could not be found.");
		}
		Match match = template.getMatch();
		if(match == null)
		{
			return null;
		}
		SparqlQuery sparqlQuery = match.getContent();
		if(sparqlQuery == null)
		{
			return null;
		}
		Query query = QueryFactory.create(sparqlQuery.getQuery(context));
		QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
		return queryExecution.execSelect();
	}
	
	private TransformContext prepareContext(TransformContext currentContext, WithParamList paramList)
	{
		TransformContext result = new TransformContext();
		Template template = getTemplate();
		for(WithParam withParam : paramList)
		{
			String name = withParam.getName();
			Param param = template.getParams().get(name);
			if(template == null)
			{
				throw new TransformException(String.format("The template '%s' for not have a parameter '%s'.", input.getName(), withParam.getName()));
			}
			if(param instanceof ParamLiteral)
			{
				WithParamLiteralProcessor withParamLiteralProcessor = new WithParamLiteralProcessor(model, withParam);
				result.getVariables().addVariable(name, withParamLiteralProcessor.transformLiteral(currentContext));
			}
			else if (param instanceof ParamResource)
			{
				WithParamResourceProcessor withParamResourceProcessor = new WithParamResourceProcessor(model, withParam);
				result.getVariables().addVariable(name, withParamResourceProcessor.transformResource(currentContext));
			}
		}
		return result;
	}
	
	private void prepareContext(TransformContext templateContext, QuerySolution solution)
	{
		//System.out.println("prepareContext enter");
		Iterator iterator = solution.varNames();
		while(iterator.hasNext())
		{
			String variableName = (String)iterator.next();
			//System.out.println("prepareContext variableName : "+variableName);
			RDFNode rdfNode = solution.get(variableName);
			if(rdfNode == null)
			{
				templateContext.getVariables().addVariable(variableName);
			}
			else if (rdfNode instanceof Literal)
			{
				Literal literal = (Literal)rdfNode;
				templateContext.getVariables().addVariable(variableName, literal.getString());
			}
			else if (rdfNode instanceof Resource)
			{
				Resource resource = (Resource)rdfNode;
				templateContext.getVariables().addVariable(variableName, resource);
			}
			else
			{
				throw new TransformException("Invalid type of resource.");
			}
		}
	}
}
