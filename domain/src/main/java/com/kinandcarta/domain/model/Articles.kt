package com.kinandcarta.domain.model

data class Articles(val caseStudies: List<CaseStudies?> = arrayListOf()) {
    companion object {
        val empty = Articles(arrayListOf())
    }
}

data class CaseStudies(
    var id: Int? = null,
    var client: String? = null,
    var teaser: String? = null,
    var vertical: String? = null,
    var isEnterprise: Boolean? = null,
    var title: String? = null,
    var heroImage: String? = null,
    var sections: List<Sections> = arrayListOf()
) : java.io.Serializable

data class Sections(
    val title: String? = null,
    val body_elements: List<Any?> = listOf()
) : java.io.Serializable

data class BodyElements(
    var url: String? = null
) : java.io.Serializable

data class ImageUrl(var url: String? = null)

fun String.Companion.empty() = ""