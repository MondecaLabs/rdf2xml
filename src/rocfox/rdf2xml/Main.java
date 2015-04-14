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

package rocfox.rdf2xml;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import rocfox.rdf2xml.arguments.ArgumentReader;
import rocfox.rdf2xml.arguments.Arguments;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.transformation.parsers.TransformParser;
import rocfox.rdf2xml.transformation.processor.Processor;

/**
 *
 * @author Nicolas
 */
public class Main
{
	/**
	 * @param args the command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		Arguments arguments = ArgumentReader.read(args);
		if(arguments.isHelp())
		{
			System.out.println(Usage.UsageString);
		}
		else
		{
			Model model = ReadModel(arguments);
			Transform transformation = ReadTransformation(arguments);
			Processor transformer = new Processor(transformation);
			transformer.transform(model, arguments.getOutput());
		}
	}
	
	private static Model ReadModel(final Arguments arguments)
	{
		Model result = ModelFactory.createOntologyModel();
		for(String input : arguments.getInputs())
		{
			try
			{
				InputStream in = new FileInputStream(new File(input));
				result.read(in, null);
				in.close();
			}
			catch (IOException exception)
			{
				throw new TransformException("Error reading the file " + input + ".", exception);
			}
		}
		return result;
	}
	
	private static Transform ReadTransformation(final Arguments arguments)
	{
		TransformParser parser = new TransformParser();
		return parser.parse(arguments.getTransformation());
	}
}
