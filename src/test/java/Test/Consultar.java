package Test;

import io.restassured.response.Response;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.Matchers.equalTo;

public class Consultar {

    static final String baseUrl = "https://jsonplaceholder.typicode.com/users";
    private static final String userId = "1"; // Você pode ajustar o id conforme necessário

    @Test
    public void consultarTodosUsuarios() {
        given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200);

    }

    @Test
    public void consultarUsuarioEspecificoComId1() {
        Response response = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .pathParam("id", 4)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);



        if (statusCode == 200 && responseBody.contains("SUCCESS")) {
            System.out.println("A mensagem contém a string 'SUCCESS'");
        } else {
            System.out.println("Erro na requisição. Verifique os detalhes acima.");
        }
    }

    @Test
    public void consultarUsuarioEspecificoComId2ValidandoRetorno() throws Exception {
        // Lê o conteúdo do arquivo JSON
        String expectedResponse = new String(readAllBytes(Paths.get("src/Data/response.json")));

        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/users")
                .pathParam("id", 2)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        if (statusCode == 200 && responseBody.contains("SUCCESS")) {
            System.out.println("A mensagem contém a string 'SUCCESS'");
        } else {
            System.out.println("Erro na requisição. Verifique os detalhes acima.");
        }
    }

    @Test
    public void consultarUsuarioComId2ComQueryStringValidandoCampoEspecifico() {
                 Response response =
                 given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .pathParam("id", 2)
                .queryParam("example", "teste")
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .extract().response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        if (statusCode == 200 && responseBody.contains("SUCCESS")) {
            System.out.println("A mensagem contém a string 'SUCCESS'");
        } else {
            System.out.println("Erro na requisição. Verifique os detalhes acima.");
        }
    }

    @Test
    public void cadastrarUsuarioComSucesso() throws Exception {
        // Lê o conteúdo do arquivo JSON
        String expectedResponse = new String(Files.readAllBytes(Paths.get("src/Data/payloadFile.Json")));

        // Realiza a requisição e recebe a resposta
        Response response;
        response = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(expectedResponse)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("nome", equalTo("fabiano dojo karater"))
                .body("id", equalTo(11))
                .extract().response();

        // Imprime o status code e o corpo da resposta
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
     //   System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        if (statusCode == 201 ) {
            System.out.println("A string do corpo da resposta contém o JSON desejado.");
        } else {
            System.out.println("A string do corpo da resposta não contém o JSON desejado.");
        }

    }

    @Test
    public void atualizarUsuarioComId3() {
        Response response = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .header("apikey", "valor-header")
                .headers("auth", "token", "empelco", "teste")
                .pathParam("id", userId)
                .when()
                .put("/{id}")
                .then()
                .log().all() // Adiciona este log para imprimir detalhes da requisição e resposta
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        if (statusCode == 200) {
            System.out.println("A mensagem contém a string 'SUCCESS'");
        } else {
            System.out.println("Erro na requisição. Verifique os detalhes acima.");
        }

    }




}


