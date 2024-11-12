/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model

import java.util.UUID

class Product(
    val id: UUID,
    var name: String,
    var description: String,
    val price: ProductPrice,
    var tags: Set<String>
)
