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

package rocfox.rdf2xml.select.dom;

/**
 *
 * @author Nicolas
 */
public enum FunctionName
{
	uri("uri"),
	namespace("namespace"),
	localName("local-name"),
	nodeName("node-name");
	
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	private FunctionName(String name)
	{
		this.name = name;
	}
	
	public static FunctionName getByName(String name)
	{
		for(FunctionName current : FunctionName.values())
		{
			if(current.getName().equals(name))
			{
				return current;
			}
		}
		return null;
	}
}
