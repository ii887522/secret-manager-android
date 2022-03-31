package ii887522.secret_manager.delete_secret

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ii887522.secret_manager.R
import ii887522.secret_manager.any.SecretListAdapter
import ii887522.secret_manager.database.SecretManagerDatabase
import ii887522.secret_manager.databinding.DeleteSecretFragmentBinding

class DeleteSecretFragment : Fragment() {
  private lateinit var binding: DeleteSecretFragmentBinding
  private lateinit var viewModel: DeleteSecretViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Delete an existing secret"
    viewModel = ViewModelProvider(
      this,
      DeleteSecretViewModelFactory(SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao)
    )[DeleteSecretViewModel::class.java]
    val adapter = SecretListAdapter {
      AlertDialog.Builder(requireContext())
        .setTitle("Delete Confirmation")
        .setMessage("Are your sure you want to delete ${it.label}?")
        .setNegativeButton("Close") { _, _ -> }
        .setPositiveButton("Delete") { _, _ -> viewModel.delete(it) }
        .setIcon(android.R.drawable.ic_delete)
        .show()
    }
    viewModel.secrets.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      adapter.submitList(it.sortedBy { secret -> secret.label.lowercase() })
    }
    viewModel.deletedSecret.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      Toast.makeText(context, "The secret of ${it.label} has been successfully deleted!", Toast.LENGTH_LONG).show()
      findNavController().navigateUp()
      viewModel.doneReactDeletedSecret()
    }
    binding = DataBindingUtil.inflate(inflater, R.layout.delete_secret_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.secretList.adapter = adapter
    return binding.root
  }
}
