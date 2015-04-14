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

/**
 *
 * @author Nicolas
 */
public class Template
{
	public static final String ElementName = "template";

	public static final String AttributeNameName = "name";
	
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	private ParamList params = new ParamList();

	public ParamList getParams()
	{
		return params;
	}
	
	private Match match;

	public Match getMatch()
	{
		return match;
	}

	public void setMatch(Match match)
	{
		this.match = match;
	}
	
	private TemplateOutput output;

	public TemplateOutput getOutput()
	{
		return output;
	}

	public void setOutput(TemplateOutput output)
	{
		this.output = output;
	}
}
