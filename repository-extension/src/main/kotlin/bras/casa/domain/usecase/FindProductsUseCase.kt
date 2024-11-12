/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.usecase

import bras.casa.domain.model.Product
import bras.casa.domain.services.ProductRepository
import bras.casa.domain.services.ProductSearchQuery
import jakarta.enterprise.context.RequestScoped

@RequestScoped
class FindProductsUseCase(private val repository: ProductRepository) {

    fun execute(query: ProductSearchQuery): List<Product> = repository.find(query)
}
