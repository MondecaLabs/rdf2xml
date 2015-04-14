/*
 * Copyright 2008 Nicolas Cochard
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rocfox.rdf2xml.transformation.processor;

import rocfox.rdf2xml.transformation.processor.context.TransformContext;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.utilities.xml.XmlWriter;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.OutputType;
import rocfox.rdf2xml.transformation.processor.txt.TxtTransformationProcessor;
import rocfox.rdf2xml.transformation.processor.xml.TransformProcessor;

/**
 *
 * @author Nicolas
 */
public class Processor
{
	private Transform transform;
	
	public Processor(Transform transform)
	{
		if(transform == null)
		{
			throw ExceptionBuilder.createNullArgumentException("transform");
		}
		this.transform = transform;
	}
	
	public void transform(Model model, String outputFile)
	{
		try
		{
			OutputStream stream = new FileOutputStream(outputFile);
			transform(model, stream);
			stream.flush();
			stream.close();
		}
		catch (FileNotFoundException exception)
		{
			throw new TransformException("The file " + outputFile + " does not exists. " + exception.getMessage(), exception);
		}
		catch(IOException exception)
		{
			throw new TransformException(exception.getMessage(), exception);
		}
	}
	
	private void transform(Model model, OutputStream stream)
	{
		OutputType outputType = transform.getOutput().getType();
		switch(outputType)
		{
		case text:
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
			TxtTransformationProcessor transformer = new TxtTransformationProcessor(model, transform, outputStreamWriter);
			transformer.Transform();
			break;
		case xml:
			try
			{
				XMLOutputFactory factory = XMLOutputFactory.newInstance();
				XMLStreamWriter xmlStreamWriter = factory.createXMLStreamWriter(stream, transform.getOutput().getEncoding());
				XmlWriter writer = new XmlWriter(xmlStreamWriter);
				TransformProcessor processor = new TransformProcessor(model,transform);
				processor.transform(writer, new TransformContext());
				xmlStreamWriter.flush();
			}
			catch (XMLStreamException exception)
			{
				throw new UnsupportedOperationException("Error creating the XML.", exception);
			}
			break;
		}
	}
}
