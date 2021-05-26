package fr.isen.manonmartinezcastelbon.sweetsleepapp


import android.content.*
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityLumiereBinding


class LumiereActivity : AppCompatActivity() {

    private val TAG = LumiereActivity::class.java.simpleName
    private var messenger: Messenger? = null
    private var bound: Boolean = false

    lateinit var binding: ActivityLumiereBinding
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
        binding = ActivityLumiereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boutonBleu.setOnClickListener {
            sendMessageToBleService(1)
        }
        binding.boutonBlanc.setOnClickListener {
            sendMessageToBleService(2)
        }
        binding.boutonJaune.setOnClickListener {
            sendMessageToBleService(1)

        }
        binding.boutonVert.setOnClickListener {
            sendMessageToBleService(2)

        }
        binding.boutonRouge.setOnClickListener {
            sendMessageToBleService(3)
        }
    }

    private fun sendMessageToBleService(messageToSend: Int) {
        Log.d(TAG, "my Message")
        if (bound){
            val msg: Message = Message.obtain(null, messageToSend, 0, 0)
            try {
                Log.d(TAG, "my Message 2")
                messenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "inside la connexion ")
        // Bind to LocalService
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

}