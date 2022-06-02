package io.linkfast.demogrpc.arangodb.entity;

import com.arangodb.springframework.annotation.Document;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Document("user")
public class User {

    @Id
    private String id;

    private String name;
    private String nickName;
    private String address;
    private Boolean active;
    private Integer age;
    private String sex;

}
