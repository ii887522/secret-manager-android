package ii887522.secret_manager.delete_secret

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ii887522.secret_manager.database.Secret
import ii887522.secret_manager.database.SecretManagerDao
import kotlinx.coroutines.launch

class DeleteSecretViewModel(private val dao: SecretManagerDao) : ViewModel() {
  val secrets = dao.getAllLiveSecrets()

  private val _hasDeleteSecret = MutableLiveData(false)
  val hasDeleteSecret: LiveData<Boolean> get() = _hasDeleteSecret

  fun delete(secret: Secret) {
    viewModelScope.launch {
      dao.delete(secret)
      _hasDeleteSecret.value = true
    }
  }

  fun doneReactHasDeleteSecret() {
    _hasDeleteSecret.value = false
  }
}
