package io.linkfast.demogrpc.arangodb.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import io.linkfast.demogrpc.arangodb.entity.Position;

public interface PositionRepository extends ArangoRepository<Position, String> { }