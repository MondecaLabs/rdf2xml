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

import java.util.ArrayList;
import org.w3c.dom.Element;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.transformation.dom.Match;
import rocfox.rdf2xml.transformation.dom.SparqlQuery;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class MatchParser extends Parser
{
	@Override
	public boolean isElement(Element source)
	{
		return super.isElement(source) && Match.ElementName.equals(source.getLocalName());
	}
	
	public Match parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		SparqlQuery query = new SparqlQuery(XmlReader.readTextContent(source));
		ArrayList<String> undeclaredPrefixes = query.getUndeclaredPrefixes();
		for(String prefix : undeclaredPrefixes)
		{
			String namespaceUri = source.lookupNamespaceURI(prefix);
			if(namespaceUri != null)
			{
				if(!query.containsPrefix(prefix))
				{
					query.addPrefix(prefix, namespaceUri);
				}
			}
		}
		Match result = new Match();
		result.setContent(query);
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(true);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.validate(source);
	}
}
