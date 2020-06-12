package com.tanzy.sherlockdemo.radar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tanzy.sherlockdemo.R

/**
 * Created by PPTing on 2020/5/11.
 * Description: 雷达能力五边形 View
 */
open class RadarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    companion object{
        private const val COUNT = 5
    }

    private var angle: Float = 0f//角度
    private var radius: Float = 0f//半径

    private var mStrokeWidth = 1f
    private var mStrokeColor = Color.BLACK
    private var mRegionStartColor = Color.WHITE
    private var mRegionEndColor = Color.BLACK

    //圆心 X 坐标
    private var centerX = 0
    //圆心 Y 坐标
    private var centerY = 0

    private val polygonPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth
            color = mStrokeColor
        }
    }

    private val regionPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
    }


    init {
        angle = (Math.PI * 2/ COUNT).toFloat()

//        val typeAttr = context.obtainStyledAttributes(attrs, R.styleable.ui_RadarView)
//        mStrokeWidth = typeAttr.getDimension(R.styleable.ui_RadarView_ui_stroke_width,1f)
//        mStrokeColor = typeAttr.getColor(R.styleable.ui_RadarView_ui_stroke_color, Color.BLACK)
//        mRegionStartColor = typeAttr.getColor(R.styleable.ui_RadarView_ui_region_start_color, Color.BLACK)
//        mRegionEndColor = typeAttr.getColor(R.styleable.ui_RadarView_ui_region_end_color, Color.WHITE)
//        typeAttr.recycle()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = Math.min(h, w) / 2f
        centerX = w / 2
        centerY = h / 2
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawPolygon(it)
            drawRegion(it)
        }

    }

    /**
     * 绘制最外层的正五边形
     */
    private fun drawPolygon(canvas: Canvas){
        val path = Path()
        for (i in 0 until COUNT){
            if (i == 0){
                path.moveTo(centerX.toFloat(),centerY - radius)
            }else{
                val x = (centerX + Math.sin(angle.toDouble() * i) * radius).toFloat()
                val y = (centerY - Math.cos(angle.toDouble() * i) * radius).toFloat()
                path.lineTo(x,y)
            }
        }
        path.close()
        canvas.drawPath(path,polygonPaint)
    }



    /**
     * 填充能力值
     */
    private fun drawRegion(canvas: Canvas){
        val path = Path()

        for (i in 0 until COUNT){
            if (i == 0){
                path.moveTo(centerX.toFloat(), (centerY - radius * percents[i]))
            }else{
                val x = (centerX + Math.sin(angle.toDouble() * i) * (percents[i] * radius))
                val y = (centerY - Math.cos(angle.toDouble() * i) * (percents[i] * radius))
                path.lineTo(x.toFloat(), y.toFloat())
            }
        }

        path.close()
        regionPaint.setShader(RadialGradient(centerX.toFloat(),centerY.toFloat(),radius,
                mRegionStartColor,mRegionEndColor, Shader.TileMode.CLAMP))
        canvas.drawPath(path, regionPaint)
    }

    private val percents = FloatArray(5).apply {
        set(0,0.5f)
        set(1,0.8f)
        set(2,0.8f)
        set(3,0.6f)
        set(4,0.8f)
    }


    /**
     * 设置描边宽度
     * @param width 宽度值
     */
    fun setStrokeWidth(width: Float){
        mStrokeWidth = width
    }

    /**
     * 设置描边颜色
     */
    fun setStrokeColor(color: Int){
        mStrokeColor = color
    }

    /**
     * 设置填充区域的渐变起始和结束颜色
     */
    fun setRegionGradientColor(startColor: Int,endColor: Int){
        mRegionStartColor = startColor
        mRegionEndColor = endColor
    }

    /**
     * 设置五边形的五个角度，a-e 的值需要在0-1之间
     * 总分为 100，10分则传 0.1f 22分则传 0.22f
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    fun setValue(a: Float,b: Float,c: Float,d: Float,e: Float){
        if (!checkValueRight(a) || !checkValueRight(b) || !checkValueRight(c) || !checkValueRight(d)|| !checkValueRight(e)){
            Log.e("RadarView","please pass value from 0 to 1")
            return
        }
        percents.set(0,a)
        percents.set(1,b)
        percents.set(2,c)
        percents.set(3,d)
        percents.set(4,e)
        invalidate()
    }



    /**
     * 检查数据
     */
    private fun checkValueRight(value: Float): Boolean{
        return value in 0f..1f
    }
}
