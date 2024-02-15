package Test;

import Test.Consultar;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeleçãoDeUsuario extends Consultar {

  //  @Test
    public void Excluir_usuario_com_id_1_com_sucesso() {
        given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .when()
                .delete("/{id}", 10)
                .then()
                .statusCode(200);
    }
}



