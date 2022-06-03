package io.linkfast.demogrpc.configuration;

import io.linkfast.demogrpc.wsrpc.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer {

    private final UserService userService;

    public WebsocketConfiguration(
            UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(this.userService.createUser(),
                        "/UserService/CreateUser")
                .addHandler(this.userService.deleteUser(),
                        "/UserService/DeleteUser")
                .addHandler(this.userService.getAllUsers(),
                        "/UserService/GetAllUsers")
                .addHandler(this.userService.updateUser(),
                        "/UserService/UpdateUser")
                .addHandler(this.userService.onReceivedLocationDriver(),
                        "/UserService/OnReceivedLocationDriver")
                .setAllowedOrigins("*");
    }
}
