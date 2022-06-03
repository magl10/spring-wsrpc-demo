package io.linkfast.demogrpc.arangodb.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import io.linkfast.demogrpc.arangodb.entity.Driver;

public interface DriverRepository extends ArangoRepository<Driver, String> { }