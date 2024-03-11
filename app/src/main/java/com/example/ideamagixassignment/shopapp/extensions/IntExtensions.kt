package com.example.ideamagixassignment.shopapp.extensions

import android.content.res.Resources
import androidx.annotation.Dimension

@Dimension(unit = Dimension.DP)
fun Int.toDP(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

@Dimension(unit = Dimension.PX)
fun Int.toPX(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()