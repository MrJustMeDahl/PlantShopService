package Controller;

import io.javalin.http.Handler;

public interface IPlantController {

    Handler getAll();
    Handler getByID();
    Handler getByType();
    Handler addNew();

    Handler delete();

    Handler addPlantToSeller();
}
