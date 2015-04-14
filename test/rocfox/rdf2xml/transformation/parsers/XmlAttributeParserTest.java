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
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;
import rocfox.rdf2xml.utilities.xml.DocumentReader;

/**
 *
 * @author Nicolas
 */
public class XmlAttributeParserTest
{
	@Test
	public void parse1()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "1.xml");
		Element element = document.getDocumentElement();
		Attr attr = element.getAttributeNode("href");
		XmlAttribute result = XmlAttributeParser.parse(attr);
		Assert.assertEquals("href", result.getName());
		Assert.assertEquals("AUrl", result.getValue());
		Assert.assertNull(result.getNamespaceUri());
		Assert.assertNull(result.getPrefix());
	}
	@Test
	public void parse2()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "2.xml");
		Element element = document.getDocumentElement();
		Attr attr = element.getAttributeNode("xhtml:href");
		XmlAttribute result = XmlAttributeParser.parse(attr);
		Assert.assertEquals("href", result.getName());
		Assert.assertEquals("AUrl", result.getValue());
		Assert.assertEquals("http://www.w3.org/1999/xhtml", result.getNamespaceUri());
		Assert.assertEquals("xhtml", result.getPrefix());
	}
	@Test
	public void parse3()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + "3.xml");
		Element element = document.getDocumentElement();
		Attr attr = element.getAttributeNode("href");
		XmlAttribute result = XmlAttributeParser.parse(attr);
		Assert.assertEquals("href", result.getName());
		Assert.assertEquals("", result.getValue());
		Assert.assertNull(result.getNamespaceUri());
		Assert.assertNull(result.getPrefix());
	}
}
