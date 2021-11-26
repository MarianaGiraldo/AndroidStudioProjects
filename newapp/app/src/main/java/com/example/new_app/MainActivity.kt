package com.example.new_app


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
/*
import com.example.new_app.databinding.ActivityMainBinding
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
*/

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.limerse.slider.ImageCarousel
import com.limerse.slider.model.CarouselItem
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var sampleImages = intArrayOf(
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val carouselView: CarouselView = findViewById(R.id.carouselView)
        carouselView.pageCount = sampleImages.size;
        carouselView.setImageListener(imageListener);
        */
        //val carousel : ImageView = findViewById(R.id.imagen)

    // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        //carousel.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

        // Image URL with caption
        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                caption = "Photo by Aaron Wu on Unsplash"
            )
        )

        // Just image URL
        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
            )
        )

        // Image URL with header
        val headers = mutableMapOf<String, String>()
        headers["header_key"] = "header_value"

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_1,
                headers = headers
            )
        )

        // Image drawable with caption
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_2,
                caption = "Photo by Kimiya Oveisi on Unsplash"
            )
        )

        // Just image drawable
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_3
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_4
            )
        )
        //carousel.setData(list)

        //Remote Config Firebase
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        //Creación de documentos para FireStore
        val jsonArr = JSONArray(
            """[
            {
            'name' : 'Steffi Velandia', 
             'jobtitle' : 'Instructura ADSI', 
             'workplace' : 'SENA CBA', 
             'biography' : 'Nació en 1991 en Facatativá...', 
             'twitter' : 'steffi.velandia123', 
             'image' : 'https://www.mujerestransformandomexico.com/images/photos/anonimo.png', 
             'category' : 0 
            },
            {
            'name' : 'Pedro Sanchez', 
             'jobtitle' : 'Desarrollador FrontEnd', 
             'workplace' : 'SENA Bogotá', 
             'biography' : 'Nació en el 2000 en Bogotá...', 
             'twitter' : 'pedroSanchez1', 
             'image' : 'ruta',
            'category' : 1 
            }
            ]"""
        )

        val jsonArr2 = JSONArray(
            """[
                    {
                    "title" : "Conferencia N1", 
                     "description" : "Esta será una conferencia  muy buena con muchos conocimientos", 
                     "tag" : "IOT", 
                     "datetime" : "1645340000", 
                     "speaker" : "Steffi Velandia" 
                     },
                    {
                    "title" : "Conferencia N2", 
                     "description" : "Esta será una conferencia  muy buena con muchos conocimientos", 
                     "tag" : "IOT", 
                     "datetime" : "1645340000", 
                     "speaker" : "Steffi Velandia"
                     }
                    ]"""
        )

        val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

        for(i in 0 until jsonArr.length()){
            val aux = jsonArr.get(i) as JSONObject
            val speaker= Speaker()
            speaker.name = aux.getString("name")
            speaker.jobtitle = aux.getString("jobtitle")
            speaker.workplace = aux.getString("workplace")
            speaker.biography = aux.getString("biography")
            speaker.twitter = aux.getString("twitter")
            speaker.image = aux.getString("image")
            speaker.category = aux.getInt("category")

            firebaseFireStore.collection("speakers").document().set(speaker)
                .addOnSuccessListener {
                Log.d("SpeakerDocument", "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }

        for(i in 0 until jsonArr2.length()){
            val aux = jsonArr2.get(i) as JSONObject
            val conference= Conference()
            conference.title = aux.getString("title")
            conference.description = aux.getString("description")
            conference.tag = aux.getString("tag")

            //Genera una instancia tipo Calendar
            val cal: Calendar = Calendar.getInstance()
            cal.timeInMillis = aux.getLong("datetime")*1000
            conference.datetime = cal.time

            conference.speaker = aux.getString("speaker")

            firebaseFireStore.collection("conferences").document().set(conference)
        }

    }

    var imageListener: ImageListener = ImageListener { position, imageView -> // You can use Glide or Picasso here
        imageView.setImageResource(sampleImages[position])
    }

}

