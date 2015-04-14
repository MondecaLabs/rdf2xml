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

import org.w3c.dom.Attr;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;

/**
 *
 * @author Nicolas
 */
public class XmlAttributeParser
{	
	public static XmlAttribute parse(Attr source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		XmlAttribute result = new XmlAttribute();
		result.setName(source.getLocalName());
		result.setPrefix(source.getPrefix());
		result.setNamespaceUri(source.getNamespaceURI());
		result.setValue(source.getValue());
		return result;
	}
}
