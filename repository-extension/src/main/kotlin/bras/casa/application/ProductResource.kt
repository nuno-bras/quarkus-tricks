/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.application

import bras.casa.domain.services.ProductSearchQuery
import bras.casa.domain.usecase.FindProductsUseCase
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam

@ApplicationScoped
@Path("/product")
class ProductResource(private val findProductsUseCase: FindProductsUseCase) {

    @GET
    fun findProducts(
        @QueryParam("name") name: String?,
        @QueryParam("desc") description: String?,
        @QueryParam("price") price: Int?,
        @QueryParam("tag") tag: String?
    ) = findProductsUseCase.execute(ProductSearchQuery(name, description, price, tag))

    fun hello(@Observes event: StartupEvent) {
        println("Hello repository extension!")
    }
}
