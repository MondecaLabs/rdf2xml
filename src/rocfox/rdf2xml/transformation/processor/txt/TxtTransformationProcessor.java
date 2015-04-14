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

package rocfox.rdf2xml.transformation.processor.txt;

import com.hp.hpl.jena.rdf.model.Model;
import java.io.OutputStreamWriter;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.exceptions.TransformException;

/**
 *
 * @author Nicolas
 */
public class TxtTransformationProcessor
{
	private Transform transformation;
	private Model model;
	private OutputStreamWriter writer;
	
	public TxtTransformationProcessor(Model model,Transform transformation, OutputStreamWriter writer) 
	{
		if(transformation == null)
		{
			throw new TransformException("The parameter 'transformation' cannot be null");
		}
		this.transformation = transformation;
		if(model == null)
		{
			throw new TransformException("The parameter 'model' cannot be null");
		}
		this.model = model;
		if(writer == null)
		{
			throw new TransformException("The parameter 'writer' cannot be null");
		}
		this.writer = writer;
	}

	public void Transform()
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
