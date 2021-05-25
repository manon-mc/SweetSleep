package fr.isen.manonmartinezcastelbon.sweetsleepapp


import android.content.*
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityLumiereBinding


class LumiereActivity : AppCompatActivity() {

    private val TAG = LumiereActivity::class.java.simpleName
    private var m2Service: Messenger? = null
    private var bound: Boolean = false

    lateinit var binding: ActivityLumiereBinding
    private lateinit var mService: BoundService
    private var mBound: Boolean = false

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
           // mService = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLumiereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boutonBleu.setOnClickListener {
            Log.d(TAG, "my Message")
            if (!bound) return@setOnClickListener
            // Create and send a message to the service, using a supported 'what' value
            val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
            try {
                Log.d(TAG, "my Message 2")
                m2Service?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        binding.boutonBlanc.setOnClickListener {
            Log.d(TAG, "my Message")
            if (!bound) return@setOnClickListener
            // Create and send a message to the service, using a supported 'what' value
            val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
            try {
                Log.d(TAG, "my Message 2")
                m2Service?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        binding.boutonJaune.setOnClickListener {
            startActivity(intent)
        }
        binding.boutonVert.setOnClickListener {
            startActivity(intent)
        }
        binding.boutonRouge.setOnClickListener {
            startActivity(intent)
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