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

package rocfox.rdf2xml.transformation.processor.sparql;

import org.junit.Assert;
import org.junit.Test;
import rocfox.rdf2xml.transformation.dom.SparqlQuery;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class SparqlAnalyserTest
{
	private static String readQuery()
	{
		return StringUtility.read(ClassLoader.getSystemResourceAsStream(SparqlAnalyserTest.class.getCanonicalName().replace('.', '/') + ".txt"));
	}
	
	@Test
	public void getQuery()
	{
		String query = readQuery();
		SparqlQuery instance = new SparqlQuery(query);
		Assert.assertEquals(query, instance.getQuery());
	}
	
	@Test
	public void containsPrefix()
	{
		SparqlQuery instance = new SparqlQuery(readQuery());
		Assert.assertTrue(instance.containsPrefix("rdfs"));
	}
	
	@Test
	public void addPrefix()
	{
		final String prefix = "ex";
		final String uri = "http://example.com";
		String query = readQuery();
		SparqlQuery instance = new SparqlQuery(query);
		instance.addPrefix(prefix, uri);
		StringBuilder expectedResult = new StringBuilder();
		expectedResult.append("PREFIX ex: <http://example.com>\n");
		expectedResult.append(query);
		query = instance.getQuery();
		//		System.out.println("=====================expected====================");
		//		System.out.println(expectedResult.toString());
		//		System.out.println("======================value======================");
		//		System.out.println(query);
		Assert.assertEquals(expectedResult.toString(), query);
	}
	
	@Test
	public void replaceTest()
	{
		String input = "adfasdfasd $var asdf";
		String output = input.replaceAll("\\$var", "test");
		String expected = "adfasdfasd test asdf";
		Assert.assertEquals(expected, output);
	}
}
