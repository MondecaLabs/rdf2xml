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
import org.junit.Test;
import org.w3c.dom.Document;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.utilities.xml.unitTest.XmlAssert;
import static org.junit.Assert.*;

/**
 *
 * @author Nicolas
 */
public class XmlAttributeProcessorTest
{
	private static final String prefix1 = "prefix1";
	private static final String prefix2 = "prefix2";
	private static final String uri1 = "uri1";
	private static final String uri2 = "uri2";
	@Test
	public void transform1()
	{
		transform(null, null, null, null, 1);
	}
	@Test
	public void transform2()
	{
		transform(null, null, null, uri2, 2);
	}
	@Test
	public void transform3()
	{
		transform(null, null, prefix2, null, 3);
	}
	@Test
	public void transform4()
	{
		transform(null, null, prefix2, uri2, 4);
	}
	@Test
	public void transform5()
	{
		transform(null, uri1, null, null, 5);
	}
	@Test
	public void transform6()
	{
		transform(null, uri1, null, uri2, 6);
	}
	@Test
	public void transform7()
	{
		transform(null, uri1, prefix2, null, 7);
	}
	@Test
	public void transform8()
	{
		transform(null, uri1, prefix2, uri2, 8);
	}
	@Test
	public void transform9()
	{
		transform(prefix1, uri1, null, null, 9);
	}
	@Test
	public void transform10()
	{
		transform(prefix1, uri1, null, uri2, 10);
	}
	@Test
	public void transform11()
	{
		transform(prefix1, uri1, prefix2, null, 11);
	}
	
	@Test
	public void transform12()
	{
		transform(prefix1, uri1, prefix2, uri2, 12);
	}
	
	@Test
	public void transform13()
	{
		transform(prefix1, uri1, null, uri1, 13);
	}
	
	@Test
	public void transform14()
	{
		transform(prefix1, uri1, prefix1, null, 14);
	}
	
	@Test
	public void transform15()
	{
		transform(prefix1, uri1, prefix1, uri1, 15);
	}
	
	private void transform(String rootPrefix, String rootUri, String prefix, String uri, int index)
	{
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		XmlAttribute input = new XmlAttribute();
		input.setName("SomeName");
		input.setNamespaceUri(uri);
		input.setPrefix(prefix);
		XmlAttributeProcessor processor = new XmlAttributeProcessor(Utility.getModel(), input);
		
		TransformContext context = new TransformContext();
		
		writer.writeStartDocument("utf-8", "1.0");
		writer.writeStartElement(rootPrefix, "body", rootUri);
		processor.transform(writer, context);
		writer.writeEndElement();
		
		Document expected = Utility.getXml(getClass(), "output" + index);
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}
}
