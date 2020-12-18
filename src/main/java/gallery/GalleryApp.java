package gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@Configuration
@SpringBootApplication
public class GalleryApp {

    public static void main(String[] args) {
        SpringApplication.run(GalleryApp.class, args); // запускаем Spring приложение
    }
}