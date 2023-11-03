package Routing;

import Exceptions.APIException;
import Exceptions.ExceptionHandler;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private ExceptionHandler exceptionHandler = new ExceptionHandler();
    private PlantRoutes plantRoutes = new PlantRoutes();

    public EndpointGroup getRoutes(Javalin app){
        return () -> {
            app.routes(() -> {
                path("/", plantRoutes.getRoutes());
                    });
            app.exception(APIException.class, exceptionHandler::apiExceptionHandler);
            app.exception(NumberFormatException.class, exceptionHandler::numberFormatExceptionHandler);
            app.exception(Exception.class, exceptionHandler::exceptionHandler);
        };
    }
}
