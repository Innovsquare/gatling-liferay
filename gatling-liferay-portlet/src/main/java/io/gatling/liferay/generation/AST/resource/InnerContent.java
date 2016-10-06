/**
 * Copyright 2011-2015 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.liferay.generation.AST.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * InnerContent represents resource data that must be
 * included into some other file.
 * Thus, it isn't really a plain file. 
 */
public abstract class InnerContent extends ResourceFileAST{

	private static final String LOCATION = "data/feeders/";
	
	protected InnerContent(String name, String type) {
		super(name, type, LOCATION);
	}

	@Override
	public List<ResourceFileAST> flatWithSubsequentRessourceFile() {
		return new ArrayList<>(0);
	}
}