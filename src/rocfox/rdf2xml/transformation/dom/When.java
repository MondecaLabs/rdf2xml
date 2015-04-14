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

import rocfox.rdf2xml.select.dom.SelectVariable;

/**
 *
 * @author Nicolas
 */
public class When extends NodeContainer
{
	public static final String ElementName = "when";
	
	public static final String AttributeNameTest = "test";
	
	private SelectVariable test;
	
	public SelectVariable getTest()
	{
		return test;
	}
	
	public void setTest(SelectVariable test)
	{
		this.test = test;
	}
}
