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

package rocfox.rdf2xml.utilities.xml;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.GenericException;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class DocumentReader
{
	public static Document readFromFile(String fileName)
	{
		if(StringUtility.isNullOrEmpty(fileName))
		{
			throw ExceptionBuilder.createNullArgumentException("fileName");
		}
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(fileName);
		}
		catch(Exception exception)
		{
			throw new GenericException(exception);
		}
	}
	
	public static Document readFromStream(InputStream input)
	{
		if(input == null)
		{
			throw ExceptionBuilder.createNullArgumentException("input");
		}
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(input);
		}
		catch(Exception exception)
		{
			throw new GenericException(exception);
		}
	}
	
	public static Document readFromJar(String path)
	{
		if(StringUtility.isNullOrEmpty(path))
		{
			throw ExceptionBuilder.createNullArgumentException("path");
		}
		InputStream stream = ClassLoader.getSystemResourceAsStream(path);
		if(stream == null)
		{
			throw ExceptionBuilder.createIllegalArgumentException(String.format("The resource '%s' cannot be loaded fom the JAR.", path));
		}
		return readFromStream(stream);
	}
	
	public static Document readFromWriter(CharArrayWriter buffer)
	{
		try
		{
			CharArrayReader reader = new CharArrayReader(buffer.toCharArray());
			InputSource source = new InputSource(reader);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(source);
		}
		catch (Exception ex)
		{
			throw new GenericException(ex);
		}
	}
}
