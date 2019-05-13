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
package cn.adbyte.mongodbdemo;

import cn.adbyte.mongodbdemo.doc.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * @author Christoph Strobl
 */
public interface BlogPostRepository extends CrudRepository<BlogPost, String>, QueryByExampleExecutor<BlogPost> {

	List<BlogPost> findAllBy(TextCriteria criteria);

	List<BlogPost> findAllByOrderByScoreDesc(TextCriteria criteria);

	// page through results for full text query
	Page<BlogPost> findBy(TextCriteria criteria, Pageable page);

}
