package ii887522.secret_manager.update_secret_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ii887522.secret_manager.database.Secret
import ii887522.secret_manager.database.SecretManagerDao
import ii887522.secret_manager.functions.contains
import kotlinx.coroutines.launch

class UpdateSecretFormViewModel(private val dao: SecretManagerDao, val secretLabel: String) : ViewModel() {
  var secret = ""
  var retypeSecret = ""

  private val _canShowSecretBadCharError = MutableLiveData(false)
  val canShowSecretBadCharError: LiveData<Boolean> get() = _canShowSecretBadCharError

  private val _canShowSecretBadSizeError = MutableLiveData(false)
  val canShowSecretBadSizeError: LiveData<Boolean> get() = _canShowSecretBadSizeError

  private val _canShowSecretNotEqualError = MutableLiveData(false)
  val canShowSecretNotEqualError: LiveData<Boolean> get() = _canShowSecretNotEqualError

  private val _updatedSecret = MutableLiveData<Secret?>()
  val updatedSecret: LiveData<Secret?> get() = _updatedSecret

  fun update() {
    when {
      secret !in ' '..'~' -> _canShowSecretBadCharError.value = true
      secret.length < 16 || secret.length > 64 -> _canShowSecretBadSizeError.value = true
      secret != retypeSecret -> _canShowSecretNotEqualError.value = true
      else -> viewModelScope.launch {
        val secret = Secret(secretLabel, secret)
        dao.update(secret)
        _updatedSecret.value = secret
      }
    }
  }

  fun doneShowSecretBadCharError() {
    _canShowSecretBadCharError.value = false
  }

  fun doneShowSecretBadSizeError() {
    _canShowSecretBadSizeError.value = false
  }

  fun doneShowSecretNotEqualError() {
    _canShowSecretNotEqualError.value = false
  }

  fun doneReactUpdatedSecret() {
    _updatedSecret.value = null
  }
}
