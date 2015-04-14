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

package rocfox.rdf2xml.arguments;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Nicolas
 */
public class ArgumentReaderTest
{
	private static final String transformationFile = "transformationFile1";
	private static final String inputFile1 = "inputFile1";
	private static final String inputFile2 = "inputFile2";
	private static final String outputFile = "outputFile";
	private static final String paramKey1 = "paramKey1";
	private static final String paramVal1 = "paramVal1";
	private static final String paramKey2 = "paramKey2";
	private static final String paramVal2 = "paramVal2";
	
	@Test
	public void testRead()
	{		
		final String[] args =
		{
			"-i", inputFile1,
			"-i", inputFile2,
			"-o", outputFile,
			"-t", transformationFile,
			"-p", paramKey1 + "=" + paramVal1,
			"-p", paramKey2 + "=" + paramVal2
		};
		
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(true, outputFile.equals(result.getOutput()));
		Assert.assertEquals(true, result.getInputs().size() == 2);
		Assert.assertEquals(true, result.getParameters().size() == 2);
		for(String input : result.getInputs())
		{
			Assert.assertEquals(true, input.equals(inputFile1) || input.equals(inputFile2));
		}
		Assert.assertEquals(paramVal1, result.getParameters().get(paramKey1));
		Assert.assertEquals(paramVal2, result.getParameters().get(paramKey2));
		Assert.assertEquals(false, result.isHelp());
	}
	
	@Test
	public void testRead2()
	{		
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--output", outputFile,
			"--transformation", transformationFile,
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(true, outputFile.equals(result.getOutput()));
		Assert.assertEquals(true, result.getInputs().size() == 2);
		Assert.assertEquals(true, result.getParameters().size() == 2);
		for(String input : result.getInputs())
		{
			Assert.assertEquals(true, input.equals(inputFile1) || input.equals(inputFile2));
		}
		Assert.assertEquals(paramVal1, result.getParameters().get(paramKey1));
		Assert.assertEquals(paramVal2, result.getParameters().get(paramKey2));
		Assert.assertEquals(false, result.isHelp());
	}
	
	@Test(expected=ArgumentException.class)
	public void testInvalidFileName()
	{		
		final String[] args =
		{
			"-i", "-asdf"
		};
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(false, result.isHelp());
		Assert.fail("Invalid input should be detected");
	}
	
	@Test(expected=ArgumentException.class)
	public void testRead2Outputs()
	{	
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--output", outputFile,
			"--output", outputFile,//two outputs
			"--transformation", transformationFile,
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(false, result.isHelp());
	}
	
	@Test(expected=ArgumentException.class)
	public void testRead2Transformations()
	{
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--output", outputFile,
			"--transformation", transformationFile,
			"--transformation", transformationFile,//second transformation
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(false, result.isHelp());
	}
	
	@Test
	public void testHelp1()
	{
		final String[] args =
		{
			"-h"
		};
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(true, result.isHelp());
	}
	
	@Test
	public void testHelp2()
	{
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--output", outputFile,
			"--transformation", transformationFile,
			"--help",
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		Arguments result = ArgumentReader.read(args);
		Assert.assertEquals(true, result.isHelp());
	}
	
	@Test(expected=ArgumentException.class)
	public void testMissingTransformation()
	{
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--output", outputFile,
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		ArgumentReader.read(args);
	}
	
	@Test(expected=ArgumentException.class)
	public void testMissingOutput()
	{
		final String[] args =
		{
			"--input", inputFile1,
			"--input", inputFile2,
			"--transformation", transformationFile,
			"--parameter", paramKey1 + "=" + paramVal1,
			"--parameter", paramKey2 + "=" + paramVal2
		};
		ArgumentReader.read(args);
	}
}
