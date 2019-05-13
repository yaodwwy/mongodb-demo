/*
 * Copyright 2016-2018 the original author or authors.
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
import cn.adbyte.mongodbdemo.doc.PersonDocument;
import cn.adbyte.mongodbdemo.util.ConsoleResultPrinter;
import cn.adbyte.mongodbdemo.util.SnowflakeIdWorker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.domain.ExampleMatcher.matching;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @author Jens Schauder
 */
@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTests {

    @Autowired
    UserRepository repository;
    @Autowired
    MongoTemplate template;
    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 0);
    private ConsoleResultPrinter<PersonDocument> consoleResultPrinter = new ConsoleResultPrinter<PersonDocument>();

    PersonDocument skyler, walter, flynn, marie, hank;

    //@Before
    public void setUp() {

        repository.deleteAll();
        ArrayList<PersonDocument> objects = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            int age = new Random().nextInt(150);
            int birthday = new Random().nextInt(150_000_000);
            PersonDocument e = new PersonDocument("User" + i, age, new Date(birthday), "bio" + i);
            e.setId(snowflakeIdWorker.nextId());
            objects.add(e);
        }
        repository.saveAll(objects);

    }


    @Test
    public void countBySimpleExample() {

        Example<PersonDocument> example = Example.of(new PersonDocument("User999", null, null, null));
        long count = repository.count(example);
        assertThat(count).isEqualTo(1L);
    }

    @Test
    public void ignorePropertiesAndMatchByAge() {
        flynn = new PersonDocument();
        flynn.setBio("biO");
        flynn.setAge(28);
        Example<PersonDocument> example = Example.of(flynn, matching(). //
                withIgnorePaths("bio"));
        Optional<PersonDocument> one = repository.findOne(example);
        System.out.println(one);
    }


    @Test
    public void substringMatching() {

        Example<PersonDocument> example = Example.of(new PersonDocument("99", null, null, null), matching(). //
                withStringMatcher(StringMatcher.ENDING));
        System.out.println(repository.findAll(example));
    }


    @Test
    public void regexMatching() {

        Example<PersonDocument> example = Example.of(
                new PersonDocument("(USER|User)10", null, null, null), matching(). //
                withMatcher("name", matcher -> matcher.regex()));

        System.out.println(repository.findAll(example));
    }

    @Test
    public void matchStartingStringsIgnoreCase() {

        Example<PersonDocument> example = Example.of(new PersonDocument("USER999", 999, null, null), matching(). //
                withIgnorePaths("age"). //
                withMatcher("name", ignoreCase()));

        System.out.println(repository.findAll(example));
    }


    @Test
    public void configuringMatchersUsingLambdas() {

        Example<PersonDocument> example = Example.of(new PersonDocument("USER999", 20, null, null), matching(). //
                withIgnorePaths("age"). //
                withMatcher("name", matcher -> matcher.ignoreCase()));

        System.out.println(repository.findAll(example));
    }


    @Test
    public void valueTransformer() {

        Example<PersonDocument> example = Example.of(new PersonDocument(null, 99, null, null), matching(). //
                withMatcher("age", matcher -> matcher.transform(value -> Optional.of(Integer.valueOf(50)))));

        System.out.println(repository.findAll(example));
    }

    @Test
    public void ageGt() {
        Criteria criteria = Criteria.where("age").gt(120);
        Query query = query(criteria);
        List<PersonDocument> personDocuments = template.find(query, PersonDocument.class);
        consoleResultPrinter.printResult(personDocuments,criteria);
    }

}
