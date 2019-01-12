package dar.ahmad.karim.quran.qurankarim

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedP {
    companion object {
        @SuppressLint("CommitPrefEdits")
        fun saveInt(conx:Context, key:String, value:Int){
            val share = conx.getSharedPreferences("Setting",MODE_PRIVATE)
            val editor = share.edit()
            editor.putInt(key,value)
            editor.apply()
        }

        fun getInt(conx: Context,key: String):Int{
            val share = conx.getSharedPreferences("Setting",MODE_PRIVATE)
            return share.getInt(key,0)
        }


        fun saveString(conx: Context,key: String,value:String){
            val share = conx.getSharedPreferences("Setting",MODE_PRIVATE)
            val editor = share.edit()
            editor.putString(key,value)
            editor.apply()
        }

        fun getString(conx: Context,key: String):String{
            val share = conx.getSharedPreferences("Setting",MODE_PRIVATE)
            return share.getString(key,null)!!
        }
    }
}