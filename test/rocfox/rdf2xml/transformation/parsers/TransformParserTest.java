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
import rocfox.rdf2xml.transformation.dom.OutputType;
import rocfox.rdf2xml.transformation.dom.Template;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.dom.XmlAttribute;
import rocfox.rdf2xml.transformation.dom.XmlElement;
import rocfox.rdf2xml.transformation.dom.XmlText;
import rocfox.rdf2xml.utilities.xml.DocumentReader;

/**
 *
 * @author Nicolas
 */
public class TransformParserTest
{
	private static final String xhtmlUri = "http://www.w3.org/1999/xhtml";
	@Test
	public void isElement()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".xml");
		TransformParser parser = new TransformParser();
		Assert.assertTrue(parser.isElement(document.getDocumentElement()));
	}
	
	@Test
	public void parse()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".xml");
		TransformParser parser = new TransformParser();
		Transform result = parser.parse(document.getDocumentElement());
		
		Assert.assertEquals(2, result.getTemplates().count());
		
		Template templateElements = result.getTemplates().get("Elements");
		Assert.assertNotNull(templateElements);
		Assert.assertEquals("ElementsQuery", templateElements.getMatch().getContent().getQuery());
		
		Template templateElementRefinements = result.getTemplates().get("ElementRefinements");
		Assert.assertNotNull(templateElementRefinements);
		Assert.assertEquals("ElementRefinementsQuery", templateElementRefinements.getMatch().getContent().getQuery());
		
		Assert.assertEquals("UTF-8", result.getOutput().getEncoding());
		Assert.assertEquals(OutputType.xml, result.getOutput().getType());
		Assert.assertEquals("1.0", result.getOutput().getVersion());
		
		//html
		Assert.assertEquals(1, result.getOutput().getChildren().size());
		XmlElement elementHtml = (XmlElement) result.getOutput().getChildren().get(0);
		Assert.assertEquals("html", elementHtml.getName());
		Assert.assertEquals(xhtmlUri, elementHtml.getNamespaceUri());
		
		//body
		Assert.assertEquals(1, elementHtml.getChildren().size());
		XmlElement elementBody = (XmlElement) elementHtml.getChildren().get(0);
		Assert.assertEquals("body", elementBody.getName());
		Assert.assertEquals(xhtmlUri, elementBody.getNamespaceUri());
		
		//bgcolor
		Assert.assertEquals(1, elementBody.getAttributes().size());
		XmlAttribute attributeBGColor = elementBody.getAttributes().get(0);
		Assert.assertEquals("bgcolor", attributeBGColor.getName());
		Assert.assertEquals("white", attributeBGColor.getValue());
		
		//PageBody
		Assert.assertEquals(1, elementBody.getChildren().size());
		XmlText pageBody = (XmlText) elementBody.getChildren().get(0);
		Assert.assertEquals("PageBody", pageBody.getContent());
	}
}
