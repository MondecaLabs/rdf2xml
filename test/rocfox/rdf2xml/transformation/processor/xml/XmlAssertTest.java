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

import org.junit.Test;
import org.w3c.dom.Document;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.unitTest.XmlAssert;

/**
 *
 * @author Nicolas
 */
public class XmlAssertTest
{	
	@Test
	public void testXmlAssert()
	{
		Document x = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".x.xml");
		Document y = DocumentReader.readFromJar(getClass().getCanonicalName().replace('.', '/') + ".y.xml");
		XmlAssert.assertEquals(x, y);
	}
}
