import Config.HibernateConfig;
import Config.ServerConfig;
import DTO.PlantDTO;
import Entities.Plant;
import Entities.Reseller;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestTest {

    private EntityManagerFactory emf;
    private Javalin app;
    private Plant p1;
    private Plant p2;
    private String baseURL = "http://localhost:7070/api";

    @BeforeAll
    void beforeAll() {
        HibernateConfig.setForTest(true);
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        app = Javalin.create();
        ServerConfig.startJavalinServer(app, 7070);
    }

    @BeforeEach
    void setup() {
        p1 = new Plant("Rose", "Albertine", 400, 199.50);
        p2 = new Plant("Bush", "Aronia", 200, 169.50);
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
    void delete() {
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
        app.stop();
    }

    @Test
    void allPlants() {
        List<Plant> plants =
                given()
                        .contentType("application/json")
                        .when()
                        .get(baseURL + "/plants")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", Plant.class);

        assertThat(p1, isIn(plants));
        assertThat(p2, isIn(plants));
        assertEquals(5, plants.size());
    }

    @Test
    void getByID() {
        given()
                .contentType("application/json")
                .when()
                .get(baseURL + "/plants/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void getByIDNonExist() {
        given()
                .contentType("application/json")
                .when()
                .get(baseURL + "/plants/25")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Plant with ID: 25 does not exist."));
    }

    @Test
    void getByType() {
        List<Plant> plants =
                given()
                        .contentType("application/json")
                        .when()
                        .get(baseURL + "/plants/type/rose")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", Plant.class);
        assertEquals(2, plants.size());
        assertThat(p1, isIn(plants));
    }

    @Test
    void getByTypeNonExist(){
        given()
                .contentType("application/json")
                .when()
                .get(baseURL + "/plants/type/tree")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Plants with type: tree does not exist."));
    }

    @Test
    void add(){
                given()
                        .contentType("application/json")
                        .body("{\"name\": \"OliveTree\", \"type\": \"Tree\", \"maxHeight\": 1000, \"price\": 1499.50}")
                        .when()
                        .post(baseURL + "/plants")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .body("id", equalTo(6));
    }
}
