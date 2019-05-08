package cn.adbyte.mongodbdemo.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Adam
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sys_log")
@CompoundIndexes({
        @CompoundIndex(name = "sequence_Id", def = "{'sequenceId':-1}")
})
public class SysLogDocument {
    @Id
    private String id;
    private Long sequenceId;
    private String description;
    private String method;
    private String logType;
    private String requestIp;
    private String exceptionCode;
    private String exceptionDetail;
    private String params;

    private Boolean del = false;
    @LastModifiedDate
    private Date last;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date time;
    /**
     * 创建人
     */
    @CreatedBy
    private String createdBy;
    /**
     * 修改人
     */
    @LastModifiedBy
    private String modifiedBy;
}
