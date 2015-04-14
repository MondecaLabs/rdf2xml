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
import rocfox.rdf2xml.transformation.dom.Otherwise;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.utilities.xml.DocumentReader;

/**
 *
 * @author Nicolas
 */
public class OtherwiseParserTest
{
	@Test
	public void isElement()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".xml");
		OtherwiseParser parser = new OtherwiseParser(new Transform());
		Assert.assertTrue(parser.isElement(document.getDocumentElement()));
	}
	
	@Test
	public void parse()
	{
		Document document = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".xml");
		OtherwiseParser parser = new OtherwiseParser(new Transform());
		Otherwise otherwise = parser.parse(document.getDocumentElement());
	}
}
