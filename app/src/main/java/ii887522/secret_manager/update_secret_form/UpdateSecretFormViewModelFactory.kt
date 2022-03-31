package ii887522.secret_manager.update_secret_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ii887522.secret_manager.database.SecretManagerDao
import java.lang.IllegalArgumentException

class UpdateSecretFormViewModelFactory(private val dao: SecretManagerDao, private val secretLabel: String) :
  ViewModelProvider.Factory {

  @Suppress("unchecked_cast") override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UpdateSecretFormViewModel::class.java))
      return UpdateSecretFormViewModel(dao, secretLabel) as T
    throw IllegalArgumentException("Unknown ViewModel class!")
  }
}
