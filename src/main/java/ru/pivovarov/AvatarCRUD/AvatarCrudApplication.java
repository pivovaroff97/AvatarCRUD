package ru.pivovarov.AvatarCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.pivovarov.AvatarCRUD.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AvatarCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvatarCrudApplication.class, args);
	}

}
