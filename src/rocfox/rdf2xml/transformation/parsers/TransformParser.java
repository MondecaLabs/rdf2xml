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

package rocfox.rdf2xml.transformation.parsers;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.transformation.dom.TransformOutput;
import rocfox.rdf2xml.transformation.dom.Transform;
import rocfox.rdf2xml.exceptions.TransformException;
import rocfox.rdf2xml.transformation.dom.Template;
import rocfox.rdf2xml.transformation.parsers.validation.ValidationOptions;
import rocfox.rdf2xml.utilities.xml.DocumentReader;
import rocfox.rdf2xml.utilities.xml.XmlReader;

/**
 *
 * @author Nicolas
 */
public class TransformParser extends Parser
{
	@Override
	public boolean isElement(Element source)
	{
		return super.isElement(source) && Transform.ElementName.equals(source.getLocalName());
	}

	public Transform parse(InputStream stream)
	{
		return parse(DocumentReader.readFromStream(stream));
	}
	
	public Transform parse(String file)
	{
		final String message = "The transformation could not be loaded.";
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return parse(db.parse(file));
		}
		catch (ParserConfigurationException ex)
		{
			throw new TransformException(message, ex);
		}
		catch (IOException ex)
		{
			throw new TransformException(message, ex);
		}
		catch (SAXException ex)
		{
			throw new TransformException(message, ex);
		}
	}
	
	public Transform parse(Document document)
	{
		if(document == null)
		{
			throw ExceptionBuilder.createNullArgumentException("document");
		}
		return parse(document.getDocumentElement());
	}
	
	public Transform parse(Element source)
	{
		if(source == null)
		{
			throw ExceptionBuilder.createNullArgumentException("source");
		}
		validate(source);
		Transform result = new Transform();
		for(Element element : XmlReader.selectChildElements(source, Template.ElementName))
		{
			TemplateParser parser = new TemplateParser(result);
			result.getTemplates().add(parser.parse(element));
		}
		Element element = XmlReader.selectSingleChildElement(source, TransformOutput.ElementName);
		if(element == null)
		{
			throw new TransformException("The element '" + Transform.ElementName + "' must contain one '" + TransformOutput.ElementName + "' element.");
		}
		TransformOutputParser parser = new TransformOutputParser(result);
		result.setOutput(parser.parse(element));
		return result;
	}
	
	public void validate(Element source)
	{
		ValidationOptions options = new ValidationOptions();
		options.setAllowChildTextNodes(false);
		options.setAllowOtherAttributes(false);
		options.setAllowOtherChildElements(false);
		options.getAllowedChildElements().add(Template.ElementName);
		options.getAllowedChildElements().add(TransformOutput.ElementName);
		options.validate(source);
	}
}
