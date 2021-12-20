package ii887522.secret_manager.update_secret

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ii887522.secret_manager.R
import ii887522.secret_manager.any.SecretListAdapter
import ii887522.secret_manager.database.SecretManagerDatabase
import ii887522.secret_manager.databinding.UpdateSecretFragmentBinding

class UpdateSecretFragment : Fragment() {
  private lateinit var binding: UpdateSecretFragmentBinding
  private lateinit var viewModel: UpdateSecretViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Update an existing secret"
    binding = DataBindingUtil.inflate(inflater, R.layout.update_secret_fragment, container, false)
    val adapter = SecretListAdapter {
      findNavController().navigate(UpdateSecretFragmentDirections.actionUpdateSecretFragmentToUpdateSecretFormFragment(it.label))
    }
    binding.secretList.adapter = adapter
    viewModel = ViewModelProvider(
      this,
      UpdateSecretViewModelFactory(SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao)
    )[UpdateSecretViewModel::class.java]
    viewModel.secrets.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      binding.secrets = it
      adapter.submitList(it.sortedBy { secret -> secret.label.lowercase() })
    }
    return binding.root
  }
}
