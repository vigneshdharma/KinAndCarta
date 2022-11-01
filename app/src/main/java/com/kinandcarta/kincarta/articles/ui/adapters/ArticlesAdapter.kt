package com.kinandcarta.kincarta.articles.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinandcarta.kincarta.R
import com.kinandcarta.kincarta.base.extension.inflate
import com.kinandcarta.kincarta.base.extension.loadFromUrl
import com.kinandcarta.kincarta.navigation.Navigator
import kotlinx.android.synthetic.main.row_article.view.*
import com.kinandcarta.domain.model.CaseStudies
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticlesAdapter
@Inject constructor() : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    internal var collection: List<CaseStudies> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (CaseStudies, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_article))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(caseStudies: CaseStudies, clickListener: (CaseStudies, Navigator.Extras) -> Unit) {
            caseStudies.heroImage?.let { itemView.articlePoster.loadFromUrl(it) }
            itemView.articleTitle.text = caseStudies.teaser
            itemView.setOnClickListener {
                clickListener(
                    caseStudies,
                    Navigator.Extras(itemView.articlePoster)
                )
            }
        }
    }
}
