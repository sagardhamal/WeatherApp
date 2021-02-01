package com.test.weather.ui.cityDetails

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.test.weather.R
import com.test.weather.data.WeCurrentWeather
import kotlinx.android.synthetic.main.activity_city_detail.*
import kotlinx.android.synthetic.main.activity_city_detail.view.*


class CityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)
        val cityModel: WeCurrentWeather = intent.getParcelableExtra("CITY_DETAIL")
        setSupportActionBar(toolbar)
        setDetails(cityModel)

    }

    private fun setDetails(cityModel: WeCurrentWeather) {

        //val textView = toolbar.findViewById(R.id.toolbar) as TextView
        //textView.text = cityModel.name

       // supportActionBar!!.setDisplayShowTitleEnabled(false)
        textCityName.text = cityModel.name
        textHumadity.text = "Humidity : " + cityModel.main?.humidity.toString()
        textRain.text = "Rain Timming : " + cityModel.rain?.h.toString()
        textWind.text = "Wind Speed (km/h) :" + cityModel.wind?.speed.toString()
        textTemp.text = "Temprature :" + cityModel.main?.temp.toString()

    }
}