package utils; //déclare le package ou se trouve le dataprovider
//on importe les classe nécessaire pour lire du JSON sur le disque et gérer les exceptions au cas ou le fichier est introuvable ou mal formé
import java.io.File;
import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.ObjectMapper; //on importe jackson qui est une bib java pour lire et convertir un fichier json en object java []

public class userDataProvider {

@DataProvider(name="Datausers")
public static Object[][] getUsersData()throws IOException{
ObjectMapper mapper = new ObjectMapper();
user[] authUsers = mapper.readValue(new File("src/test/resources/authUsers.json"),user[].class);

Object[][] data = new Object[authUsers.length][1];
for (int i =0 ; i < authUsers.length ; i++) {
	data[i][0] = authUsers[i];
}
return data;
}
	
}
