package Controller;

import DAO.PlantDAO;
import DTO.PlantDTO;
import io.javalin.http.Handler;
import jakarta.persistence.criteria.CriteriaBuilder;

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

    @Override
    public Handler delete() {
        return ctx -> {
            ctx.status(204);
            ctx.json(dao.delete(Integer.parseInt(ctx.pathParam("id"))));
        };
    }

    @Override
    public Handler addPlantToSeller() {
        return ctx -> {
            ctx.status(200);
            ctx.json(dao.addPlantToReseller(Integer.parseInt(ctx.pathParam("resellerid")), Integer.parseInt(ctx.pathParam("plantid"))));
        };
    }
}
