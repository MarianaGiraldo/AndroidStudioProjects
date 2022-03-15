package com.example.new_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.new_app.MainActivity
import com.example.new_app.R
import kotlinx.android.synthetic.main.activity_splash.*

private const val time:Long=1600
@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout. activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, time)

        var animationText = AnimationUtils.loadAnimation(this, R.anim.anim)
        tvSplashText.startAnimation(animationText)

        var animationLogo = AnimationUtils.loadAnimation(this, R.anim.animlogo)
        ivSenaLogo.startAnimation(animationLogo)

        animationText.setAnimationListener(object: Animation.AnimationListener{
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
