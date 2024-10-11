/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import jakarta.persistence.criteria.Predicate

class OrSearchFilter<ENTITY>(val left: SearchFilter<ENTITY>, val right: SearchFilter<ENTITY>) :
    SearchFilter<ENTITY> {

    override fun apply(parser: SearchFilterParser<ENTITY>): Predicate = parser.apply(this)
}
