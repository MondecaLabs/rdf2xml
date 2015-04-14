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

package rocfox.rdf2xml.transformation.processor.context;

import rocfox.rdf2xml.transformation.processor.variable.VariableCollection;

/**
 * Manages the context of the transformation.
 * Stores and manages the list of variables and
 * the list of namespaces
 * 
 * @author Nicolas
 */
public class TransformContext
{
	/**
	 * Stores and manages the list of variables in
	 * the current scope of the transformation
	 */
	private VariableCollection variables = new VariableCollection();

	/**
	 * Stores and manages the list of variables in
	 * the current scope of the transformation
	 */
	public VariableCollection getVariables()
	{
		return variables;
	}
//	/**
//	 * Stores and manages the list of namespaces in
//	 * the current scope of the transformation
//	 */
//	private NamespaceCollection namespaces = new NamespaceCollection();
//
//	/**
//	 * Stores and manages the list of namespaces in
//	 * the current scope of the transformation
//	 */
//	public NamespaceCollection getNamespaces()
//	{
//		return namespaces;
//	}
	
	@Override
	public TransformContext clone()
	{
		TransformContext result = new TransformContext();
		result.variables = variables.clone();
		//result.namespaces = namespaces.clone();
		return result;
	}
}
