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

package rocfox.rdf2xml.utilities.xml;

import java.util.TreeMap;

/**
 *
 * @author Nicolas
 */
public class XmlNamespaceList
{
	private TreeMap<String, XmlNamespaceDetails> namespacesSortedByPrefixes = new TreeMap<String, XmlNamespaceDetails>();
	private TreeMap<String, XmlNamespaceDetails> namespacesSortedByUri = new TreeMap<String, XmlNamespaceDetails>();
	
	public void add(String prefix,String namespaceUri)
	{
		if(prefix == null)
		{
			prefix = "";
		}
		if(namespaceUri == null)
		{
			namespaceUri = "";
		}
		XmlNamespaceDetails value = new XmlNamespaceDetails(prefix, namespaceUri);
		namespacesSortedByPrefixes.put(prefix, value);
		namespacesSortedByUri.put(namespaceUri, value);
	}
	
	public boolean containsPrefix(String prefix)
	{
		if(prefix == null)
		{
			prefix = "";
		}
		return namespacesSortedByPrefixes.containsKey(prefix);
	}
	
	public boolean containsUri(String namespaceUri)
	{
		if(namespaceUri == null)
		{
			namespaceUri = "";
		}
		return namespacesSortedByUri.containsKey(namespaceUri);
	}
	
	public void removePrefix(String prefix)
	{
		XmlNamespaceDetails value = namespacesSortedByPrefixes.get(prefix);
		remove(value);
	}
	
	public void removeUri(String namespaceUri)
	{
		XmlNamespaceDetails value = namespacesSortedByUri.get(namespaceUri);
		remove(value);
	}
	
	private void remove(XmlNamespaceDetails value)
	{
		namespacesSortedByPrefixes.remove(value.getPrefix());
		namespacesSortedByUri.remove(value.getNamespaceUri());
	}
	
	public String getNamespaceUri(String prefix)
	{
		XmlNamespaceDetails value = namespacesSortedByPrefixes.get(prefix);
		if(value != null)
		{
			return value.getNamespaceUri(); 
		}
		return null;
	}
	
	public String getPrefix(String namespaceUri)
	{
		XmlNamespaceDetails value = namespacesSortedByUri.get(namespaceUri);
		if(value != null)
		{
			return value.getPrefix(); 
		}
		return null;
	}
}
