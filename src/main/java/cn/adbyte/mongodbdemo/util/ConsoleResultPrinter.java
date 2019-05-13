/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.adbyte.mongodbdemo.util;

import cn.adbyte.mongodbdemo.doc.BlogPost;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.Collection;

/**
 * Just a little helper for showing {@link BlogPost}s output on the console.
 *
 * @author Christoph Strobl
 */
public class ConsoleResultPrinter<T> {

	public void printResult(Collection<T> list, CriteriaDefinition criteria) {

		System.out.println(String.format("XXXXXXXXXXXX -- Found %s blogPosts matching '%s' --XXXXXXXXXXXX",
				list.size(), criteria != null ? criteria.getCriteriaObject() : ""));

		for (T t : list) {
			System.out.println(t);
		}

		System.out.println("XXXXXXXXXXXX -- XXXXXXXXXXXX -- XXXXXXXXXXXX\r\n");
	}
}
