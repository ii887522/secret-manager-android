package ii887522.secret_manager.save_secret

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ii887522.secret_manager.database.SecretManagerDao
import java.lang.IllegalArgumentException

class SaveSecretViewModelFactory(private val dao: SecretManagerDao) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast") override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SaveSecretViewModel::class.java)) return SaveSecretViewModel(dao) as T
    throw IllegalArgumentException("Unknown ViewModel class!")
  }
}
