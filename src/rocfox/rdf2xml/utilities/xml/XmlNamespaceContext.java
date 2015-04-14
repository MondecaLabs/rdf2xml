/*
 *  Copyright 2008 Nicolas Cochard
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package rocfox.rdf2xml.utilities.xml;

import java.util.TreeMap;
import rocfox.rdf2xml.exceptions.GenericException;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XmlNamespaceContext
{
	private TreeMap<String, String> namespaces = new TreeMap<String, String>();
	
	public void add(String prefix, String namespaceUri)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		namespaceUri = StringUtility.ensureNotNull(namespaceUri);
		if(namespaces.containsKey(prefix))
		{
			namespaces.remove(prefix);
		}
		namespaces.put(prefix, namespaceUri);
	}
	
	public boolean containsPrefix(String prefix)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		return namespaces.containsKey(prefix);
	}
	
	public boolean containsNamespaceUri(String namespaceUri)
	{
		namespaceUri = StringUtility.ensureNotNull(namespaceUri);
		return namespaces.containsValue(namespaceUri);
	}
	
	public boolean contains(String prefix, String namespaceUri)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		namespaceUri = StringUtility.ensureNotNull(namespaceUri);
		if(!namespaces.containsKey(prefix))
		{
			return false;
		}
		String definedUri = namespaces.get(prefix);
		return StringUtility.equals(definedUri, namespaceUri);
	}
	
	public String getNamespaceUri(String prefix)
	{
		if(!namespaces.containsKey(prefix))
		{
			throw new GenericException("Invalid prefix");
		}
		prefix = StringUtility.ensureNotNull(prefix);
		return namespaces.get(prefix);
	}
	
	public String getPrefix(String namespaceUri)
	{
		if(!namespaces.containsValue(namespaceUri))
		{
			throw new GenericException("Invalid uri");
		}
		namespaceUri = StringUtility.ensureNotNull(namespaceUri);
		for(String prefix : namespaces.values())
		{
			String definedUri = namespaces.get(prefix);
			if(StringUtility.equals(namespaceUri, definedUri))
			{
				return prefix;
			}
		}
		return null;
	}
	
	@Override
	public XmlNamespaceContext clone()
	{
		XmlNamespaceContext result = new XmlNamespaceContext();
		result.namespaces = (TreeMap<String, String>) namespaces.clone();
		return result;
	}

	public String createNewPrefix()
	{
		int index = 0;
		String prefix;
		do
		{
			prefix = String.format("ns%d", index);
		}
		while(namespaces.containsKey(prefix));
		return prefix;
	}
}
