/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence.search

import bras.casa.domain.model.Product
import bras.casa.infrastructure.persistence.search.extensions.AnalysisSearchExtension
import java.util.UUID
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.SearchEntity

@SearchEntity
@Indexed
internal data class SearchableProduct(
    @DocumentId val id: UUID,
    @FullTextField(analyzer = AnalysisSearchExtension.Analyzers.ENGLISH) var name: String,
    @FullTextField(analyzer = AnalysisSearchExtension.Analyzers.ENGLISH) var description: String,
    val totalPrice: Double,
    @FullTextField(analyzer = AnalysisSearchExtension.Analyzers.KEYWORD) var tags: Set<String>
) {
    constructor(
        product: Product
    ) : this(
        product.id,
        product.name,
        product.description,
        product.price.baseCost.amount + product.price.fee.amount,
        product.tags
    )

    companion object Factory {
        fun create(product: Product): SearchableProduct {
            return SearchableProduct(product)
        }
    }
}
