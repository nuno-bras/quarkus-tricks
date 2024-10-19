/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services

data class ProductSearchQuery(
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val tag: String? = null
)
