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

import java.io.CharArrayWriter;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import rocfox.rdf2xml.transformation.dom.XmlText;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.utilities.xml.unitTest.XmlAssert;

/**
 *
 * @author Nicolas
 */
public class XmlTextProcessorTest
{
	@Test
	public void transform()
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		XmlText input = new XmlText();
		input.setContent("SomeData");
		XmlTextProcessor processor = new XmlTextProcessor(Utility.getModel(), input);
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement("xhtml", "body", "http://www.w3.org/1999/xhtml");
		processor.transform(writer, new TransformContext());
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "output");
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}
	
	@Test
	public void transformLiteral()
	{
		XmlText input = new XmlText();
		input.setContent("SomeData");
		XmlTextProcessor processor = new XmlTextProcessor(Utility.getModel(), input);
		
		String value = processor.transformLiteral(new TransformContext());
		
		Assert.assertEquals("SomeData", value);
	}
}
