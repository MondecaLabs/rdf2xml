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
public class TransformOutput extends NodeContainer
{
	public static final String ElementName = "output";
	
	public static final String AttributeNameType = "type";
	
	public static final String AttributeNameEncoding = "encoding";
	
	public static final String AttributeNameVersion = "version";
	
	private OutputType type;
	
	public OutputType getType()
	{
		return type;
	}
	
	public void setType(OutputType type)
	{
		this.type = type;
	}
	
	private String encoding;
	
	public String getEncoding()
	{
		return encoding;
	}
	
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}
	
	private String version;
	
	public String getVersion()
	{
		return version;
	}
	
	public void setVersion(String version)
	{
		this.version = version;
	}
}
