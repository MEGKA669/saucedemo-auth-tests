package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReaderConfig {

	private static Config config ;
	//Lire et chargé les données de la calsse Config
	
	public static Config getConfig() {
		if (config == null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				// Charge le fichier config.json à partir des ressources
                InputStream inputStream = ReaderConfig.class.getClassLoader().getResourceAsStream("config.json");
                
                // Vérifie si le fichier existe bien
                if (inputStream == null) {
                    throw new RuntimeException("Le fichier config.json n'a pas été trouvé dans les ressources.");
                }
				// Ajoute un log pour vérifier que le fichier est chargé
	            System.out.println("Chargement du fichier de configuration...");
				config = mapper.readValue(new File("src/main/resources/config.json"),Config.class);
			}catch (IOException e) {
				throw new RuntimeException("❌ Erreur lors de la lecture du fichier de configuration !");
			}
		}
		return config;
	}


/*public static void main(String[] args) {
Config config = ReaderConfig.getConfig();
System.out.println("URL : " + config.getUrl());
System.out.println("Username : " + config.getUsername());
System.out.println("password : " + config.getPassword());
}*/
}