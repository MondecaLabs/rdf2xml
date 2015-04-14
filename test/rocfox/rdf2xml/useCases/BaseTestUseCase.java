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

package rocfox.rdf2xml.useCases;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Document;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.GenericException;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.parsers.TransformParser;
import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import rocfox.rdf2xml.transformation.processor.xml.TransformProcessor;
import rocfox.rdf2xml.transformation.processor.xml.Utility;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.utilities.xml.unitTest.XmlAssert;

/**
 *
 * @author Nicolas
 */
public abstract class BaseTestUseCase
{
	protected void transform(String[] inputs, String transform, String output)
	{
		Model model = readModel(inputs);
		Transform transformation = readTransformation(transform);
		TransformProcessor processor = new TransformProcessor(model, transformation);
		
		CharArrayWriter buffer = new CharArrayWriter();
		XmlWriter writer = Utility.getWriter(buffer);
		
		processor.transform(writer, new TransformContext());
		
		Document expected = getExpected(output);
		Document actual = DocumentReader.readFromWriter(buffer);
		XmlAssert.assertEquals(expected, actual);
	}
	
	private Model readModel(String[] inputs)
	{
		Model result = ModelFactory.createMemModelMaker().createDefaultModel();
		for(String input : inputs)
		{
			try
			{
				//Build resource name
				StringBuffer buffer = new StringBuffer();
				buffer.append(this.getClass().getPackage().getName().replace('.', '/'));
				buffer.append('/');
				buffer.append(input);
				//Stream
				InputStream stream = ClassLoader.getSystemResourceAsStream(buffer.toString());
				if(stream == null)
				{
					throw ExceptionBuilder.createIllegalArgumentException(String.format("The resource '%s' cannot be loaded fom the JAR.", buffer.toString()));
				}
				try
				{
					result.read(stream, null);
				}
				finally
				{
					if(stream != null)
					{
						stream.close();
					}
				}
			}
			catch (IOException exception)
			{
				throw new GenericException("Error reading the file " + input + ".", exception);
			}
		}
		return result;
	}
	
	private Transform readTransformation(String transform)
	{
		//Build resource name
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getPackage().getName().replace('.', '/'));
		buffer.append('/');
		buffer.append(transform);
		//Stream
		InputStream stream = ClassLoader.getSystemResourceAsStream(buffer.toString());
		if(stream == null)
		{
			throw ExceptionBuilder.createIllegalArgumentException(String.format("The resource '%s' cannot be loaded fom the JAR.", buffer.toString()));
		}
		TransformParser parser = new TransformParser();
		return parser.parse(stream);
	}

	private Document getExpected(String output)
	{
		//Build resource name
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getPackage().getName().replace('.', '/'));
		buffer.append('/');
		buffer.append(output);
		return DocumentReader.readFromJar(buffer.toString());
	}
}
