package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.*
import android.os.IBinder
import android.util.Log
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LumiereActivity : AppCompatActivity() {

    private var mConnectionState: TextView? = null
    private var mDataField: TextView? = null
    private var mDeviceName: String? = null
    private var mDeviceAddress: String? = null
    private var mGattServicesList: ExpandableListView? = null
    private var mBluetoothLeService: BoundService? = null
    private var mGattCharacteristics: ArrayList<ArrayList<BluetoothGattCharacteristic>>? = ArrayList()
    private var mConnected = false
    private var mNotifyCharacteristic: BluetoothGattCharacteristic? = null

    private val LIST_NAME = "NAME"
    private val LIST_UUID = "UUID"

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            mBluetoothLeService = (service as BoundService.LocalBinder).service
            if (!mBluetoothLeService!!.initialize()) {
                //Log.d(TAG, "Unable to initialize Bluetooth")
                finish()
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService!!.connect(mDeviceAddress)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }
    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BoundService.ACTION_GATT_CONNECTED == action) {
                mConnected = true
                updateConnectionState(R.string.connected)
                invalidateOptionsMenu()
            } else if (BoundService.ACTION_GATT_DISCONNECTED == action) {
                mConnected = false
                updateConnectionState(R.string.disconnected)
                invalidateOptionsMenu()
                clearUI()
            } else if (BoundService.ACTION_GATT_SERVICES_DISCOVERED == action) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService!!.supportedGattServices)
            } else if (BoundService.ACTION_DATA_AVAILABLE == action) {
                displayData(intent.getStringExtra(BoundService.EXTRA_DATA))
            }
        }
    }
    private val servicesListClickListner = ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
        if (mGattCharacteristics != null) {
            val characteristic = mGattCharacteristics!![groupPosition][childPosition]
            val charaProp = characteristic.properties
            if (charaProp or BluetoothGattCharacteristic.PROPERTY_READ > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService!!.setCharacteristicNotification(
                            mNotifyCharacteristic!!, false)
                    mNotifyCharacteristic = null
                }
                mBluetoothLeService!!.readCharacteristic(characteristic)
            }
            if (charaProp or BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                mNotifyCharacteristic = characteristic
                mBluetoothLeService!!.setCharacteristicNotification(
                        characteristic, true)
            }
            return@OnChildClickListener true
        }
        false
    }
    private fun clearUI() {
        mGattServicesList!!.setAdapter(null as SimpleExpandableListAdapter?)
        mDataField!!.setText(R.string.no_data)
    }
    /*private val TAG = LumiereActivity::class.java.simpleName
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
            mService = binder.getService()
            mBound = true
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
            startActivity(intent)
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
        Log.d(TAG, "inside la conexion ")
        // Bind to LocalService
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)

            }
        }

        /*fun onStop() {
            super.onStop()
            unbindService(connection)
            mBound = false
            if (bound) {
                unbindService(connection)
                bound = false
            }*/

            /** Called when a button is clicked (the button in the layout file attaches to
             * this method with the android:onClick attribute)  */
            /*fun onButtonClick(v: View) {
                if (mBound) {
                    // Call a method from the LocalService.
                    // However, if this call were something that might hang, then this request should
                    // occur in a separate thread to avoid slowing down the activity performance.
                    val num: Int = mService.randomNumber
                    //Toast.makeText(this, "number: $num", Toast.LENGTH_SHORT).show()
                }
            }*/
        }
    */
}