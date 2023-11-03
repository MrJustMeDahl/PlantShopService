package Routing;

import Controller.IPlantController;
import Controller.PlantController;
import Controller.PlantControllerDB;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {

//    private IPlantController pc = new PlantController();
    private IPlantController pc = new PlantControllerDB();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/plants", () -> {
                get("/", pc.getAll());
                get("/{id}", pc.getByID());
                get("/type/{type}", pc.getByType());
                post("/", pc.addNew());
            });
        };
    }
}
