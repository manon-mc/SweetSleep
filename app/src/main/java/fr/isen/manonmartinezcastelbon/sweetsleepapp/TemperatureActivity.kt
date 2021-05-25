package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import fr.isen.manonmartinezcastelbon.sweetsleepapp.R
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityLumiereBinding
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityTemperatureBinding

class TemperatureActivity : AppCompatActivity() {

    lateinit var binding: ActivityTemperatureBinding
    private lateinit var mService: BoundService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BoundService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTemperatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boutonTemp2.setOnClickListener {
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute)  */
    fun onButtonClick(v: View) {
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            val num: Int = mService.randomNumber
            //Toast.makeText(this, "number: $num", Toast.LENGTH_SHORT).show()
        }
    }
}