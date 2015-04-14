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
import rocfox.rdf2xml.transformation.dom.TemplateOutput;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;

/**
 *
 * @author Nicolas
 */
public class TemplateOutputParser extends Parser
{
	public TemplateOutputParser(Transform transform)
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
		return super.isElement(source) && TemplateOutput.ElementName.equals(source.getLocalName());
	}
	
	public TemplateOutput parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		TemplateOutput result = new TemplateOutput();
		XmlNodeParser parser = new XmlNodeParser(transform);
		parser.parseChildren(source, result);
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(true);
		options.setAllowOtherAttributes(true);
		options.setAllowOtherChildElements(true);
		options.validate(source);
	}
}
