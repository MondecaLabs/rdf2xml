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

/**
 *
 * @author Nicolas
 */
public class Usage
{
	
	public static final String UsageString = 
		"Usage: rdfTranformer -i <InputFile> -o <OutputFile> -p <ParameterName>=<ParameterValue>\n" +
		"Usage: rdfTranformer -h\n" +
		"\n" +
		"Transforms an RDF graph to text or XML." +
		"The RDF graph can be defined in multiple files.\n" +
		"\n" +
		"-i, --input\t Path to an input RDF file.\n" +
		"-o, --output\t Path to an output TXT or XML file.\n" +
		"-t, --transformation\t Path to the RDF transformation.\n" +
		"-p, --parameter\t A key-value pair of a parameter for the RDF transsformation.\n" +
		"\n";
	
}
