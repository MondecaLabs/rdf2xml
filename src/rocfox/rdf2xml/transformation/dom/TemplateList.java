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

package rocfox.rdf2xml.transformation.dom;

import java.util.Iterator;
import java.util.TreeMap;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class TemplateList implements Iterable<Template>
{
	private TreeMap<String, Template> list = new TreeMap<String, Template>();
	
	public void add(Template template)
	{
		if(template == null)
		{
			throw ExceptionBuilder.createNullArgumentException("template");
		}
		if(StringUtility.isNullOrEmpty(template.getName()))
		{
			throw ExceptionBuilder.createIllegalArgumentException("The element '" + Template.ElementName + "' must have a '" + Template.AttributeNameName + "' attribute.");
		}
		list.put(template.getName(), template);
	}
	
	public Iterator<Template> iterator()
	{
		return list.values().iterator();
	}
	
	public Template get(String templateName)
	{
		if(StringUtility.isNullOrEmpty(templateName))
		{
			throw ExceptionBuilder.createNullArgumentException(templateName);
		}
		if(list.containsKey(templateName))
		{
			return list.get(templateName);
		}
		return null;
	}
	
	public int count()
	{
		return list.size();
	}
}
