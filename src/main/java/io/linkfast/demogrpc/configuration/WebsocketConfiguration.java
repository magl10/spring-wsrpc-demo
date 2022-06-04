package io.linkfast.demogrpc.configuration;

import io.linkfast.demogrpc.wsrpc.UserService;
import io.linkfast.demogrpc.wsrpc.DrivertService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer {

    private final UserService userService;
    private final DrivertService drivertService;

    public WebsocketConfiguration(
            UserService userService,
            DrivertService drivertService
    ) {
        this.userService = userService;
        this.drivertService = drivertService;
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

        registry
                .addHandler(this.drivertService.createDrivert(),
                        "/DrivertService/createDrivert")
                .addHandler(this.drivertService.getAllDrivert(),
                        "/DrivertService/getAllDrivert")
                .addHandler(this.drivertService.updateDrivert(),
                        "/DriverService/UpdateDrivert")
                .setAllowedOrigins("*");
    }
}
/*.setAllowedOrigins(this.drivertService.getAllDrivert(),
                        "/DrivertService/getAllDrivert")*/
