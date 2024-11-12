/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence.search.extensions

import io.quarkus.hibernate.search.standalone.elasticsearch.SearchExtension
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer

@SearchExtension
internal class AnalysisSearchExtension : ElasticsearchAnalysisConfigurer {

    override fun configure(context: ElasticsearchAnalysisConfigurationContext?) {
        context
            ?.analyzer(ENGLISH)
            ?.custom()
            ?.tokenizer("standard")
            ?.tokenFilters("asciifolding", "lowercase", "porter_stem")
    }

    companion object Analyzers {
        const val ENGLISH = "english"
        const val KEYWORD = "keyword"
    }
}
