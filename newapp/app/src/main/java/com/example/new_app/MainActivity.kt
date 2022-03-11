package com.example.new_app


import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.new_app.models.Comment
import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var videoView: VideoView? = null
    private lateinit var appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //Remote Config Firebase
        this.remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        this.remoteConfig.setConfigSettingsAsync(configSettings)
        this.remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

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
        val jsonArr3 = JSONArray(
            """[
                    {
                     "user_image" : "imagePath", 
                     "user_name" : "Mariana Giraldo", 
                     "comment_text" : "Un evento muy enriquecedor con temas excelentes"
                     },
                    {
                     "user_image" : "imagePath", 
                     "user_name" : "Jose Milton", 
                     "comment_text" : "Otro evento muy enriquecedor con temas excelentes"
                     }
                    ]"""
        )

        //val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

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

            /*
            firebaseFireStore.collection("speakers").document().set(speaker)
                .addOnSuccessListener {
                Log.d("SpeakerDocument", "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }

             */
        }
        for(i in 0 until jsonArr3.length()){
            val aux = jsonArr3.get(i) as JSONObject
            val comment= Comment()
            comment.user_image = aux.getString("user_image")
            comment.user_name = aux.getString("user_name")
            comment.comment_text = aux.getString("comment_text")
            /*
            firebaseFireStore.collection("comments").document().set(comment)
                .addOnSuccessListener {
                Log.d("CommentDocument", "Document added ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }

             */

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

            //firebaseFireStore.collection("conferences").document().set(conference)
        }

        //Intento controles video
        this.videoView = findViewById(R.id.videoView)
        val controller = MediaController(this).also {
            it.setMediaPlayer(this.videoView)
            it.setAnchorView(this.videoView)
        }
        this.videoView?.setMediaController(controller)


        //Toolbar
        findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
            supportActionBar?.title = null
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        //Navigation
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)

        setupBottomNavMenu(navController)

    }
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bNMenu)
        bottomNav?.setupWithNavController(navController)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }


}

