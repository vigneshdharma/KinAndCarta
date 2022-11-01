package com.kinandcarta.data.model

import com.google.gson.annotations.SerializedName
import com.kinandcarta.domain.model.Articles
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.domain.model.Sections

data class ArticlesEntity(@SerializedName("case_studies") var caseStudies: ArrayList<CaseStudiesEntity> = arrayListOf()) {
    fun toArticle() = Articles(caseStudies.map { it.toDomainCaseStudies() })
}

data class CaseStudiesEntity(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("client") var client: String? = null,
    @SerializedName("teaser") var teaser: String? = null,
    @SerializedName("vertical") var vertical: String? = null,
    @SerializedName("is_enterprise") var isEnterprise: Boolean? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("hero_image") var heroImage: String? = null,
    @SerializedName("sections") var sections: MutableList<SectionsEntity> = arrayListOf()
) {
    fun toDomainCaseStudies(): CaseStudies? {
        val sections = sections.map { it.toDomainSections() }
        return if (teaser.isNullOrBlank() || heroImage.isNullOrBlank())
            null
        else
            CaseStudies(id, client, teaser, vertical, isEnterprise, title, heroImage, sections)
    }
}

data class SectionsEntity(
    @SerializedName("title") val title: String? = null,
    @SerializedName("body_elements") val body_elements: List<Any?> = listOf()
) {
    fun toDomainSections(): Sections {
        return Sections(title, body_elements)
    }
}
