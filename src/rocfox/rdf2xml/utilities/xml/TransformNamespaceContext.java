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

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class TransformNamespaceContext implements NamespaceContext
{
	public String getNamespaceURI(String prefix)
	{
		if(StringUtility.isNullOrEmpty(prefix))
		{
			return null;
		}
		else if(Transform.NamespacePreferredPrefix.equals(prefix))
		{
			return Transform.NamespaceUri;
		}
		else
		{
			return null;
		}
	}

	public String getPrefix(String namespaceURI)
	{
		if(StringUtility.isNullOrEmpty(namespaceURI))
		{
			return null;
		}
		else if(Transform.NamespaceUri.equals(namespaceURI))
		{
			return Transform.NamespacePreferredPrefix;
		}
		else
		{
			return null;
		}
	}

	public Iterator getPrefixes(String namespaceURI)
	{
		ArrayList result = new ArrayList();
		result.add(Transform.NamespaceUri);
		return result.iterator();
	}
	
}
