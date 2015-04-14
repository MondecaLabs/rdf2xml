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
import rocfox.rdf2xml.select.dom.SelectExpression;
import rocfox.rdf2xml.select.dom.SelectVariable;
import rocfox.rdf2xml.select.parser.SelectParser;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.When;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class WhenParser extends Parser
{
	public WhenParser(Transform transform)
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
		return super.isElement(source) && When.ElementName.equals(source.getLocalName());
	}
	
	public When parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		When result = new When();
		String test = XmlReader.selectAttribute(source, When.AttributeNameTest);
		if(StringUtility.isNullOrEmpty(test))
		{
			throw new TransformException(String.format("The element '%s' must contain a '%s' attribute.", When.ElementName, When.AttributeNameTest));
		}
		SelectParser selectParser = new SelectParser(test);
		SelectExpression expression = selectParser.parse();
		if(!(expression instanceof SelectVariable))
		{
			throw new TransformException(String.format("The use of the expression '%s' is not allowed in this location.", expression.getName()));
		}
		result.setTest((SelectVariable)expression);
		XmlNodeParser parser = new XmlNodeParser(transform);
		parser.parseChildren(source, result);
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(true);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(true);
		options.getAllowedAttributes().add(When.AttributeNameTest);
		options.validate(source);
	}
}