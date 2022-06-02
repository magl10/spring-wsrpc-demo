package io.linkfast.demogrpc.arangodb.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import io.linkfast.demogrpc.arangodb.entity.User;

import java.util.List;

public interface UserRepository extends ArangoRepository<User, String> {

    List<User> findAll();

}
