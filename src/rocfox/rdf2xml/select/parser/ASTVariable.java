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

import rocfox.rdf2xml.select.dom.SelectVariable;

public class ASTVariable extends SimpleNode
{
	public ASTVariable(int id)
	{
		super(id);
	}
	
	public ASTVariable(SelectParser p, int id)
	{
		super(p, id);
	}
	
	public SelectVariable convert()
	{
		for(int index = 0; index < children.length; index++)
		{
			Node node = children[index];
			if(node instanceof ASTKeyword)
			{
				ASTKeyword keyword = (ASTKeyword)node;
				SelectVariable result = new SelectVariable();
				result.setName(keyword.getName());
				return result;
			}
		}
		return null;
	}
}
