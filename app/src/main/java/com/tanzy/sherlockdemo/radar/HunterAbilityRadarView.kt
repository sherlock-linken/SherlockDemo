package com.tanzy.sherlockdemo.radar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tanzy.sherlockdemo.R


/**
 * Created by tanzy on 2020/5/14.
 */
class HunterAbilityRadarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    companion object {
        private const val COUNT = 5
    }

    private var angle: Float = 0f//角度
    private var radius: Float = 0f//半径

    private var mStrokeWidth = 1f
    private var mStrokeColor = Color.BLACK
    private var mStrokeInsideWidth = 1f
    private var mStrokeInsideColor = Color.BLACK
    private var mStorkeInsidelayerCount = 0
    private var mStorkeCenterToConner = false
    private var mRegionStartColor = Color.WHITE
    private var mRegionEndColor = Color.BLACK
    private var mRegionStrokeWidth = 1f
    private var mRegionStrokeColor = Color.BLACK
    private var mRegionCenterX = 1.3f
    private var mRegionCenterY = 0.7f

    //圆心 X 坐标
    private var centerX = 0
    //圆心 Y 坐标
    private var centerY = 0

    //外框画笔
    private val polygonPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth
            color = mStrokeColor
        }
    }

    //内框画笔
    private val polygonInsidePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mStrokeInsideWidth
            color = mStrokeInsideColor
        }
    }

    //内填充画笔
    private val regionPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = mRegionStrokeWidth
            color = mRegionStrokeColor
        }
    }

    init {
        angle = (Math.PI * 2 / COUNT).toFloat()

        val typeAttr = context.obtainStyledAttributes(attrs, R.styleable.ui_HunterRadarView)
        mStrokeWidth = typeAttr.getDimension(R.styleable.ui_HunterRadarView_ui_stroke_width, 1f)
        mStrokeColor = typeAttr.getColor(R.styleable.ui_HunterRadarView_ui_stroke_color, Color.BLACK)
        mStrokeInsideWidth = typeAttr.getDimension(R.styleable.ui_HunterRadarView_ui_stroke_inside_width, 1f)
        mStrokeInsideColor = typeAttr.getColor(R.styleable.ui_HunterRadarView_ui_stroke_inside_color, Color.BLACK)
        mStorkeInsidelayerCount = typeAttr.getInt(R.styleable.ui_HunterRadarView_ui_stroke_inside_layer_count, 0)
        mStorkeCenterToConner = typeAttr.getBoolean(R.styleable.ui_HunterRadarView_ui_stroke_center_to_conner, false)
        mRegionCenterX = typeAttr.getFloat(R.styleable.ui_HunterRadarView_ui_region_center_x, 1f)
        mRegionCenterY = typeAttr.getFloat(R.styleable.ui_HunterRadarView_ui_region_center_y, 1f)
        mRegionStrokeWidth = typeAttr.getDimension(R.styleable.ui_HunterRadarView_ui_stroke_region_width, 0f)
        mRegionStrokeColor = typeAttr.getColor(R.styleable.ui_HunterRadarView_ui_stroke_color, Color.BLACK)
        mRegionStartColor = typeAttr.getColor(R.styleable.ui_HunterRadarView_ui_region_start_color, Color.parseColor("#fff5f5"))
        mRegionEndColor = typeAttr.getColor(R.styleable.ui_HunterRadarView_ui_region_end_color, Color.parseColor("#ff8080"))
        typeAttr.recycle()

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
     * 绘制背景正五边形
     */
    private fun drawPolygon(canvas: Canvas) {

        //绘制最外层五边形
        val pathOut = Path()
        for (i in 0 until COUNT) {
            if (i == 0) {
                pathOut.moveTo(centerX.toFloat(), centerY - radius)
            } else {
                val x = (centerX + Math.sin(angle.toDouble() * i) * radius).toFloat()
                val y = (centerY - Math.cos(angle.toDouble() * i) * radius).toFloat()
                pathOut.lineTo(x, y)
            }
        }
        pathOut.close()
        canvas.drawPath(pathOut, polygonPaint)

        //绘制内部层级边框
        if (mStorkeInsidelayerCount > 0) {
            for (c in 1 until mStorkeInsidelayerCount + 1) {
                val r = radius - c * radius / (mStorkeInsidelayerCount + 1)
                val pathIn = Path()
                for (i in 0 until COUNT) {
                    if (i == 0) {
                        pathIn.moveTo(centerX.toFloat(), centerY - r)
                    } else {
                        val x = (centerX + Math.sin(angle.toDouble() * i) * r).toFloat()
                        val y = (centerY - Math.cos(angle.toDouble() * i) * r).toFloat()
                        pathIn.lineTo(x, y)
                    }
                }
                pathIn.close()
                canvas.drawPath(pathIn, polygonInsidePaint)
            }
        }

        //绘制中心到角的线段
        if (mStorkeCenterToConner) {
            val path = Path()
            for (i in 0 until COUNT) {
                path.moveTo(centerX.toFloat(), centerY.toFloat())
                val x = (centerX + Math.sin(angle.toDouble() * i) * radius).toFloat()
                val y = (centerY - Math.cos(angle.toDouble() * i) * radius).toFloat()
                path.lineTo(x, y)
            }
            path.close()
            canvas.drawPath(path, polygonInsidePaint)
        }
    }

    /**
     * 填充能力值
     */
    private fun drawRegion(canvas: Canvas) {
        val path = Path()

        for (i in 0 until COUNT) {
            if (i == 0) {
                path.moveTo(centerX.toFloat(), (centerY - radius * percents[i]))
            } else {
                val x = (centerX + Math.sin(angle.toDouble() * i) * (percents[i] * radius))
                val y = (centerY - Math.cos(angle.toDouble() * i) * (percents[i] * radius))
                path.lineTo(x.toFloat(), y.toFloat())
            }
        }

        path.close()
        regionPaint.setShader(
            RadialGradient(
                centerX.toFloat() * mRegionCenterX, centerY.toFloat() * mRegionCenterY, radius * 1.5f,
                mRegionStartColor, mRegionEndColor, Shader.TileMode.CLAMP
            )
        )
        canvas.drawPath(path, regionPaint)
    }

    private val percents = FloatArray(5).apply {
        set(0, 0.5f)
        set(1, 0.8f)
        set(2, 0.8f)
        set(3, 0.6f)
        set(4, 0.8f)
    }


    /**
     * 设置描边宽度和颜色
     * @param width 宽度值
     */
    fun setStroke(width: Float, color: Int) {
        mStrokeWidth = width
        mStrokeColor = color
    }

    /**
     * 设置内描边宽度和颜色
     * @param width 宽度值
     */
    fun setStrokeInside(width: Float, color: Int) {
        mStrokeInsideWidth = width
        mStrokeInsideColor = color
    }

    /**
     * 设置填充区域描边宽度和颜色
     * @param width 宽度值
     */
    fun setStrokeRegion(width: Float, color: Int) {
        mRegionStrokeWidth = width
        mRegionStrokeColor = color
    }

    /**
     * 图形内层数
     * */
    fun setStorkeInsideLayerCount(count: Int) {
        mStorkeInsidelayerCount = count
    }

    /**
     * 是否绘制图形中心到各个角落的线段
     * */
    fun setStorkeCenterToConner(show: Boolean) {
        mStorkeCenterToConner = show
    }

    /**
     * 设置渐变中心位置，（1,1）为图形正中心
     * @param x 位置，半径的倍数
     * @param y 位置，半径的倍数
     * */
    fun setRegionCenter(x: Float, y: Float) {
        mRegionCenterX = x
        mRegionCenterY = y
    }

    /**
     * 设置填充区域的渐变起始和结束颜色
     */
    fun setRegionGradientColor(startColor: Int, endColor: Int) {
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
    fun setValue(a: Float, b: Float, c: Float, d: Float, e: Float) {
        if (!checkValueRight(a) || !checkValueRight(b) || !checkValueRight(c) || !checkValueRight(d) || !checkValueRight(e)) {
            Log.e("RadarView", "please pass value from 0 to 1")
            return
        }
        percents.set(0, a)
        percents.set(1, b)
        percents.set(2, c)
        percents.set(3, d)
        percents.set(4, e)
        invalidate()
    }


    /**
     * 检查数据
     */
    private fun checkValueRight(value: Float): Boolean {
        return value in 0f..1f
    }


}