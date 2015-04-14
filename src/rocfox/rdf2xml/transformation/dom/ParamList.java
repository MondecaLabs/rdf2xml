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
public class ParamList implements Iterable<Param>
{
	private TreeMap<String, Param> list = new TreeMap<String, Param>();
	
	public void add(Param param)
	{
		if(param == null)
		{
			throw ExceptionBuilder.createNullArgumentException("param");
		}
		if(StringUtility.isNullOrEmpty(param.getName()))
		{
			throw ExceptionBuilder.createIllegalArgumentException("The 'name' attribute of the parameter cannot be null");
		}
		list.put(param.getName(), param);
	}

	public Iterator<Param> iterator()
	{
		return list.values().iterator();
	}
	
	public int count()
	{
		return list.size();
	}
	
	public Param get(String name)
	{
		if(StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		if(list.containsKey(name))
		{
			return list.get(name);
		}
		return null;
	}
}
