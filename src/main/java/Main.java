import Config.ServerConfig;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        ServerConfig.startJavalinServer(Javalin.create(), 7007);
    }
}
