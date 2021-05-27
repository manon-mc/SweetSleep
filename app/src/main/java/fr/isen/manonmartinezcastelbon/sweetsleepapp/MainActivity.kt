package fr.isen.manonmartinezcastelbon.sweetsleepapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // affichage de l'activité
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //quand on click on entre dans coonectingActivity
        binding.buttonEntre.setOnClickListener {
            // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, ConnectingActivity::class.java)
            intent.putExtra("category", "Entrer")
            startActivity(intent)
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("mon tag","non de sort pas")
    }
}