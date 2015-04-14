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
package rocfox.rdf2xml.utilities.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import rocfox.rdf2xml.exceptions.ExceptionBuilder;
import rocfox.rdf2xml.exceptions.GenericException;
import rocfox.rdf2xml.utilities.StringUtility;

/**
 *
 * @author Nicolas
 */
public class XmlWriter
{
	private XMLStreamWriter writer;
	private XmlNamespaceContextStack contexts = new XmlNamespaceContextStack();
	private boolean previousWasText = false;
	private int indent = 0;

	public XmlWriter(XMLStreamWriter writer)
	{
		if (writer == null)
		{
			throw ExceptionBuilder.createNullArgumentException("writer");
		}
		this.writer = writer;
	}

	public void writeStartDocument(String encoding, String version)
	{
		try
		{
			writer.writeStartDocument(encoding, version);
			writer.flush();
			previousWasText = false;
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	private void addNamespaceDeclaration(XmlNamespaceContext context, String prefix, String namespaceUri)
	{
		try
		{
			if (!context.contains(prefix, namespaceUri))
			{
				context.add(prefix, namespaceUri);
				if(!StringUtility.isNullOrEmpty(namespaceUri))
				{
					writer.writeNamespace(prefix, namespaceUri);
				}
			}
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	private void checkBeforeAttribute(XmlNamespaceContext context, XmlNamespaceDetails namespace)
	{
		String prefix = StringUtility.ensureNotNull(namespace.getPrefix());
		String namespaceUri = StringUtility.ensureNotNull(namespace.getNamespaceUri());
		if(StringUtility.isNullOrEmpty(namespaceUri))
		{
			if(!StringUtility.isNullOrEmpty(prefix))
			{
				if(context.containsPrefix(prefix))
				{
					//we use the uri for that prefix
					//and keep that prefix
					namespaceUri = context.getNamespaceUri(prefix);
				}
				else
				{
					//this is an invalid prefix so we clear it
					prefix = "";
					if(context.containsPrefix(prefix))
					{
						//we use the URI of the default namespace
						//with no prefix
						namespaceUri = context.getNamespaceUri(prefix);
					}
				}
			}
		}
		else
		{
			if(StringUtility.isNullOrEmpty(prefix))
			{
				if(!context.contains(prefix, namespaceUri))
				{
					prefix = context.createNewPrefix();
				}
			}
			else
			{
				if(context.containsPrefix(prefix))
				{
					if(!context.contains(prefix, namespaceUri))
					{
						prefix = context.createNewPrefix();
					}
				}
			}
		}
		namespace.setNamespaceUri(namespaceUri);
		namespace.setPrefix(prefix);
	}
	
	private void checkBeforeElement(XmlNamespaceContext context, XmlNamespaceDetails namespace)
	{
		String prefix = StringUtility.ensureNotNull(namespace.getPrefix());
		String namespaceUri = StringUtility.ensureNotNull(namespace.getNamespaceUri());
		if(StringUtility.isNullOrEmpty(namespaceUri))
		{
			if(StringUtility.isNullOrEmpty(prefix))
			{
				if(context.containsPrefix(prefix))
				{
					//we use the URI of the default namespace
					//with no prefix
					namespaceUri = context.getNamespaceUri(prefix);
				}
			}
			else
			{
				if(context.containsPrefix(prefix))
				{
					//we use the uri for that prefix
					//and keep that prefix
					namespaceUri = context.getNamespaceUri(prefix);
				}
				else
				{
					//this is an invalid prefix so we clear it
					prefix = "";
					if(context.containsPrefix(prefix))
					{
						//we use the URI of the default namespace
						//with no prefix
						namespaceUri = context.getNamespaceUri(prefix);
					}
				}
			}
		}
		namespace.setNamespaceUri(namespaceUri);
		namespace.setPrefix(prefix);
	}

	public void writeEmptyElement(String prefix, String name, String namespaceUri)
	{
		if (StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		try
		{
			//indentation
			writeIndent(indent++);
			//context 
			XmlNamespaceContext context = contexts.moveToNext();
			//prefix and uri
			XmlNamespaceDetails details = new XmlNamespaceDetails(prefix, namespaceUri);
			checkBeforeElement(context, details);
			prefix = details.getPrefix();
			namespaceUri = details.getNamespaceUri();
			//write element
			if(StringUtility.isNullOrEmpty(namespaceUri))
			{
				writer.writeEmptyElement(name);
			}
			else
			{
				writer.writeEmptyElement(prefix, name, namespaceUri);			
			}
			addNamespaceDeclaration(context, prefix, namespaceUri);
			indent--;
			writer.flush();
			previousWasText = false;
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public void writeStartElement(String prefix, String name, String namespaceUri)
	{
		if (StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		try
		{
			//indentation
			writeIndent(indent++);
			//context 
			XmlNamespaceContext context = contexts.moveToNext();
			//prefix and uri
			XmlNamespaceDetails details = new XmlNamespaceDetails(prefix, namespaceUri);
			checkBeforeElement(context, details);
			prefix = details.getPrefix();
			namespaceUri = details.getNamespaceUri();
			//write element
			if(StringUtility.isNullOrEmpty(namespaceUri))
			{
				writer.writeStartElement(name);
			}
			else
			{
				writer.writeStartElement(prefix, name, namespaceUri);
			}
			addNamespaceDeclaration(context, prefix, namespaceUri);
			writer.flush();
			previousWasText = false;
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public void writeEndElement()
	{
		try
		{
			indent--;
			if (!previousWasText)
			{
				writeIndent(indent);
			}
			writer.writeEndElement();
			contexts.moveBack();
			writer.flush();
			previousWasText = false;
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public void writeAttribute(String prefix, String name, String namespaceUri, String value)
	{
		if (StringUtility.isNullOrEmpty(name))
		{
			throw ExceptionBuilder.createNullArgumentException("name");
		}
		try
		{
			//context 
			XmlNamespaceContext context = contexts.moveToNext();
			//prefix and uri
			XmlNamespaceDetails details = new XmlNamespaceDetails(prefix, namespaceUri);
			checkBeforeAttribute(context, details);
			prefix = details.getPrefix();
			namespaceUri = details.getNamespaceUri();
			//check value
			value = StringUtility.ensureNotNull(value);
			//add namespace declaration
			addNamespaceDeclaration(context, prefix, namespaceUri);
			//write attribute
			if(StringUtility.isNullOrEmpty(namespaceUri))
			{
				writer.writeAttribute(name, value);
			}
			else
			{
				if(StringUtility.isNullOrEmpty(prefix))
				{
					writer.writeAttribute(namespaceUri, name, value);
				}
				else
				{
					writer.writeAttribute(prefix, namespaceUri, name, value);
				}
			}
			contexts.moveBack();
			writer.flush();
			previousWasText = false;
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public void writeText(String text)
	{
		try
		{
			if (!StringUtility.isNullOrEmpty(text))
			{
				String trimmedText = text.trim();
				//we do not want to copy white spaces, to preserv a pretty identation!
				if (trimmedText.length() > 0)
				{
					writer.writeCharacters(text);
					writer.flush();
					previousWasText = true;
				}
			}
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	private void writeIndent(int indent)
	{
		try
		{
			writer.writeCharacters("\n");
			for (int index = 0; index < indent; index++)
			{
				writer.writeCharacters("\t");
			}
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}

	public void writeComment(String text)
	{
		try
		{
			if (!StringUtility.isNullOrEmpty(text))
			{
				writer.writeComment(text);
			}
		}
		catch (XMLStreamException ex)
		{
			throw new GenericException(ex);
		}
	}
}
