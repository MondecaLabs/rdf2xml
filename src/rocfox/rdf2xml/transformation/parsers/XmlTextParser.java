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

import org.w3c.dom.Text;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.transformation.dom.XmlText;
import rocfox.rdf2xml.utilities.xml.XmlReader;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XmlTextParser
{
	public static XmlText parse(Text source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		String value = XmlReader.readTextContent(source);
		if(StringUtility.isNullOrEmpty(value))
		{
			return null;
		}
		else
		{
			XmlText result = new XmlText();
			result.setContent(XmlReader.readTextContent(source));
			return result;
		}
	}
}
