/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model.department

import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class DepartmentId(private val id: UUID) {
    constructor() : this(UUID.randomUUID())

    fun toUUID() = id
}
