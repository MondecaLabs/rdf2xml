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
import rocfox.rdf2xml.transformation.dom.CallTemplate;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.WithParam;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class CallTemplateParser extends Parser
{
	public CallTemplateParser(Transform transform)
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
		return super.isElement(source) && CallTemplate.ElementName.equals(source.getLocalName());
	}
	
	public CallTemplate parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		CallTemplate result = new CallTemplate(transform);
		String name = XmlReader.selectAttribute(source, CallTemplate.AttributeNameName);
		if(StringUtility.isNullOrEmpty(name))
		{
			throw new TransformException(String.format("The element '%s' must have an attribute '%s'.", CallTemplate.ElementName, CallTemplate.AttributeNameName));
		}
		result.setName(name);
		for(Element element : XmlReader.selectChildElements(source, WithParam.ElementName))
		{
			WithParamParser withParamParser = new WithParamParser(transform);
			result.getWithParams().add(withParamParser.parse(element));
		}
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedChildElements().add(WithParam.ElementName);
		options.getAllowedAttributes().add(CallTemplate.AttributeNameName);
		options.validate(source);
	}
}
