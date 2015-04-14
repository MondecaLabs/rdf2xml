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
import rocfox.rdf2xml.select.parser.SelectParser;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.ValueOf;
import rocfox.rdf2xml.transformation.dom.WithParam;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class WithParamParser extends Parser
{
	public WithParamParser(Transform transform)
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
		return super.isElement(source) && WithParam.ElementName.equals(source.getLocalName());
	}
	
	public WithParam parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		WithParam result = new WithParam();
		String name = XmlReader.selectAttribute(source, WithParam.AttributeNameName);
		if(StringUtility.isNullOrEmpty(name))
		{
			throw new TransformException(String.format("An element '%s' must always have a '%s' attribute.", WithParam.ElementName, WithParam.AttributeNameName));
		}
		result.setName(name);
		String select = XmlReader.selectAttribute(source, WithParam.AttributeNameSelect);
		boolean hasSelectAttribute = !StringUtility.isNullOrEmpty(select);
		boolean hasChildren = source.getChildNodes().getLength() > 0;
		if(hasSelectAttribute)
		{
			if(hasChildren)
			{
				throw new TransformException(String.format("An element '%s' cannot have child nodes if it has got an '%s' attribute.", WithParam.ElementName, WithParam.AttributeNameSelect));
			}
			else
			{
				//select attribute
				SelectParser selectParser = new SelectParser(select);
				SelectExpression selectExpression = selectParser.parse();
				ValueOf valueOf = new ValueOf();
				valueOf.setSelect(selectExpression);
				result.getChildren().add(valueOf);
			}
		}
		else
		{
			if(hasChildren)
			{
				//child node value of
				XmlNodeParser parser = new XmlNodeParser(transform);
				parser.parseChildren(source, result);
			}
		}
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(true);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedAttributes().add(WithParam.AttributeNameName);
		options.getAllowedAttributes().add(WithParam.AttributeNameSelect);
		options.getAllowedChildElements().add(ValueOf.ElementName);
		options.validate(source);
	}
}
