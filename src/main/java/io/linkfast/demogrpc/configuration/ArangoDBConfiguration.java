package io.linkfast.demogrpc.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"io.linkfast.demogrpc.arangodb.repository"})
public class ArangoDBConfiguration extends AbstractArangoConfiguration {

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder()
                .host("docker-dev-1.pe1.linkfast.net.pe", 8529)
                .user("usr_demo")
                .password("123456");
    }

    @Override
    public String database() {
        return "db_demo";
    }

}
