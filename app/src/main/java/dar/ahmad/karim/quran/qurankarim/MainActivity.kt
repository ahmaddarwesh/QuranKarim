package dar.ahmad.karim.quran.qurankarim

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        mstlhat.setOnClickListener {
            startActivity(Intent(this, alamat_dabt::class.java).putExtra("ID", 0))
        }

        quran.setOnClickListener {
            startActivity(Intent(this, AllQuran::class.java))

        }


        Azkar.setOnClickListener {
            startActivity(Intent(this, alamat_dabt::class.java).putExtra("ID", 1))

        }

        Setting.setOnClickListener {
            startActivity(Intent(this, dar.ahmad.karim.quran.qurankarim.Setting::class.java))
        }

        about.setOnClickListener {
            var d = Dialog(this)

            d.setCancelable(true)
            d.setContentView(R.layout.card_programmer)
            d.setOnCancelListener {
                d.dismiss()
            }
            d.show()
        }

    }


    override fun onResume() {
        super.onResume()
        if (SharedP.getInt(this, "mode") == 0) {
            cons_main.setBackgroundColor(Color.WHITE)
        } else {
            cons_main.setBackgroundColor(Color.DKGRAY)
        }
    }

}
