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

import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class ArgumentReader
{
	private static final String HelpKey = "help";
	private static final String TransformationKey = "transformation";
	private static final String InputKey = "input";
	private static final String OutputKey = "output";
	private static final String ParameterKey = "parameter";
	
	public static Arguments read(String[] args)
	{
		Arguments result = new Arguments();
		int index = -1;
		while(index + 1 < args.length)
		{
			index = read(args, result, index);
		}
		if(!result.isHelp())
		{
			if(StringUtility.isNullOrEmpty(result.getTransformation()))
			{
				throw new ArgumentException("Missing transformation.");
			}
			if(StringUtility.isNullOrEmpty(result.getOutput()))
			{
				throw new ArgumentException("Missing output file.");
			}
		}
		return result;
	}

	private static int read(final String[] args, final Arguments result, int index)
	{
		String arg = readNextArg(args, ++index);
		if(isInputKey(arg))
		{
			result.getInputs().add(readValue(args, ++index));
		}
		else if(isHelpKey(arg))
		{
			result.setHelp(true);
		}
		else if(isOutputKey(arg))
		{
			if(StringUtility.isNullOrEmpty(result.getOutput()))
			{
				result.setOutput(readValue(args, ++index));
			}
			else
			{
				throw new ArgumentException("Only one output file is accepted.");
			}
		}
		else if(isTransformationKey(arg))
		{
			if(StringUtility.isNullOrEmpty(result.getTransformation()))
			{
				result.setTransformation(readValue(args, ++index));
			}
			else
			{
				throw new ArgumentException("Only one transformation file is accepted.");
			}
		}
		else if(isParameterKey(arg))
		{
			String paramString = readValue(args, ++index);
			String[] params = paramString.split("=");
			if(params.length != 2)
			{
				throw new ArgumentException("The parameter " + paramString + " is not in the correct format.");
			}
			String paramKey = params[0];
			String paramValue = params[1];
			if(result.getParameters().containsKey(paramKey))
			{
				throw new ArgumentException("Duplicated parameter " + paramValue + ".");
			}
			result.getParameters().put(paramKey, paramValue);
		}
		else
		{
			throw new ArgumentException("The swith " + arg + " was not recognised.");
		}
		return index;
	}

	private static boolean isHelpKey(final String arg)
	{
		return isKey(arg, HelpKey);
	}

	private static boolean isTransformationKey(final String arg)
	{
		return isKey(arg, TransformationKey);
	}

	private static boolean isInputKey(final String arg)
	{
		return isKey(arg, InputKey);
	}

	private static boolean isOutputKey(final String arg)
	{
		return isKey(arg, OutputKey);
	}

	private static boolean isParameterKey(final String arg)
	{
		return isKey(arg, ParameterKey);
	}

	private static boolean isKey(final String arg, final String keyName)
	{
		if(arg.equalsIgnoreCase("--" + keyName))
		{
			return true;
		}
		else if(arg.equalsIgnoreCase("-" + keyName.substring(0, 1)))
		{
			return true;
		}
		return false;
	}
	
	private static String readValue(final String[] args, final int index)
	{
		String arg = readNextArg(args, index);
		if(StringUtility.isNullOrEmpty(arg))
		{
			throw new ArgumentException("The parameter " + index + " cannot be empty.");
		}
		if(arg.startsWith("-"))
		{
			throw new ArgumentException("The parameter " + index + " cannot start with '-'.");
		}
		return arg;
	}

	private static String readNextArg(final String[] args, final int index)
	{
		if(args.length > index)
		{
			return args[index];
		}
		return null;
	}
}
