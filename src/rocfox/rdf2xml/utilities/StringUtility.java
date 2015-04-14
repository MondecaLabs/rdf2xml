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

package rocfox.rdf2xml.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.GenericException;

/**
 *
 * @author Nicolas
 */
public class StringUtility
{
	/**
	 * Returns the value if it is not null,
	 * returns an empty string otherwise.
	 */
	public static String ensureNotNull(String value)
	{
		if(value == null)
		{
			return "";
		}
		return value;
	}

	/**
	 * Indicates whether two string are equal.
	 * 
	 * @param string1
	 * @param string2
	 * @return A boolean indicating whether the two string are equal.
	 */
	public static boolean equals(String string1, String string2)
	{
		if(string1 == null)
		{
			return string2 == null;
		}
		else
		{
			return string1.equals(string2);
		}
	}
	
	public static boolean isNullOrEmpty(String value)
	{
		if(value == null)
		{
			return true;
		}
		if(value.length() <= 0)
		{
			return true;
		}
		return false;
	}
	
	public static final String read(InputStream stream)
	{
		if(stream == null)
		{
			ExceptionBuilder.createNullArgumentException("stream");
		}
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(stream));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try
			{
				while ((line = bufferedReader.readLine()) != null)
				{
					stringBuilder.append(line + "\n");
				}
			}
			finally
			{
				bufferedReader.close();
			}
			return stringBuilder.toString();
		}
		catch(IOException exception)
		{
			throw new GenericException(exception);
		}
	}
}
