package cn.adbyte.mongodbdemo.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Adam
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "person")
@CompoundIndexes({
        @CompoundIndex(name = "birthday", def = "{'birthday':-1}")
})
public class PersonDocument extends BaseDocument {
    private String name;
    private Integer age;
    private Date birthday;
    private String bio;

}
