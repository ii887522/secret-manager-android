package ii887522.secret_manager.update_secret

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ii887522.secret_manager.database.SecretManagerDao
import java.lang.IllegalArgumentException

class UpdateSecretViewModelFactory(private val dao: SecretManagerDao) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast") override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UpdateSecretViewModel::class.java)) return UpdateSecretViewModel(dao) as T
    throw IllegalArgumentException("Unknown ViewModel class!")
  }
}
