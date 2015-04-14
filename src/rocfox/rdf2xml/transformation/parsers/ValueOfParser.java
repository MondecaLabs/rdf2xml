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
import rocfox.rdf2xml.select.parser.SelectParser;
import rocfox.rdf2xml.transformation.dom.ValueOf;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class ValueOfParser extends Parser
{
	@Override
	public boolean isElement(Element source)
	{
		return super.isElement(source) && ValueOf.ElementName.equals(source.getLocalName());
	}
	
	public ValueOf parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		ValueOf result = new ValueOf();
		String select = XmlReader.selectAttribute(source, ValueOf.AttributeNameSelect);
		if(StringUtility.isNullOrEmpty(select))
		{
			throw new TransformException(String.format("The element '%s' must contain an '%s' attribute.", ValueOf.ElementName, ValueOf.AttributeNameSelect));
		}
		SelectParser selectParser = new SelectParser(select);
		result.setSelect(selectParser.parse());
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedAttributes().add(ValueOf.AttributeNameSelect);
		options.validate(source);
	}
}
