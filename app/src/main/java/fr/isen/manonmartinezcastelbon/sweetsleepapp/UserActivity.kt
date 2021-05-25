package fr.isen.manonmartinezcastelbon.sweetsleepapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.ActivityUserBinding
import fr.isen.manonmartinezcastelbon.sweetsleepapp.registre.LoginFragment
import fr.isen.manonmartinezcastelbon.sweetsleepapp.registre.RegistreFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface UserActivityFragmentInteraction {
    fun showLogin()
    fun showRegister()
    fun Login(email: String, password: String)
    fun Register(email: String, password: String, nom : String, prenom: String)
}
 class UserActivity : AppCompatActivity(), UserActivityFragmentInteraction {
    lateinit var binding: ActivityUserBinding
     val database = FirebaseDatabase.getInstance()
     private lateinit var mAuth: FirebaseAuth

     @RequiresApi(Build.VERSION_CODES.O)
   override  fun Register(email: String, password: String, nom : String, prenom: String){
         if(password.length < 7){
             Toast.makeText(baseContext, "Mot de passe trop court", Toast.LENGTH_SHORT).show()
         }else{
             mAuth?.createUserWithEmailAndPassword(email.toString(), password.toString())?.addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information

                    showLogin();


                 } else {
                     // If sign in fails, display a message to the user.
                     Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                 }

             }
         }

     }

override    fun Login(email: String, password: String){
    if( email != null && password != null) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Connexion", "Connexion reussi")
                    val user = mAuth?.currentUser
                    Toast.makeText(baseContext, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AccueilActivity::class.java)
                    saveUser(user)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Connexion", "Email ou mot de passe incorrect", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Email ou mot de passe incorrect",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }else{
        Toast.makeText(baseContext, "Email ou mot de passe vide", Toast.LENGTH_SHORT).show()

    }

     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance();
        val fragment = RegistreFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
    }



    override fun showLogin() {

        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun showRegister() {
        val fragment = RegistreFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    fun saveUser(user: FirebaseUser?) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ID_USER, user?.getUid())
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }


    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}

