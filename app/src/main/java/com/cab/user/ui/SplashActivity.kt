package com.cab.user.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cab.user.R
import com.cab.user.utils.Constant
import com.cab.user.utils.Utils

class SplashActivity : AppCompatActivity() {

    // Splash screen timer
    private val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViews()

    }

    private fun initViews() {

        if (Utils.checkPermissions(this@SplashActivity,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))) {

            continueRoutine()

        } else {

            Utils.requestPermissions(this@SplashActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
        }

    }

    internal fun continueRoutine() {

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    override fun onBackPressed() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUESTS.USER_PERMISSION -> {

                var granted = true

                for (i in permissions.indices) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        granted = false

                        break
                    }
                }

                if (granted) {

                    continueRoutine()
                } else {

                    Utils.requestPermissions(this@SplashActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))

                }
            }
        }


    }

}
