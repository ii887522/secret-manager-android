package ii887522.secret_manager.update_secret_form

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
import ii887522.secret_manager.database.SecretManagerDatabase
import ii887522.secret_manager.databinding.UpdateSecretFormFragmentBinding

class UpdateSecretFormFragment : Fragment() {
  private lateinit var binding: UpdateSecretFormFragmentBinding
  private lateinit var viewModel: UpdateSecretFormViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Update existing secret form"
    viewModel = ViewModelProvider(
      this,
      UpdateSecretFormViewModelFactory(
        SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao,
        UpdateSecretFormFragmentArgs.fromBundle(requireArguments()).secretLabel
      )
    )[UpdateSecretFormViewModel::class.java]
    viewModel.canShowSecretBadCharError.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(
        context,
        "Secret must only include 0..9 A-Z a-z !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~   Please try again.",
        Toast.LENGTH_LONG
      ).show()
      viewModel.doneShowSecretBadCharError()
    }
    viewModel.canShowSecretBadSizeError.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(
        context,
        "Secret must have at least 16 characters and not greater than 64 characters! Please try again.",
        Toast.LENGTH_LONG
      ).show()
      viewModel.doneShowSecretBadSizeError()
    }
    viewModel.canShowSecretNotEqualError.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(
        context, "Retype secret differs from the first secret typed! Please try again.", Toast.LENGTH_LONG
      ).show()
      viewModel.doneShowSecretNotEqualError()
    }
    viewModel.updatedSecret.observe(viewLifecycleOwner) {
      if (it === null) return@observe
      Toast.makeText(context, "The secret of ${it.label} has been successfully updated!", Toast.LENGTH_LONG)
        .show()
      findNavController().navigateUp()
      viewModel.doneReactUpdatedSecret()
    }
    binding = DataBindingUtil.inflate(inflater, R.layout.update_secret_form_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.updateSecretButton.setOnClickListener { viewModel.update() }
    return binding.root
  }
}
