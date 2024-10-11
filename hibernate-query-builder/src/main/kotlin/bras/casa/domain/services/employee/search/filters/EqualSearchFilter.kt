/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import jakarta.persistence.criteria.Predicate

sealed class EqualSearchFilter<ENTITY>(val value: Any) : SearchFilter<ENTITY> {

    override fun apply(parser: SearchFilterParser<ENTITY>): Predicate = parser.apply(this)
}
