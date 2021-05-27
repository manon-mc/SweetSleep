package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityConnectingBinding

class ConnectingActivity : AppCompatActivity(){

    lateinit var binding: ActivityConnectingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityConnectingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.boutonBLE.setOnClickListener {
           val intent = Intent(this, BLEActivity::class.java)
            intent.putExtra("ble", "")
            startActivity(intent)
        }*/
        // redirige vers la connexion
        binding.boutonConnexion.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivityForResult(intent, UserActivity.REQUEST_CODE)
        }


    }
}