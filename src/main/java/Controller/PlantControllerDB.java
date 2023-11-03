package Controller;

import DAO.IDAO;
import DAO.PlantDAO;
import DTO.PlantDTO;
import io.javalin.http.Handler;

public class PlantControllerDB implements IPlantController{

    private PlantDAO dao = new PlantDAO();

    @Override
    public Handler getAll() {
        return ctx ->{
            ctx.status(200);
            ctx.json(dao.getAll());
        };
    }

    @Override
    public Handler getByID() {
        return ctx -> {
            ctx.status(200);
            ctx.json(dao.getByID(Integer.parseInt(ctx.pathParam("id"))));
        };
    }

    @Override
    public Handler getByType() {
        return ctx -> {
            ctx.status(200);
            ctx.json(dao.getByType(ctx.pathParam("type")));
        };
    }

    @Override
    public Handler addNew() {
        return ctx -> {
            ctx.status(201);
            ctx.json(dao.add(ctx.bodyAsClass(PlantDTO.class)));
        };
    }
}
