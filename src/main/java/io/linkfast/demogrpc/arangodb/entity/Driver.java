package io.linkfast.demogrpc.arangodb.entity;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Ref;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Document("driver")
public class Driver {

    @Id
    private String id;

    private String name;
    private String nickName;

    // The Last Position from Driver
    @Ref
    private Position position;

}

