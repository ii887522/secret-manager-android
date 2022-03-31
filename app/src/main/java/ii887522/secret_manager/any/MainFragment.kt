package ii887522.secret_manager.any

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import ii887522.secret_manager.R
import ii887522.secret_manager.databinding.MainFragmentBinding

class MainFragment : Fragment() {
  private lateinit var binding: MainFragmentBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    activity?.title = "Secret Manager"
    binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
    binding.saveButton.setOnClickListener {
      findNavController().navigate(MainFragmentDirections.actionMainFragmentToSaveSecretFragment())
    }
    binding.copyButton.setOnClickListener {
      findNavController().navigate(MainFragmentDirections.actionMainFragmentToCopySecretFragment())
    }
    binding.readButton.setOnClickListener {
      findNavController().navigate(MainFragmentDirections.actionMainFragmentToReadSecretFragment())
    }
    binding.updateButton.setOnClickListener {
      findNavController().navigate(MainFragmentDirections.actionMainFragmentToUpdateSecretFragment())
    }
    binding.deleteButton.setOnClickListener {
      findNavController().navigate(MainFragmentDirections.actionMainFragmentToDeleteSecretFragment())
    }
    binding.exitButton.setOnClickListener {
      activity?.finish()
    }
    return binding.root
  }
}
