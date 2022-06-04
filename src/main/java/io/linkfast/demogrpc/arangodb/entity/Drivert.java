package io.linkfast.demogrpc.arangodb.entity;

import com.arangodb.springframework.annotation.Document;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Document("drivert")
public class Drivert {
    @Id
    private String id;
    private String name;
    private String lastname;
    private String dni;
    private String placa;
    private String marca;
    private Boolean status;
}