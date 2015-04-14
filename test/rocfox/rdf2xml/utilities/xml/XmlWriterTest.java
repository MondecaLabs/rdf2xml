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
package rocfox.rdf2xml.utilities.xml;

import java.io.CharArrayWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import rocfox.rdf2xml.transformation.processor.xml.Utility;
import rocfox.rdf2xml.utilities.xml.unitTest.XmlAssert;

/**
 *
 * @author Nicolas
 */
public class XmlWriterTest
{	
	@Test
	public void writeStartDocument()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "body", "uri");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeStartDocument");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElementWithNoPrefix()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement(null, "root", "uri1");
		writer.writeEmptyElement(null, "child", "uri2");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElementWithNoPrefix");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement1_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeEmptyElement(null, "child", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement1_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement1_null_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeEmptyElement(null, "child", "uri");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement1_null_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement1_notnull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeEmptyElement("prefix", "child", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement1_notnull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement1_notnull_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeEmptyElement("prefix", "child", "uri");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement1_notnull_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement2_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefixROOT", "root", "uriROOT");
		writer.writeEmptyElement(null, "child", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement2_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement2_null_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefixROOT", "root", "uriROOT");
		writer.writeEmptyElement(null, "child", "uri");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement2_null_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement2_notnull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefixROOT", "root", "uriROOT");
		writer.writeEmptyElement("prefix", "child", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement2_notnull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeEmptyElement2_notnull_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefixROOT", "root", "uriROOT");
		writer.writeEmptyElement("prefix", "child", "uri");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeEmptyElement2_notnull_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement1_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeStartElement(null, "child", null);
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement1_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement1_null_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeStartElement(null, "child", "uri");
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement1_null_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement1_notnull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeStartElement("prefix", "child", null);
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement1_notnull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement1_notnull_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeStartElement("prefix", "child", "uri");
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement1_notnull_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement2_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeStartElement(null, "child", null);
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement2_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement2_null_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeStartElement(null, "child", "uri2");
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement2_null_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement2_notnull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeStartElement("prefix2", "child", null);
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement2_notnull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeElement2_notnull_notnull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeStartElement("prefix2", "child", "uri2");
		writer.writeEndElement();
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeElement2_notnull_notnull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeNoPrefixAttributeIntoNoPrefixElement()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement(null, "root", "uri1");
		writer.writeAttribute(null, "Attribute", "uri2", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeNoPrefixAttributeIntoNoPrefixElement");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_null_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute(null, "Attribute", null, null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_null_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_null_null_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute(null, "Attribute", null, "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_null_null_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_null_notNull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute(null, "Attribute", "uri2", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_null_notNull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_null_notNull_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute(null, "Attribute", "uri2", "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_null_notNull_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_notNull_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute("prefix2", "Attribute", null, null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_notNull_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_notNull_null_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute("prefix2", "Attribute", null, "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_notNull_null_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_notNull_notNull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute("prefix2", "Attribute", "uri2", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_notNull_notNull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute1_notNull_notNull_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix1", "root", "uri1");
		writer.writeAttribute("prefix2", "Attribute", "uri2", "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute1_notNull_notNull_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_null_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute(null, "Attribute", null, null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_null_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_null_null_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute(null, "Attribute", null, "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_null_null_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_null_notNull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute(null, "Attribute", "uri", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_null_notNull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_null_notNull_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute(null, "Attribute", "uri", "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_null_notNull_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_notNull_null_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute("prefix", "Attribute", null, null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_notNull_null_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_notNull_null_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute("prefix", "Attribute", null, "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_notNull_null_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_notNull_notNull_null()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute("prefix", "Attribute", "uri", null);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_notNull_notNull_null");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeAttribute2_notNull_notNull_notNull()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeAttribute("prefix", "Attribute", "uri", "Value");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeAttribute2_notNull_notNull_notNull");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeText()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeText("Some text");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeText");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}

	@Test
	public void writeComment()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("prefix", "root", "uri");
		writer.writeComment("Some text");
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "writeComment");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}
}
