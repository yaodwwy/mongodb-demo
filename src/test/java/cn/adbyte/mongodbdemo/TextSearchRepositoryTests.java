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
import cn.adbyte.mongodbdemo.util.ConsoleResultPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TextSearchRepositoryTests {

    @Autowired
    BlogPostRepository repo;
    private ConsoleResultPrinter<BlogPost> consoleResultPrinter = new ConsoleResultPrinter<BlogPost>();

    @Test
    public void findAllBlogPostsWithRelease() {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("release");
        Page<BlogPost> by = repo.findBy(criteria, PageRequest.of(1, 1));
        consoleResultPrinter.printResult(by.getContent(), criteria);
    }

    @Test
    public void findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff() {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("release").notMatching("engineering");
        List<BlogPost> blogPosts = repo.findAllBy(criteria);
        consoleResultPrinter.printResult(blogPosts, criteria);
    }

    /**
     * Phrase matching looks for the whole phrase as one.
     */
    @Test
    public void findAllBlogPostsByPhrase() {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase("release candidate");
        List<BlogPost> blogPosts = repo.findAllBy(criteria);

        consoleResultPrinter.printResult(blogPosts, criteria);
    }

    /**
     * Sort by relevance relying on the value marked with {@link TextScore}.
     */
    @Test
    public void findAllBlogPostsByPhraseSortByScore() {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase("release candidate");
        List<BlogPost> blogPosts = repo.findAllByOrderByScoreDesc(criteria);

        consoleResultPrinter.printResult(blogPosts, criteria);
    }
}
