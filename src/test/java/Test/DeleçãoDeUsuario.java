package Test;

import Test.Consultar;

import static io.restassured.RestAssured.given;

public class DeleçãoDeUsuario extends Consultar {

    public void consultarTodosUsuarios() {
        given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200);

    }
}
