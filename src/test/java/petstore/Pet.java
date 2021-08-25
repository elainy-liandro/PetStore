// 1 - Pacote
package petstore;

// 2 - bibliotecas

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endere�o da entidade Pet

    //3.2 - M�todos e Fun��es
    public String lerJason(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // identifica o m�todo ou fun��o como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJason("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Ent�o
        // given - when - Then

        given() //Dados
                .contentType("application/json") // comum em API REST -antifas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() //Ent�o
                .log().all()
                .statusCode(200)
                .body("name", is("Romeu"))
                .body("status", is("available"))
                .body("category.name", is("AX15487LID"))
                .body("tags.name", contains("data"))
                ;

    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "1702199952";

        String token =
        given()
                .contentType("aplication/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Romeu"))
                .body("category.name", is("AX15487LID"))
                .body("status", is("available"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token � " + token);

    }
}
