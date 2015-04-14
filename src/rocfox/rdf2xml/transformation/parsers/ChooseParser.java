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
import rocfox.rdf2xml.transformation.dom.Choose;
import rocfox.rdf2xml.transformation.dom.Otherwise;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.When;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class ChooseParser extends Parser
{
	public ChooseParser(Transform transform)
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
		return super.isElement(source) && Choose.ElementName.equals(source.getLocalName());
	}
	
	public Choose parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		Choose result = new Choose();
		for(Element element : XmlReader.selectChildElements(source, When.ElementName))
		{
			WhenParser whenParser = new WhenParser(transform);
			result.getWhen().add(whenParser.parse(element));
		}
		Element element = XmlReader.selectSingleChildElement(source, Otherwise.ElementName);
		if(element != null)
		{
			OtherwiseParser otherwiseParser = new OtherwiseParser(transform);
			result.setOtherwise(otherwiseParser.parse(element));
		}
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedChildElements().add(When.ElementName);
		options.getAllowedChildElements().add(Otherwise.ElementName);
		options.validate(source);
	}
}
