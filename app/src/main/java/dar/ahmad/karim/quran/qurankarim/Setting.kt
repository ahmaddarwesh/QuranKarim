package dar.ahmad.karim.quran.qurankarim

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        valueLive.text = SharedP.getInt(this, "fontSize").toString()
        seek.progress = SharedP.getInt(this, "fontSize")

        if (SharedP.getInt(this, "mode") == 0) {
            lightmode.imageAlpha = 50
            darkmode.imageAlpha = 250
            name_mode.text = "الوضع الفاتح"
        } else {
            darkmode.imageAlpha = 50
            lightmode.imageAlpha = 250
            name_mode.text = "الوضع الداكن"
        }

        lightmode.setOnClickListener {
            Toast.makeText(this, "Mode has been changed to Light mode", Toast.LENGTH_LONG).show()
            SharedP.saveInt(this, "mode", 0)
            lightmode.imageAlpha = 50
            darkmode.imageAlpha = 250
            name_mode.text = "الوضع الفاتح"
        }

        darkmode.setOnClickListener {
            Toast.makeText(this, "Mode has been changed to Dark mode", Toast.LENGTH_LONG).show()
            SharedP.saveInt(this, "mode", 1)
            darkmode.imageAlpha = 50
            lightmode.imageAlpha = 250
            name_mode.text = "الوضع الداكن"
        }


        seek.max = 255

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                valueLive.text = "$progress"

                try {
                    android.provider.Settings.System.putInt(
                        contentResolver,
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress
                    )
                } catch (e: Exception) {
                }

                SharedP.saveInt(this@Setting, "fontSize", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }
}
