package DAO;

import Config.HibernateConfig;
import DTO.PlantDTO;
import Entities.Plant;
import Entities.Reseller;
import Exceptions.APIException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class PlantDAO implements IDAO<PlantDTO, Integer, Plant>, IDAOType<Plant, String>{

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    @Override
    public List<Plant> getAll() throws APIException{
        List<Plant> found;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Plant> query = em.createNamedQuery("Plant.getAll", Plant.class);
            found = query.getResultList();
        }
        if(found.isEmpty()){
            throw new APIException(200, "There is no plants to show at the moment.");
        }
        return found;
    }

    @Override
    public Plant getByID(Integer id) throws APIException{
        Plant found;
        try(EntityManager em = emf.createEntityManager()){
            found = em.find(Plant.class, id);
        }
        if(found == null){
            throw new APIException(404, "Plant with ID: " + id + " does not exist.");
        }
        return found;
    }

    @Override
    public Plant add(PlantDTO plantDTO) {
        Plant newPlant = new Plant(plantDTO.getType(), plantDTO.getName(), plantDTO.getMaxHeight(), plantDTO.getPrice());
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(newPlant);
            em.getTransaction().commit();
        }
        return newPlant;
    }

    @Override
    public List<Plant> getByType(String s) throws APIException{
        List<Plant> found;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Plant> query = em.createNamedQuery("Plant.getByType", Plant.class);
            query.setParameter("type", s);
            found = query.getResultList();
        }
        if(found.isEmpty()){
            throw new APIException(404, "Plants with type: " + s + " does not exist.");
        }
        return found;
    }

    public Plant delete(int id) throws APIException{
        Plant found;
        try(EntityManager em = emf.createEntityManager()){
            found = em.find(Plant.class, id);
            if(found != null){
                em.getTransaction().begin();
                em.remove(found);
                em.getTransaction().commit();
            } else {
                throw new APIException(404, "The plant you are trying to delete does not exist, please ensure you are using the correct id.");
            }
        }
        return found;
    }

    public Reseller addPlantToReseller(int resellerID, int plantID) throws APIException{
        Reseller reseller;
        Plant plant;
        try(EntityManager em = emf.createEntityManager()) {
            reseller = em.find(Reseller.class, resellerID);
            plant = em.find(Plant.class, plantID);
            if (reseller == null) {
                throw new APIException(404, "Can't find a reseller with the entered id.");
            } else if (plant == null) {
                throw new APIException(404, "Can't find a plant with the entered id.");
            }
            reseller.addPlant(plant);
            em.getTransaction().begin();
            em.merge(reseller);
            reseller.getPlants().size();
            em.getTransaction().commit();
        }
        return reseller;
    }

    public List<Plant> getPlantsByReseller(int resellerID) throws APIException{
        Reseller reseller;
        try(EntityManager em = emf.createEntityManager()){
            reseller = em.find(Reseller.class, resellerID);
            if(reseller != null) {
                reseller.getPlants().size();
            } else {
                throw new APIException(404, "The reseller with the entered ID does not exist.");
            }
        }
        return reseller.getPlants().stream().toList();
    }

//    public static void main(String[] args) {
//      Plant p1 = new Plant("Rose", "Albertine", 400, 199.50);
//      Plant p2 = new Plant("Bush", "Aronia", 200, 169.50);
//      Plant p3 = new Plant("FruitAndBerries", "AromaApple", 350, 399.50);
//      Plant p4 = new Plant("Rhododendron", "Astrid", 40, 269.50);
//      Plant p5 = new Plant("Rose", "The DarkLady", 100, 199.50);
//      try(EntityManager em = HibernateConfig.getEntityManagerFactoryConfig().createEntityManager()){
//          em.getTransaction().begin();
//          em.persist(p1);
//          em.persist(p2);
//          em.persist(p3);
//          em.persist(p4);
//          em.persist(p5);
//          em.getTransaction().commit();
//      }
//    }
}
