package com.dima.vkclient.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {

    init {
        // Наша viewgroup только компонует детей и не будет рисовать
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0
//        Сколько в данный момент занимают дети на текущей строке чтобы знать когда перенести
        var currentRowWidth = 0

//        Максимальная высота ребенка в строке
        var maxChildHeight = 0

        children.forEach { child ->
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, height)

            currentRowWidth += child.measuredWidth  // увеличили значение ширины по текущей строке

//            Если у нас currentRowWidth стал больше чем та ширина которую нам отдал родитель под
//            эту вью, то нам нужно перенестись на следующюю строку
            if (currentRowWidth > desiredWidth) {
                currentRowWidth = child.measuredWidth
                height += maxChildHeight
                maxChildHeight = 0
            }
            maxChildHeight = max(child.measuredHeight, maxChildHeight)
        }

        height += maxChildHeight

        setMeasuredDimension(desiredWidth, View.resolveSize(height, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = paddingLeft
        var currentTop = t + paddingTop

//        Нижняя координата ребенка с максимальной высотой в строке
        var bottomMaxChildInLineCoordinate = 0

        children.forEach { child ->
            val currentRight = currentLeft + child.measuredWidth
            if (currentRight > measuredWidth) {
                currentLeft = paddingLeft

                currentTop += bottomMaxChildInLineCoordinate
                bottomMaxChildInLineCoordinate = 0
            }

            bottomMaxChildInLineCoordinate =
                max(child.measuredHeight, bottomMaxChildInLineCoordinate)

            child.layout(currentLeft, currentTop, currentRight, currentTop + child.measuredHeight)
            currentLeft += child.measuredWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?) =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

    override fun generateDefaultLayoutParams() =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
}