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

package rocfox.rdf2xml.useCases.DC;

import org.junit.Test;
import rocfox.rdf2xml.useCases.BaseTestUseCase;

/**
 *
 * @author Nicolas
 */
public class DCTest extends BaseTestUseCase
{
	@Test
	public void transform()
	{
		super.transform(new String[] {"dces.rdf.xml", "dcq.rdf.xml"}, "transformation.xml", "dc.html");
	}
}
