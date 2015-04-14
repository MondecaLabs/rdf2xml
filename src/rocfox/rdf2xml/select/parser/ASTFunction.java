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

package rocfox.rdf2xml.select.parser;

import rocfox.rdf2xml.select.dom.SelectFunction;

public class ASTFunction extends SimpleNode
{
	public ASTFunction(int id)
	{
		super(id);
	}
	
	public ASTFunction(SelectParser p, int id)
	{
		super(p, id);
	}
	
	public SelectFunction convert()
	{
		SelectFunction result = new SelectFunction();
		for(int index = 0; index < children.length; index++)
		{
			Node node = children[index];
			if(node instanceof ASTFunctionName)
			{
				ASTFunctionName functionName = (ASTFunctionName)node;
				result.setName(functionName.convert());
			}
			else if(node instanceof ASTVariable)
			{
				ASTVariable variable = (ASTVariable)node;
				result.setVariable(variable.convert());
			}
		}
		return result;
	}
	
}
