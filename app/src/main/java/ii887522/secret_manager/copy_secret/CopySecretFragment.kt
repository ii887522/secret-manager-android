package ii887522.secret_manager.copy_secret

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ii887522.secret_manager.R
import ii887522.secret_manager.any.SecretListAdapter
import ii887522.secret_manager.database.SecretManagerDatabase
import ii887522.secret_manager.databinding.CopySecretFragmentBinding

class CopySecretFragment : Fragment() {
  private lateinit var binding: CopySecretFragmentBinding
  private lateinit var viewModel: CopySecretViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Copy an existing secret"
    viewModel = ViewModelProvider(
      this,
      CopySecretViewModelFactory(SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao)
    )[CopySecretViewModel::class.java]
    val adapter = SecretListAdapter {
      (requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
        .setPrimaryClip(ClipData.newPlainText("Secret", it.value))
      Toast.makeText(context, "The secret of ${it.label} has been successfully copied!", Toast.LENGTH_LONG).show()
      findNavController().navigateUp()
    }
    viewModel.secrets.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      adapter.submitList(it.sortedBy { secret -> secret.label.lowercase() })
    }
    binding = DataBindingUtil.inflate(inflater, R.layout.copy_secret_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.secretList.adapter = adapter
    return binding.root
  }
}
