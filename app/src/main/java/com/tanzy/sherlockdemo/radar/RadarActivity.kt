package com.tanzy.sherlockdemo.radar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tanzy.sherlockdemo.R
import kotlinx.android.synthetic.main.activity_radar.*

class RadarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)

        harv_hunter.setValue(1f, 0.5f, 0.2f, 0.7f, 0.4f)
        harv_hunter.setStroke(3f, Color.parseColor("#aad1ff"))
        harv_hunter.setStorkeInsideLayerCount(2)
        harv_hunter.setStorkeCenterToConner(true)
        harv_hunter.setStrokeInside(1.5f, Color.parseColor("#d2ebff"))
        harv_hunter.setRegionGradientColor(Color.parseColor("#fff9f9"), Color.parseColor("#84c5ff"))

    }
}
