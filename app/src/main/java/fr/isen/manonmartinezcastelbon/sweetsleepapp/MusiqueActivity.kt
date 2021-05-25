package fr.isen.manonmartinezcastelbon.sweetsleepapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityMusiqueBinding

class MusiqueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusiqueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMusiqueBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}