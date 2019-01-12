package dar.ahmad.karim.quran.qurankarim

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_soura.*
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.*


class viewSoura : AppCompatActivity() {
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_soura)

        id = intent.extras!!.get("idSoura")!!.toString().toInt()
        val name = intent.extras!!.getString("name")
        val count = intent.extras!!.getString("count")


        val res = resources

        val inStream = res.openRawResource(R.raw.quean)
        val scan = Scanner(inStream)

        val sb = StringBuilder()
        while (scan.hasNextLine()) {
            sb.append(scan.nextLine())
        }
        jsonBulderQ(sb.toString(), id!!.toString().toInt() - 1)

        name_sura.text = name
        count_aya.text = count

        save.setOnClickListener {
            val scrollY = sv.scrollY
            SharedP.saveInt(this, "scroll$id", scrollY)

            Toast.makeText(this, "Saved on ${scrollY / 10} ", Toast.LENGTH_LONG).show()

        }

        if (SharedP.getInt(this, "scroll$id") != 0) {
            val h = Handler()
            h.postDelayed(
                {
                    sv.smoothScrollTo(0, SharedP.getInt(this, "scroll$id"))
                },
                100
            )
            Toast.makeText(this, "Received ${SharedP.getInt(this, "scroll$id") / 10}", Toast.LENGTH_LONG).show()
        }

        zoomIn.setOnClickListener {
            if (viewSource.textSize != 126.0f) {
                viewSource.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewSource.textSize + 10f)
            } else {

            }

        }

        zoomOut.setOnClickListener {
            if (viewSource.textSize != 26.0f) {
                viewSource.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewSource.textSize - 10f)
            } else {

            }
        }

        shareContent.setOnClickListener {

            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, viewSource.text.toString())
            startActivity(sharingIntent)

        }



        mode.setOnClickListener {
            if (SharedP.getInt(this, "mode") == 0) {
                SharedP.saveInt(this, "mode", 1)
                mode.setImageResource(R.drawable.ic_night)
                checkMode()
            } else if (SharedP.getInt(this, "mode") == 1) {
                SharedP.saveInt(this, "mode", 0)
                mode.setImageResource(R.drawable.ic_wb_sunny_black_24dp)
                checkMode()
            }

        }


        //First check mode

        if (SharedP.getInt(this, "mode") == 0) {
            cons_viewS.setBackgroundColor(Color.WHITE)
            img_setting.setImageResource(R.drawable.ic_settings_black_24dp)
            mode.setImageResource(R.drawable.ic_wb_sunny_black_24dp)
            name_sura.setTextColor(Color.DKGRAY)
            viewSource.setTextColor(Color.BLACK)
        } else {
            img_setting.setImageResource(R.drawable.ic_settings_black_24dp_w)
            mode.setImageResource(R.drawable.ic_night)
            cons_viewS.setBackgroundColor(Color.DKGRAY)
            name_sura.setTextColor(Color.WHITE)
            viewSource.setTextColor(Color.WHITE)
        }

        var i = 0
        img_setting.setOnClickListener {
            if (i == 0) {
                cons_setting.visibility = View.VISIBLE
                i = 1
            } else {
                cons_setting.visibility = View.GONE
                i = 0
            }
        }

    }

    private fun jsonBulderQ(sb: String, id: Int?) {

        val root = JSONObject(sb)
        val quran = root.getJSONObject("quran")
        val sura = quran.getJSONArray("sura")

        var c = String()
        for (i in 0 until sura.length()) {
            if (i == id) {
                var obj = sura.getJSONObject(i)
                var ayat = obj.getJSONArray("aya")
                for (j in 0 until ayat.length()) {
                    val aya = ayat.getJSONObject(j).getString("text")
                    c = "$c $aya (${j + 1})"
                }
                c = "$c \n \n {صدق الله العظيم}  "
            }
        }

        viewSource.text = c
    }

    fun checkMode() {
        if (SharedP.getInt(this, "mode") == 0) {
            cons_viewS.setBackgroundColor(Color.WHITE)
            img_setting.setImageResource(R.drawable.ic_settings_black_24dp)
            name_sura.setTextColor(Color.DKGRAY)
            viewSource.setTextColor(Color.BLACK)
        } else {
            img_setting.setImageResource(R.drawable.ic_settings_black_24dp_w)
            cons_viewS.setBackgroundColor(Color.DKGRAY)
            name_sura.setTextColor(Color.WHITE)
            viewSource.setTextColor(Color.WHITE)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val scrollY = sv.scrollY
        SharedP.saveInt(this, "scroll$id", scrollY)

    }

}
