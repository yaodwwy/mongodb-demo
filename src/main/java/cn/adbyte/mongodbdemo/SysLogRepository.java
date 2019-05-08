package cn.adbyte.mongodbdemo;

import cn.adbyte.mongodbdemo.doc.SysLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yaodw
 */
@Repository
public interface SysLogRepository extends MongoRepository<SysLogDocument, Long> {
}
