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
public class WithParamList implements Iterable<WithParam>
{
	private TreeMap<String, WithParam> list = new TreeMap<String, WithParam>();
	
	public void add(WithParam withParam)
	{
		if(withParam == null)
		{
			throw ExceptionBuilder.createNullArgumentException("withParam");
		}
		if(StringUtility.isNullOrEmpty(withParam.getName()))
		{
			throw ExceptionBuilder.createIllegalArgumentException("The element '" + WithParam.ElementName + "' must have a '" + WithParam.AttributeNameName + "' attribute.");
		}
		list.put(withParam.getName(), withParam);
	}

	public Iterator<WithParam> iterator()
	{
		return list.values().iterator();
	}
	
	public int count()
	{
		return list.size();
	}
}
