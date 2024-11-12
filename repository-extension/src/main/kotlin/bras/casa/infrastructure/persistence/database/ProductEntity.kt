/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence.database

import bras.casa.domain.model.Product
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.util.UUID
import org.hibernate.annotations.UuidGenerator

@Entity
internal class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "ID")
    lateinit var id: UUID

    @Column(name = "NAME", length = 100, nullable = false) lateinit var name: String

    @Column(name = "DESCRIPTION", length = 300, nullable = false) lateinit var description: String

    @Column(name = "PRODUCT_PRICE", nullable = false) lateinit var price: Integer

    @Column(name = "PRODUCT_CURRENCY", nullable = false) lateinit var currency: String

    @Column(name = "FEE_PRICE", nullable = false) lateinit var fee: Integer

    @Column(name = "FEE_CURRENCY", nullable = false) lateinit var feeCurrency: String

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PRODUCT_TAGS", joinColumns = [JoinColumn(name = "PRODUCT")])
    lateinit var tags: Set<String>

    companion object Factory {

        fun create(product: Product): ProductEntity {
            val productEntity = ProductEntity()
            productEntity.id = product.id
            productEntity.name = product.name
            productEntity.price = (product.price.baseCost.amount * 100).toInt() as Integer
            productEntity.currency = product.price.baseCost.currency
            productEntity.fee = (product.price.fee.amount * 100).toInt() as Integer
            productEntity.feeCurrency = product.price.fee.currency
            productEntity.tags = product.tags

            return productEntity
        }
    }
}
