/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import jakarta.persistence.criteria.Predicate

sealed interface SearchFilter<ENTITY> {

    fun and(other: SearchFilter<ENTITY>): SearchFilter<ENTITY> = AndSearchFilter(this, other)

    fun or(other: SearchFilter<ENTITY>): SearchFilter<ENTITY> = OrSearchFilter(this, other)

    fun apply(parser: SearchFilterParser<ENTITY>): Predicate
}
