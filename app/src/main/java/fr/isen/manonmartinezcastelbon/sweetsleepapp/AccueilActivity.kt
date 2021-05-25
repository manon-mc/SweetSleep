package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityAccueilBinding

class AccueilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccueilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccueilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boutonMusic.setOnClickListener {
            // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, MusiqueActivity::class.java)
            intent.putExtra("musique", "")
            startActivity(intent)
        }
        binding.aleerte.setOnClickListener {
            // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, AlerteActivity::class.java)
            intent.putExtra("alerte", "")
            startActivity(intent)
        }
        binding.boutonLuz.setOnClickListener {
            // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, LumiereActivity::class.java)
            intent.putExtra("lumiere", "")
            startActivity(intent)
        }
        binding.boutonTemp.setOnClickListener {
            // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, TemperatureActivity::class.java)
            intent.putExtra("temp", "")
            startActivity(intent)
        }

    }
}
