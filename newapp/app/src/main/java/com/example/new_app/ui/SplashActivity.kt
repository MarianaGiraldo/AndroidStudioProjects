package com.example.new_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.new_app.MainActivity
import com.example.new_app.R
import kotlinx.android.synthetic.main.activity_splash.*

private const val time:Long=400
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout. activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, time)

        var animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        ivSenaLogo.startAnimation(animation)

        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

    }
}
