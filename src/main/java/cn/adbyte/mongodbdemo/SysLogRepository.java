package cn.adbyte.mongodbdemo;

import cn.adbyte.mongodbdemo.doc.PersonDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yaodw
 */
@Repository
public interface SysLogRepository extends MongoRepository<PersonDocument, Long> {

    Page<PersonDocument> findBy(Query var1, Pageable page);

}
