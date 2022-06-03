package io.linkfast.demogrpc.arangodb.entity;

import com.arangodb.springframework.annotation.Document;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Document("position")
public class Position {

    @Id
    private String id;
    private Double latitude;
    private Double longitude;
    private Boolean listen;

}
