package Controller;

import DAO.PlantDAOMock;
import DTO.PlantDTO;
import Exceptions.APIException;
import com.google.gson.Gson;
import io.javalin.http.Handler;

import java.util.List;

public class PlantController implements IPlantController{

    PlantDAOMock mock = new PlantDAOMock();

    @Override
    public Handler getAll() {
        return ctx -> {
            ctx.status(200);
            ctx.json(mock.getAll());
        };
    }

    @Override
    public Handler getByID() {
        return ctx -> {
            ctx.status(200);
            ctx.json(mock.getByID(Integer.parseInt(ctx.pathParam("id"))));
        };
    }

    @Override
    public Handler getByType() {
        return ctx -> {
            ctx.status(200);
            List<PlantDTO> found = mock.getByType(ctx.pathParam("type"));
            if(!found.isEmpty()) {
                ctx.json(found);
            } else {
                throw new APIException(404, "No plants with type: " + ctx.pathParam("type") + " exist.");
            }
        };
    }

    @Override
    public Handler addNew() {
        return ctx -> {
            ctx.status(201);
            ctx.json(mock.add(ctx.bodyAsClass(PlantDTO.class)));
        };
    }

    @Override
    public Handler delete() {
        return null;
    }

    @Override
    public Handler addPlantToSeller() {
        return null;
    }
}
