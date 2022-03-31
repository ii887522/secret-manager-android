package ii887522.secret_manager.read_secret

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ii887522.secret_manager.R
import ii887522.secret_manager.any.SecretListAdapter
import ii887522.secret_manager.database.SecretManagerDatabase
import ii887522.secret_manager.databinding.ReadSecretFragmentBinding

class ReadSecretFragment : Fragment() {
  private lateinit var binding: ReadSecretFragmentBinding
  private lateinit var viewModel: ReadSecretViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Read an existing secret"
    viewModel = ViewModelProvider(
      this,
      ReadSecretViewModelFactory(SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao)
    )[ReadSecretViewModel::class.java]
    val adapter = SecretListAdapter {
      requireNotNull(
        AlertDialog.Builder(requireContext())
          .setTitle(it.label)
          .setMessage(it.value)
          .setNegativeButton("Close") { _, _ -> }
          .setIcon(android.R.drawable.ic_secure)
          .show()
          .findViewById<ImageView>(android.R.id.icon)
      ).setColorFilter(Color.WHITE)
      findNavController().navigateUp()
    }
    viewModel.secrets.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      adapter.submitList(it.sortedBy { secret -> secret.label.lowercase() })
    }
    binding = DataBindingUtil.inflate(inflater, R.layout.read_secret_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.secretList.adapter = adapter
    return binding.root
  }
}
