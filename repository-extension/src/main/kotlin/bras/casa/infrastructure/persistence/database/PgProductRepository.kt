/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence.database

import bras.casa.domain.model.Money
import bras.casa.domain.model.Product
import bras.casa.domain.model.ProductPrice
import bras.casa.domain.services.ProductRepository
import bras.casa.domain.services.ProductSearchQuery
import io.quarkus.arc.DefaultBean
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityNotFoundException
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
@DefaultBean
internal class PgProductRepository(private val em: EntityManager) : ProductRepository {

    @Transactional
    override fun save(product: Product) {
        val productEntity = ProductEntity.create(product)
        em.persist(productEntity)
    }

    override fun find(command: ProductSearchQuery): List<Product> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<ProductEntity> = cb.createQuery(ProductEntity::class.java)
        val root: Root<ProductEntity> = query.from(ProductEntity::class.java)

        val predicates = mutableListOf<Predicate>()
        command.name?.let { predicates.add(cb.like(root.get<String>("name"), "%$it%")) }
        command.description?.let {
            predicates.add(cb.like(root.get<String>("description"), "%$it%"))
        }
        command.price?.let {
            predicates.add(cb.equal(cb.sum<Int>(root.get<Int>("price"), root.get<Int>("fee")), it))
        }
        command.tag?.let { predicates.add(cb.isMember(it, root.get<Set<String>>("tags"))) }

        return em.createQuery(query.select(root).where(cb.or(*predicates.toTypedArray())))
            .resultStream
            .map { mapToProduct(it) }
            .toList()
    }

    override fun find(id: UUID): Product? {
        return try {
            mapToProduct(em.find(ProductEntity::class.java, id))
        } catch (_: EntityNotFoundException) {
            null
        }
    }

    private fun mapToProduct(row: ProductEntity): Product =
        Product(
            row.id,
            row.name,
            row.description,
            ProductPrice(
                Money(row.price.toDouble() / 100, row.currency),
                Money(row.fee.toDouble() / 100, row.feeCurrency)
            ),
            row.tags
        )
}
