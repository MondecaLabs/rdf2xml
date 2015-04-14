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
import rocfox.rdf2xml.transformation.dom.OutputType;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.TransformOutput;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class TransformOutputParser extends Parser
{
	public TransformOutputParser(Transform transform)
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
		return super.isElement(source) && TransformOutput.ElementName.equals(source.getLocalName());
	}
	
	public TransformOutput parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		TransformOutput result = new TransformOutput();
		result.setEncoding(XmlReader.selectAttribute(source,TransformOutput.AttributeNameEncoding));
		result.setType(OutputType.valueOf(XmlReader.selectAttribute(source,TransformOutput.AttributeNameType)));
		result.setVersion(XmlReader.selectAttribute(source,TransformOutput.AttributeNameVersion));
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
		options.getAllowedAttributes().add(TransformOutput.AttributeNameEncoding);
		options.getAllowedAttributes().add(TransformOutput.AttributeNameType);
		options.getAllowedAttributes().add(TransformOutput.AttributeNameVersion);
		options.validate(source);
	}
}