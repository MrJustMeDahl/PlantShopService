package Config;

import Exceptions.APIException;
import Exceptions.ExceptionHandler;
import Routing.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;

public class ServerConfig {
    public static void startJavalinServer(Javalin app, int port){
        app.updateConfig(ServerConfig::javalinConfiguration);
        Routes routes = new Routes();
        app.routes(routes.getRoutes(app));
        app.start(port);
    }

    private static void javalinConfiguration(JavalinConfig config){
        config.routing.contextPath = "/api";
        config.http.defaultContentType = "application/json";
        config.plugins.register(new RouteOverviewPlugin("/"));
    }
}