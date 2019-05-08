package cn.adbyte.mongodbdemo;

import cn.adbyte.mongodbdemo.doc.SysLogDocument;
import net.bytebuddy.TypeCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbDemoApplicationTests {

    @Autowired
    private SysLogRepository sysLogRepository;

    @Test
    public void contextLoads() {
        long time1 = new Date().getTime();
        PageRequest pr = PageRequest.of(1, 25, Sort.by(Sort.Order.desc("sequenceId")));
        Page<SysLogDocument> all = sysLogRepository.findAll(pr);
        System.out.println("============================================");
        System.out.println(all.getContent());

        long time2 = new Date().getTime();
        System.out.println("==============="+ (time2 - time1)+"================");
        pr = PageRequest.of(3, 35, Sort.by(Sort.Order.desc("sequenceId")));
        all = sysLogRepository.findAll(pr);
        System.out.println("============================================");
        System.out.println(all.getContent());

        long time3 = new Date().getTime();
        System.out.println("==============="+ (time3 - time2)+"================");
        pr = PageRequest.of(900, 45, Sort.by(Sort.Order.desc("sequenceId")));
        all = sysLogRepository.findAll(pr);
        System.out.println("============================================");
        System.out.println(all.getContent());

        long time4 = new Date().getTime();
        System.out.println("==============="+ (time4 - time3)+"================");
        pr = PageRequest.of(65000, 22, Sort.by(Sort.Order.desc("sequenceId")));
        all = sysLogRepository.findAll(pr);
        System.out.println("============================================");
        System.out.println(all.getContent());
    }

}
