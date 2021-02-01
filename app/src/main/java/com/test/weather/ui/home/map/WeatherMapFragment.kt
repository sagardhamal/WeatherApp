package com.test.weather.ui.home.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import com.test.weather.R
import com.test.weather.data.CityDb
import com.test.weather.data.db.AppDatabase

import com.test.weather.data.db.entity.City
import com.test.weather.network.ApiHelperImpl
import com.test.weather.network.RetrofitBuilder
import com.test.weather.ui.base.BaseFragmentKotlin
import com.test.weather.ui.home.ViewModelFactory
import com.test.weather.utils.api.Status
import kotlinx.android.synthetic.main.weather_list_fragment.*
import java.util.*


class WeatherMapFragment : BaseFragmentKotlin(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener {

    private var viewModel: WeatherMapViewModel? = null

    companion object {
        fun newInstance() = WeatherMapFragment()
    }


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.weather_map_fragment
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun initView(view: View) {

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_city) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(WeatherMapViewModel::class.java)
    }


    override fun onMapReady(mMap: GoogleMap?) {
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.clear() //clear old markers
        if (mMap != null) {
            map = mMap
        }
        mMap?.setOnMarkerDragListener(this)
        //Move camera position
        moveCamera(mMap)
        setMarker(mMap)
    }

    private fun moveCamera(mMap: GoogleMap?) {
        var lastLocation: Location? = null
   /*     fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            lastLocation = location
        }*/
        val latLng: LatLng
        if (lastLocation == null) {
            latLng = LatLng(24.017641, 79.480401)
        } else {
            latLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
        }
        val moveToCurrentLocation = CameraPosition.builder()
            .target(latLng)
            .zoom(5f)
            .bearing(0f)
            .tilt(45f)
            .build()

        mMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(moveToCurrentLocation),
            10000,
            null
        )
    }

    private fun setMarker(mMap: GoogleMap?) {

        mMap?.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        24.017641,
                        79.480401
                    )
                )
                .title("Move marker")
                .draggable(true)
            //.icon(activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_place) })
        )


    }


    override fun onMarkerDragEnd(p0: Marker?) {
        val lat: Double? = p0?.position?.latitude;
        val lon: Double? = p0?.position?.longitude;
        var cityName = getGeoAddress(context, lat, lon)


        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Bookmark city ?")
        builder.setMessage(" Are you sure to bookmark $cityName city ?")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
           // viewModel?.saveCity(City(1,cityName), AppDatabase.invoke(requireContext()))


            viewModel?.getSaveCityNo()?.observe(this, androidx.lifecycle.Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoading()
                        // it.data

                    }
                    Status.LOADING -> {
                        showLoading()

                    }
                    Status.ERROR -> {
                        //Handle Error
                        hideLoading()
                        showError(it.message)
                    }
                }
            })

        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
            Toast.makeText(
                requireContext(),
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }


        builder.show()

    }

    override fun onMarkerDragStart(p0: Marker?) {
        // TODO("Not yet implemented")
    }

    override fun onMarkerDrag(p0: Marker?) {
        // TODO("Not yet implemented")
    }

    fun getGeoAddress(
        context: Context?,
        latitude: Double?,
        longitude: Double?
    ): String {
        var currentAddress = ""

        try {
            val geocoder = Geocoder(context, Locale.ENGLISH)
            val addresses: List<Address>
            addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)
            val str = StringBuilder()
            if (Geocoder.isPresent()) {
                val returnAddress = addresses[0]
                val localityString = returnAddress.locality
                val city = returnAddress.countryName
                /* val subLocalityString = returnAddress.subLocality
                 // String zipcode = returnAddress.getPostalCode();
                 str.append("$subLocalityString ")
                 str.append("$localityString $city ")*/
                currentAddress = localityString
            } else {
                Toast.makeText(
                    context,
                    "Current address not found", Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.e("tag", e.message)
        }

        return currentAddress
    }
}
