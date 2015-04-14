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
public class Transform
{
	public static final String ElementName = "transform";
	
	public static final String NamespaceUri = "http://schema.rocfox.co.uk/rtl#";
	
	public static final String NamespacePreferredPrefix = "rtl";
	
	private TemplateList templates = new TemplateList();
	
	public TemplateList getTemplates()
	{
		return templates;
	}
	
	private TransformOutput output = new TransformOutput();
	
	public void setOutput(TransformOutput output)
	{
		this.output = output;
	}
	
	public TransformOutput getOutput()
	{
		return output;
	}
}
