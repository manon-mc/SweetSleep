package fr.isen.manonmartinezcastelbon.sweetsleepapp.registre

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import fr.isen.manonmartinezcastelbon.sweetsleepapp.UserActivityFragmentInteraction
import fr.isen.manonmartinezcastelbon.sweetsleepapp.databinding.FragmentRegistreBinding

class RegistreFragment :Fragment() {

    lateinit var binding: FragmentRegistreBinding

    var delegate: UserActivityFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boutonCreerCompte.setOnClickListener {

            delegate?.Register(
                binding.identifiant.text.toString(),
                binding.motdepasse.text.toString(),
                binding.nom.text.toString(),
                binding.prenom.text.toString()
            )
        }

        binding.boutonSeConnecter.setOnClickListener {
            delegate?.showLogin()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is UserActivityFragmentInteraction) {
            delegate = context
        }
    }
}