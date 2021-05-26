package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityTemperatureBinding


class TemperatureActivity : AppCompatActivity() {

    private val TAG = LumiereActivity::class.java.simpleName
    private var messenger: Messenger? = null
    private var bound: Boolean = false
    private var boundS: BoundService? = null
    lateinit var binding: ActivityTemperatureBinding
    private var mBound: Boolean = false


    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "my conexion")
            messenger = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
            messenger = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boutonTemp2.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(4)
            boundS?.disconnect()

        }
    }


    private fun sendMessageToBleService(messageToSend: Int) {
        Log.d(TAG, "my temperature ")
        if (bound){
            val msg: Message = Message.obtain(null, messageToSend, 0, 0)

            try {
                Log.d(TAG, "temperature:")
                messenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

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