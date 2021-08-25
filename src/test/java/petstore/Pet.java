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
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    //3.2 - Métodos e Funções
    public String lerJason(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJason("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // given - when - Then

        given() //Dados
                .contentType("application/json") // comum em API REST -antifas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() //Então
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
        String petId = "1702199956";

        String token =
        given()
                .contentType("application/json")
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
        System.out.println("O token é " + token);

    }
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJason("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Romeu"))
                .body("status", is("sold"))
        ;
    }
    @Test(priority = 4)
    public void excluirPet(){
        String petId = "1702199956";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("1702199956"))
        ;
    }
}
