package io.linkfast.demogrpc.arangodb.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import io.linkfast.demogrpc.arangodb.entity.Drivert;

public interface DrivertRepository extends ArangoRepository<Drivert,String> {
}
