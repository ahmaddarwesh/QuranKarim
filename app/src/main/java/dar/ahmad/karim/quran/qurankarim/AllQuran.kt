package dar.ahmad.karim.quran.qurankarim

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_all_quran.*
import org.json.JSONArray
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class AllQuran : AppCompatActivity() {
    var arr = ArrayList<model>()
    var res = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_quran)


        val res = resources

        val inStream = res.openRawResource(R.raw.surah)
        val scan = Scanner(inStream)

        val sb = StringBuilder()
        while (scan.hasNextLine()) {
            sb.append(scan.nextLine())
        }
        jsonBulder(sb.toString())

    }

    private fun jsonBulder(sb: String) {

        val root = JSONArray(sb)

        for (i in 0 until root.length()) {
            val obj = root.getJSONObject(i)

            val type = obj.getString("type")
            val count = obj.getString("count")
            val titleAr = obj.getString("titleAr")
            val index = obj.getString("index")

            arr.add(model(type, count, titleAr, index))


        }

        recy.layoutManager = GridLayoutManager(this, 2)
        recy.adapter = Jdapter(arr, this)


        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                res.clear()
                for (i in 0 until arr.size) {
                    if (arr[i].titleAr.contains(p0!!.toString())) {
                        res.add(arr[i])
                    }
                }
                recy.adapter = Jdapter(res, this@AllQuran)
            }

        })
    }


    override fun onResume() {
        super.onResume()
        recy.adapter!!.notifyDataSetChanged()
    }

}

class Jdapter(var list: ArrayList<model>, var conx: Context) : RecyclerView.Adapter<Jdapter.HolderClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HolderClass {
        val myView = LayoutInflater.from(conx).inflate(R.layout.card_soura, p0, false)
        return HolderClass(myView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: HolderClass, p1: Int) {
        val myL = list[p1]
        p0.name.text = myL.titleAr

        val ind = myL.index.toInt() + 0
        p0.index.text = ind.toString()

        p0.count.text = myL.count

        if (myL.type == "Makkiyah") {
            p0.type.text = "مكية"
        } else {
            p0.type.text = "مدنية"
        }

        p0.card.setOnClickListener {

            conx.startActivity(
                Intent(conx, viewSoura::class.java)
                    .putExtra("idSoura", ind)
                    .putExtra("name", myL.titleAr)
                    .putExtra("count", myL.count)
            )
        }

        if (SharedP.getInt(conx, "mode") == 0) {
            p0.card.setCardBackgroundColor(Color.WHITE)

            p0.type.setTextColor(Color.BLACK)
            p0.count.setTextColor(Color.BLACK)
            p0.index.setTextColor(Color.BLACK)
        } else {
            p0.card.setCardBackgroundColor(Color.DKGRAY)
            p0.type.setTextColor(Color.WHITE)
            p0.count.setTextColor(Color.WHITE)
            p0.index.setTextColor(Color.WHITE)
        }

    }


    class HolderClass(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.name_soura)
        var index = view.findViewById<TextView>(R.id.index_soura)
        var count = view.findViewById<TextView>(R.id.ayat_soura)
        var type = view.findViewById<TextView>(R.id.type_sura)
        var card = view.findViewById<CardView>(R.id.card_lay)

    }


}


data class model(
    var type: String, var count: String,
    var titleAr: String, var index: String

)
