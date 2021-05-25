package fr.isen.manonmartinezcastelbon.sweetsleepapp.registre

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.manonmartinezcastelbon.sweetsleepapp.UserActivityFragmentInteraction
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.FragmentLoginBinding



class LoginFragment  : Fragment(){

    lateinit var binding: FragmentLoginBinding


    var delegate: UserActivityFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is UserActivityFragmentInteraction) {
            delegate = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.boutonConnexion.setOnClickListener {
            delegate?.Login(
                binding.ident.text.toString(),
                binding.motdepasse.text.toString(),
            )
        }

        binding.boutonCreerCpt.setOnClickListener {
            delegate?.showRegister()
        }
    }
}