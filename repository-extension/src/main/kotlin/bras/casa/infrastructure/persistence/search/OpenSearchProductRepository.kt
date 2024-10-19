/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence.search

import bras.casa.domain.model.Product
import bras.casa.domain.services.ProductRepository
import bras.casa.domain.services.ProductSearchQuery
import bras.casa.infrastructure.persistence.database.PgProductRepository
import io.quarkus.arc.properties.IfBuildProperty
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID
import org.hibernate.search.engine.search.predicate.SearchPredicate
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory
import org.hibernate.search.mapper.pojo.standalone.mapping.SearchMapping

@ApplicationScoped
@IfBuildProperty(name = "quarkus.hibernate-search-standalone.active", stringValue = "true")
internal class OpenSearchProductRepository(
    private val searchMapping: SearchMapping,
    private val dbRepository: PgProductRepository
) : ProductRepository {

    override fun save(product: Product) {

        dbRepository.save(product)

        val searchableProduct = SearchableProduct.create(product)
        searchMapping.createSession().use { searchSession ->
            searchSession.indexer().add(searchableProduct)
        }
    }

    override fun find(id: UUID): Product? = dbRepository.find(id)

    override fun find(query: ProductSearchQuery): List<Product> =
        searchMapping.createSession().use { searchSession ->
            searchSession
                .search(SearchableProduct::class.java)
                .where { it.bool().must(buildFilter(it, query)) }
                .fetchAllHits()
                .stream()
                .map<Product> { mapToProduct(it) }
                .toList()
        }

    private fun buildFilter(
        factory: SearchPredicateFactory,
        query: ProductSearchQuery,
    ): SearchPredicate {

        val predicateBuilder = factory.bool()

        query.apply {
            name?.let { predicateBuilder.should(factory.match().field("name").matching(it)) }
            description?.let {
                predicateBuilder.should(factory.match().field("description").matching(it))
            }
            price?.let {
                predicateBuilder.should(
                    factory.range().field("price").between(it.toDouble(), Double.MAX_VALUE)
                )
            }
            tag?.let { predicateBuilder.should(factory.match().field("tags").matching(it)) }
        }

        return predicateBuilder.toPredicate()
    }

    private fun mapToProduct(hit: SearchableProduct) = find(hit.id)!!
}
