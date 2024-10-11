/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model.employee

import java.util.UUID

data class EmployeeId(private val id: UUID) {
    constructor() : this(UUID.randomUUID())

    fun toUUID() = id
}
