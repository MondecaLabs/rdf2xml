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

package rocfox.rdf2xml.transformation.processor.context;

import java.util.TreeMap;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 * Represent the list of namespace in the current context.
 * Manages the list of prefix and namespace URIs.
 * 
 * @author Nicolas
 */
public class NamespaceCollection
{
	/**
	 * Internal list of prefixes and namespace URIs
	 */
	private TreeMap<String, String> namespaces = new TreeMap<String, String>();
	
	/**
	 * Adds a namespace to the current context.
	 * If a namespace already exists with the same prefix, it would be
	 * overwitten.
	 * 
	 * @param prefix The prefix of the namespace
	 * @param uri The URL of the current namespace
	 */
	public void Add(String prefix, String uri)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		if(namespaces.containsKey(prefix))
		{
			namespaces.remove(prefix);
		}
		namespaces.put(prefix, uri);
	}
	
	/**
	 * Gets the URI of a namespace from its prefix.
	 * 
	 * @param prefix The prefix of the namespace.
	 * @return The URI of the namespace.
	 */
	public String GetUri(String prefix)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		return namespaces.get(prefix);
	}
	
	@Override
	public NamespaceCollection clone()
	{
		NamespaceCollection result = new NamespaceCollection();
		result.namespaces = (TreeMap<String, String>)this.namespaces.clone();
		return result;
	}

	/**
	 * Validates the namepsace.
	 * If a prefix is already used for another URI, 
	 * we need to define a new prefix.
	 * 
	 * @param prefix The prefix of the nemspace
	 * @param namespaceUri The URI of the namepsace
	 * @return The new prefix to use instead of the source prefix.
	 */
	public String ensureNamespace(String prefix, String namespaceUri)
	{
		prefix = StringUtility.ensureNotNull(prefix);
		if(namespaces.containsKey(prefix))
		{
			String currentUriForThatPrefix = namespaces.get(prefix);
			if(StringUtility.equals(namespaceUri, currentUriForThatPrefix))
			{
				//this prefix and uri are already defined by a parent node
				//just reuse it
				return prefix;
			}
			else
			{
				//the prefix is used by a parent node but for a diferent uri
				//find the prefix already used for that uri
				String newPrefix = null;
				if(namespaces.containsValue(namespaceUri))
				{
					for(String existingPrefix : namespaces.keySet())
					{
						String correspondingUri = namespaces.get(existingPrefix);
						if(StringUtility.equals(correspondingUri, namespaceUri))
						{
							//we found the prefix used for that uri
							//reuse it
							newPrefix = existingPrefix;
						}
					}
				}
				if(newPrefix == null)
				{
					//we could not find a prefix used for that uri
					//create one
					newPrefix = newPrefix();
					Add(newPrefix, namespaceUri);
				}
				return newPrefix;
			}
		}
		else
		{
			//the prefix and uri were not used
			//let's use it
			Add(prefix, namespaceUri);
			return prefix;
		}
	}

	/**
	 * Generates and return a new prefix that is not yet in use.
	 * 
	 * @return A new prefix not yet in use.
	 */
	private String newPrefix()
	{
		int index = 0;
		String result = String.format("ns%d", index);
		while(namespaces.containsKey(result))
		{
			index++;
			result = String.format("ns%d", index);
		}
		return result;
	}
}
