package com.dima.vkclient.ui.post.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R

class PostItemDecoration(
    private val sectionCallback: SectionCallback
) :
    RecyclerView.ItemDecoration() {

    private var dateDecorator: View? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }
        if (sectionCallback.isSection(position)) {
            outRect.top =
                getDateDecoration(parent).height
        }

        if (sectionCallback.isNotLastItemInSection(position)) {
            outRect.bottom =
                parent.context.resources.getDimensionPixelSize(R.dimen.margin_between_posts)
        }

    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val header = getDateDecoration(parent).findViewById<TextView>(R.id.text_header_name)

        parent.children
            .forEach { childItemRecyclerView ->
                val childPosition = parent.getChildAdapterPosition(childItemRecyclerView)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                val title = sectionCallback.getSectionHeaderName(childPosition)
                header.text = title
                fixLayoutSize(header, parent)
                if (sectionCallback.isSection(childPosition)) {
                    drawHeader(canvas, childItemRecyclerView, header, parent)
                }
            }
    }

    private fun getDateDecoration(parent: ViewGroup): View {
        if (dateDecorator == null) {
            dateDecorator =
                LayoutInflater.from(parent.context).inflate(R.layout.section_header, parent, false)

            fixLayoutSize(dateDecorator!!, parent)
        }

        return dateDecorator!!
    }

    private fun fixLayoutSize(child: View, parent: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            parent.width,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            parent.height,
            View.MeasureSpec.UNSPECIFIED
        )

        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            child.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            child.layoutParams.height
        )

        child.measure(childWidth, childHeight)
        child.layout(0, 0, child.measuredWidth, child.measuredHeight)
    }

    private fun drawHeader(canvas: Canvas, child: View, headerView: View, parent: ViewGroup) {
        canvas.save()

        canvas.translate(
            (child.measuredWidth / 2 - headerView.measuredWidth / 2).toFloat(),
            (child.top - headerView.measuredHeight / 2 - getDateDecoration(parent).height / 2).toFloat()
        )

        headerView.draw(canvas)

        canvas.restore()
    }

    interface SectionCallback {
        fun isSection(position: Int): Boolean
        fun isNotLastItemInSection(position: Int): Boolean
        fun getSectionHeaderName(position: Int): String
    }
}