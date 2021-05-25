package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import android.view.View
import fr.isen.manonmartinezcastelbon.sweetsleepapp.R
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityLumiereBinding
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityTemperatureBinding

class TemperatureActivity : AppCompatActivity() {

    lateinit var binding: ActivityTemperatureBinding
    private var mBound: Boolean = false
    private val TAG = TemperatureActivity::class.java.simpleName

    private var m2Service: Messenger? = null
    private var bound: Boolean = false


    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "my conexion")
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BoundService.LocalBinder
            //mService = binder.getService()
            //mBound = true
            m2Service = Messenger(service)
            bound = true
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

}