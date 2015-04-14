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

package rocfox.rdf2xml.exceptions;

import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class ExceptionBuilder
{
	public static final UnsupportedOperationException createUnsupportedOperationException(String message)
	{
		if(StringUtility.isNullOrEmpty(message))
		{
			throw createNullArgumentException("message");
		}
		return new UnsupportedOperationException(message);
	}
	
	public static final UnsupportedOperationException createNotImplementedOperationException(String operationName)
	{
		if(StringUtility.isNullOrEmpty(operationName))
		{
			throw createNullArgumentException("operationName");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("The operation '");
		builder.append(operationName);
		builder.append("' is not implemented.");
		return new UnsupportedOperationException(builder.toString());
	}
	
	public static final IllegalArgumentException createIllegalArgumentException(String message)
	{
		if(StringUtility.isNullOrEmpty(message))
		{
			throw createNullArgumentException("message");
		}
		return new IllegalArgumentException(message);
	}
	
	public static final IllegalArgumentException createNullArgumentException(String argumentName)
	{
		if(StringUtility.isNullOrEmpty(argumentName))
		{
			throw createNullArgumentException("argumentName");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("The argument '");
		builder.append(argumentName);
		builder.append("' cannot be null.");
		return createIllegalArgumentException(builder.toString());
	}
	
}
