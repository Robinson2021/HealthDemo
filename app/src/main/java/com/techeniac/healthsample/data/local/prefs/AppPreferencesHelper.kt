package com.techeniac.healthsample.data.local.prefs

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.techeniac.healthsample.data.DataManager
import com.techeniac.healthsample.di.annotations.PreferenceInfo
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

/**
 * Created by Robinson on 18 Nov 2021
 */
class AppPreferencesHelper @Inject constructor(
    val context: Context,
    @PreferenceInfo val prefFileName: String
) :
    PreferencesHelper {

    private val PREF_KEY_VALUE = "PREF_KEY_VALUE"

    val mPrefs = PreferenceManager.getDefaultSharedPreferences(context)


   /*  override fun getUser(): UserModel? = getComplex(PREF_KEY_USER, UserModel::class.java)
     override fun setUser(model: UserModel?) {
         saveComplex(PREF_KEY_USER,model)
     }
*/

    override fun setIntValue(value: Int) {
       // saveComplex(PREF_KEY_USER, value)
        mPrefs.edit().putInt(PREF_KEY_VALUE, value).apply()
    }


    override fun getIntValue(): Int =
        mPrefs.getInt(PREF_KEY_VALUE, 0)



    fun <T> saveComplex(key: String, complex: T): Boolean {
        val editor = mPrefs.edit()
        editor.putString(key, Gson().toJson(complex))
        return editor.commit()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getComplex(key: String, type: Class<T>): T? {
        val data = mPrefs.getString(key, null)
        if (data != null && data != "null") {
            val mJson = JsonParser().parse(data)
            return Gson().fromJson<T>(mJson, type)
        }
        val ctor = type.constructors
        try {
            return ctor[0].newInstance() as T
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }
}