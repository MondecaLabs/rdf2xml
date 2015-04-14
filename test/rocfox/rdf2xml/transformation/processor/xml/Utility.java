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
package rocfox.rdf2xml.transformation.processor.xml;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import rocfox.rdf2xml.exceptions.GenericException;
import rocfox.rdf2xml.utilities.StringUtility;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.XmlWriter;

/**
 *
 * @author Nicolas
 */
public class Utility
{
	/**
	 * Returns an XML document from the jar file.
	 * The name of the XML file to read will be the name of a
	 * class followed by a specific sufix and the XML extension
	 * 
	 * @param type The type on which the name of the XML file is based
	 * @param sufix The sufix that is used in the name of the XML file after the class name
	 * @return The XML document
	 */
	public static Document getXml(Class type, String sufix)
	{
		String root = type.getCanonicalName().replace('.', '/');
		if(StringUtility.isNullOrEmpty(sufix))
		{
			return DocumentReader.readFromJar(root + ".xml");
		}
		else
		{
			return DocumentReader.readFromJar(root + "." + sufix + ".xml");
		}
	}

	public static XmlWriter getWriter(CharArrayWriter buffer)
	{
		try
		{
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter = factory.createXMLStreamWriter(buffer);
			return new XmlWriter(xmlStreamWriter);
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public static Model getModel()
	{
		try
		{
			InputStream dces = ClassLoader.getSystemResourceAsStream("rocfox/rdf2xml/transformation/processor/xml/resources/dces.rdf.xml");
			InputStream dcq = ClassLoader.getSystemResourceAsStream("rocfox/rdf2xml/transformation/processor/xml/resources/dcq.rdf.xml");
			Model result = ModelFactory.createMemModelMaker().createDefaultModel();
			result.read(dces, null);
			result.read(dcq, null);
			dces.close();
			dcq.close();
			return result;
		}
		catch (IOException ex)
		{
			throw new GenericException(ex);
		}
	}
}
