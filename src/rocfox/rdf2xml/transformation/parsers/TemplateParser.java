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

package rocfox.rdf2xml.transformation.parsers;

import org.w3c.dom.Element;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.Match;
import rocfox.rdf2xml.transformation.dom.Param;
import rocfox.rdf2xml.transformation.dom.Template;
import rocfox.rdf2xml.transformation.dom.TemplateOutput;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.TransformOutput;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class TemplateParser extends Parser
{
	public TemplateParser(Transform transform)
	{
		if(transform == null)
		{
			throw ExceptionBuilder.createNullArgumentException("transform");
		}
		this.transform = transform;
	}
	
	private Transform transform;
	
	public Transform getTransform()
	{
		return transform;
	}
	
	@Override
	public boolean isElement(Element source)
	{
		return super.isElement(source) && Template.ElementName.equals(source.getLocalName());
	}
	
	public Template parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		Template template = new Template();
		String name = source.getAttribute(Template.AttributeNameName);
		if(StringUtility.isNullOrEmpty(name))
		{
			throw new TransformException(String.format("The element '%s' requires an attribute '%s'.", Template.ElementName, Template.AttributeNameName));
		}
		template.setName(source.getAttribute(Template.AttributeNameName));
		parseParam(template, source);
		parseMatch(template, source);
		parseOutput(template, source);
		return template;
	}
	
	private void parseParam(Template template, Element templateElement)
	{
		for(Element element : XmlReader.selectChildElements(templateElement, Param.ElementName))
		{
			ParamParser parser = new ParamParser(transform);
			template.getParams().add(parser.parse(element));
		}
	}
	
	private void parseMatch(Template template, Element templateElement)
	{
		Element element = XmlReader.selectSingleChildElement(templateElement, Match.ElementName);
		if(element != null)
		{
			MatchParser parser = new MatchParser();
			template.setMatch(parser.parse(element));
		}
	}
	
	private void parseOutput(Template template, Element templateElement)
	{
		Element element = XmlReader.selectSingleChildElement(templateElement, TransformOutput.ElementName);
		if(element == null)
		{
			throw new TransformException("A '" + Template.ElementName + "' element should contain one '" + TemplateOutput.ElementName + "' element.");
		}
		else
		{
			TemplateOutputParser parser = new TemplateOutputParser(transform);
			template.setOutput(parser.parse(element));
		}
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedAttributes().add(Template.AttributeNameName);
		options.getAllowedChildElements().add(Param.ElementName);
		options.getAllowedChildElements().add(Match.ElementName);
		options.getAllowedChildElements().add(TransformOutput.ElementName);
		options.validate(source);
	}
}
