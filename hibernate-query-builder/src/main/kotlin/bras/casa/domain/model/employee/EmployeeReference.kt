/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model.employee

data class EmployeeReference(private val value: String) {
    override fun toString(): String = value
}
