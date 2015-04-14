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

package rocfox.rdf2xml.arguments;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Nicolas
 */
public class Arguments
{
	private ArrayList<String> inputs = new ArrayList<String>();

	public ArrayList<String> getInputs()
	{
		return inputs;
	}
	
	private Hashtable<String, String> parameters = new Hashtable<String, String>();

	public Hashtable<String, String> getParameters()
	{
		return parameters;
	}
	
	private String output;

	public String getOutput()
	{
		return output;
	}

	public void setOutput(String output)
	{
		this.output = output;
	}
	
	private boolean help = false;

	public boolean isHelp()
	{
		return help;
	}

	public void setHelp(boolean help)
	{
		this.help = help;
	}
	
	private String transformation;

	public String getTransformation()
	{
		return transformation;
	}

	public void setTransformation(String transformation)
	{
		this.transformation = transformation;
	}
}
