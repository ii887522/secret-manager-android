package ii887522.secret_manager.save_secret

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
import ii887522.secret_manager.databinding.SaveSecretFragmentBinding

class SaveSecretFragment : Fragment() {
  private lateinit var binding: SaveSecretFragmentBinding
  private lateinit var viewModel: SaveSecretViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity?.title = "Save a new secret"
    viewModel = ViewModelProvider(
      this,
      SaveSecretViewModelFactory(SecretManagerDatabase.getInstance((activity as AppCompatActivity).application).dao)
    )[SaveSecretViewModel::class.java]
    viewModel.canShowLabelEmptyError.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(context, "Label for the secret must not be empty! Please try again.", Toast.LENGTH_LONG).show()
      viewModel.doneShowLabelEmptyError()
    }
    viewModel.canShowLabelExistError.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(context, "Label for the secret already exists! Please try again.", Toast.LENGTH_LONG).show()
      viewModel.doneShowLabelExistError()
    }
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
    viewModel.hasSavedSecret.observe(viewLifecycleOwner) {
      if (!it) return@observe
      Toast.makeText(context, "You have successfully saved a new secret!", Toast.LENGTH_LONG).show()
      findNavController().navigateUp()
      viewModel.doneReactHasSavedSecret()
    }
    binding = DataBindingUtil.inflate(inflater, R.layout.save_secret_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.saveSecretButton.setOnClickListener { viewModel.save() }
    return binding.root
  }
}
