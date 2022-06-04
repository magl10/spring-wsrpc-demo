package io.linkfast.demogrpc.arangodb.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import io.linkfast.demogrpc.arangodb.entity.Drivert;
import io.linkfast.demogrpc.arangodb.entity.User;

import java.util.List;

public interface DrivertRepository extends ArangoRepository<Drivert,String> {
    List<Drivert> findAll();
}