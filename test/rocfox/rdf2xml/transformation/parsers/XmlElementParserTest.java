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

package rocfox.rdf2xml.transformation.parsers;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.XmlElement;
import rocfox.rdf2xml.utilities.xml.DocumentReader;

/**
 *
 * @author Nicolas
 */
public class XmlElementParserTest
{	
	@Test
	public void parse1()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "1.xml");
		XmlElementParser parser = new XmlElementParser(new Transform());
		XmlElement result = (XmlElement) parser.parse(document.getDocumentElement());
		Assert.assertEquals("http://www.w3.org/1999/xhtml", result.getNamespaceUri());
		Assert.assertNull(result.getPrefix());
		Assert.assertEquals("a", result.getName());
	}
	@Test
	public void parse2()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "2.xml");
		XmlElementParser parser = new XmlElementParser(new Transform());
		XmlElement result = (XmlElement) parser.parse(document.getDocumentElement());
		Assert.assertNull(result.getNamespaceUri());
		Assert.assertNull(result.getPrefix());
		Assert.assertEquals("a", result.getName());
	}
	@Test
	public void parse3()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "3.xml");
		XmlElementParser parser = new XmlElementParser(new Transform());
		XmlElement result = (XmlElement) parser.parse(document.getDocumentElement());
		Assert.assertEquals("http://www.w3.org/1999/xhtml", result.getNamespaceUri());
		Assert.assertEquals("xhtml", result.getPrefix());
		Assert.assertEquals("a", result.getName());
	}
}
