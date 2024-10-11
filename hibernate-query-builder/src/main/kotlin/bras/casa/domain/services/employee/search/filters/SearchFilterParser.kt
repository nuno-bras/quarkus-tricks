/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import jakarta.persistence.criteria.Predicate

interface SearchFilterParser<ENTITY> {

    fun apply(filter: AndSearchFilter<ENTITY>): Predicate

    fun apply(filter: OrSearchFilter<ENTITY>): Predicate

    fun apply(filter: EqualSearchFilter<ENTITY>): Predicate
}
