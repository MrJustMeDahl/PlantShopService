import Config.HibernateConfig;
import DAO.PlantDAO;
import DTO.PlantDTO;
import Entities.Plant;
import Entities.Reseller;
import Exceptions.APIException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DAOTest {

    private EntityManagerFactory emf;

    private PlantDAO dao;
    private Plant p1;

    @BeforeAll
    void setupClass() {
        HibernateConfig.setForTest(true);
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        dao = new PlantDAO();
    }

    @BeforeEach
    void setup() {
        p1 = new Plant("Rose", "Albertine", 400, 199.50);
        Plant p2 = new Plant("Bush", "Aronia", 200, 169.50);
        Plant p3 = new Plant("FruitAndBerries", "AromaApple", 350, 399.50);
        Plant p4 = new Plant("Rhododendron", "Astrid", 40, 269.50);
        Plant p5 = new Plant("Rose", "The DarkLady", 100, 199.50);
        Reseller r1 = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
        Reseller r2 = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");
        try (EntityManager em = HibernateConfig.getEntityManagerFactoryConfig().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.persist(r1);
            em.persist(r2);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void afterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE public.plant RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.reseller RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    void tearDown() {
        HibernateConfig.setForTest(false);
    }

    @Test
    void getAll() throws APIException {
        List<Plant> allPlants = dao.getAll();
        assertEquals(5, allPlants.size());
        Plant expected = new Plant(1, "Rose", "Albertine", 400, 199.50);
        assertEquals(expected, allPlants.get(0));
    }

    @Test
    void getByID() throws APIException{
        Plant found = dao.getByID(1);
        Plant expected = new Plant(1, "Rose", "Albertine", 400, 199.50);
        assertEquals(expected, found);

        assertThrows(APIException.class, () -> dao.getByID(45));
    }

    @Test
    void add(){
        Plant newPlant = dao.add(new PlantDTO("Tree", "OliveTree", 1000, 1499.50));
        assertEquals(6, newPlant.getId());
    }

    @Test
    void getByType() throws APIException{
        List<Plant> plantsByType = dao.getByType("rose");
        assertEquals(2, plantsByType.size());
        assertThrows(APIException.class, () -> dao.getByType("Porsche 911"));
    }

    @Test
    void delete() throws APIException{
        Plant deleted = dao.delete(1);
        Plant expected = new Plant(1, "Rose", "Albertine", 400, 199.50);
        assertEquals(expected, deleted);
        assertEquals(4, dao.getAll().size());
        assertThrows(APIException.class, () -> dao.delete(45));
    }

    @Test
    void deletePlantWithReseller() throws APIException{
        Reseller reseller = dao.addPlantToReseller(1, 1);
        assertEquals(1, reseller.getPlants().size());
        Plant deleted = dao.delete(1);
        Plant expected = new Plant(1, "Rose", "Albertine", 400, 199.50);
        assertEquals(expected, deleted);
        try(EntityManager em = emf.createEntityManager()){
            reseller = em.find(Reseller.class, 1);
            assertEquals(0, reseller.getPlants().size());
        }
        assertThrows(APIException.class, () -> dao.delete(1));
    }

    @Test
    void addPlantToReseller() throws APIException{
        Reseller reseller = dao.addPlantToReseller(1, 1);
        assertThat(reseller.getPlants().stream().toList(), containsInAnyOrder(p1));
        assertThrows(APIException.class, () -> dao.addPlantToReseller(5, 1));
        assertThrows(APIException.class, () -> dao.addPlantToReseller(1, 45));
    }

    @Test
    void getPlantsByReseller() throws APIException{
        dao.addPlantToReseller(1, 1);
        List<Plant> plants = dao.getPlantsByReseller(1);
        Plant expected = new Plant(1, "Rose", "Albertine", 400, 199.50);
        assertEquals(expected, plants.get(0));
        assertThrows(APIException.class, () -> dao.getPlantsByReseller(5));
    }
}
