package fr.isen.manonmartinezcastelbon.sweetsleepapp


import android.content.*
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityLumiereBinding


class LumiereActivity : AppCompatActivity() {

    // déclaration des variables et instanciation
    private val TAG = LumiereActivity::class.java.simpleName
    private var messenger: Messenger? = null
    private var bound: Boolean = false
    private var boundS: BoundService? = null
    lateinit var binding: ActivityLumiereBinding
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        // connexion au service et relié au message envoye
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "my connexion")
            messenger = Messenger(service)
            bound = true
        }
        // déconnexion
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

        // click sur bouton, appele de boundService, connexion au service ble, envoi d'un message 1 pour allumer la led
        binding.boutonBleu.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(1)
            boundS?.disconnect()
        }
        // click sur bouton, appele de boundService, connexion au service ble, envoi d'un message 1 pour allumer la led
        binding.boutonBlanc.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(1)
            boundS?.disconnect()

        }
        // click sur bouton, appele de boundService, connexion au service ble, envoi d'un message 1 pour allumer la led
        binding.boutonJaune.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(1)
            boundS?.disconnect()

        }
        binding.boutonVert.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(1)
            boundS?.disconnect()

        }
        binding.boutonRouge.setOnClickListener {
            boundS?.deviceBle()
            sendMessageToBleService(1)
            //boundS?.disconnect()


        }
    }
    // fonction pour envoyer le message et reception au service
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
    // permet de commencer la connexion au BoundService
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "inside la connexion ")
        // Bind to LocalService
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }
    // stop le service si il y a la connexion
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