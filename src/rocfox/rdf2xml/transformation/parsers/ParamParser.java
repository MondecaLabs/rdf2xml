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
import rocfox.rdf2xml.transformation.dom.Param;
import rocfox.rdf2xml.transformation.dom.ParamLiteral;
import rocfox.rdf2xml.transformation.dom.ParamResource;
import rocfox.rdf2xml.transformation.dom.ParamXmlLiteral;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.VariableType;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class ParamParser extends Parser
{
	public ParamParser(Transform transform)
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
		return super.isElement(source) && Param.ElementName.equals(source.getLocalName());
	}
	
	public Param parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		String stringType = XmlReader.selectAttribute(source, Param.AttributeNameType);
		if(StringUtility.isNullOrEmpty(stringType))
		{
			throw new TransformException(String.format("The element '%s' must have a '%s' attribute.", Param.ElementName, Param.AttributeNameType));
		}
		Param result;
		switch(VariableType.valueOf(stringType))
		{
		case Literal:
			result = new ParamLiteral();
			break;
		case Resource:
			result = new ParamResource();
			break;
		case XmlLiteral:
			result = new ParamXmlLiteral();
			break;
		default:
			throw new TransformException(String.format("Unsuported parameter type %s.", stringType));
		}
		String name = XmlReader.selectAttribute(source, Param.AttributeNameName);
		if(StringUtility.isNullOrEmpty(name))
		{
			throw new TransformException(String.format("The element '%s' must have a '%s' attribute.", Param.ElementName, Param.AttributeNameName));
		}
		result.setName(name);
		XmlNodeParser parser = new XmlNodeParser(transform);
		parser.parseChildren(source, result);
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedAttributes().add(Param.AttributeNameName);
		options.getAllowedAttributes().add(Param.AttributeNameType);
		options.validate(source);
	}
}
