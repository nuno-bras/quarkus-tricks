/**
 * Development by Nuno Brás, 2024
 */
package bras.casa

import io.quarkus.test.junit.QuarkusIntegrationTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusIntegrationTest
class EmployeeResourceIT {

    @Test
    fun testHelloEndpoint() {
        given().`when`().get("/hello").then().statusCode(200).body(`is`("Hello from Quarkus REST"))
    }
}
