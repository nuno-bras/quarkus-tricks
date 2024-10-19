/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services

import bras.casa.domain.model.Product
import java.util.UUID

interface ProductRepository {

    fun save(product: Product)

    fun find(query: ProductSearchQuery): List<Product>

    fun find(id: UUID): Product?
}
