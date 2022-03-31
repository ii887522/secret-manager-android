package ii887522.secret_manager.save_secret

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ii887522.secret_manager.database.Secret
import ii887522.secret_manager.database.SecretManagerDao
import ii887522.secret_manager.functions.contains
import kotlinx.coroutines.launch

class SaveSecretViewModel(private val dao: SecretManagerDao) : ViewModel() {
  var secretLabel = ""
  var secret = ""
  var retypeSecret = ""

  private val _canShowLabelEmptyError = MutableLiveData(false)
  val canShowLabelEmptyError: LiveData<Boolean> get() = _canShowLabelEmptyError

  private val _canShowLabelExistError = MutableLiveData(false)
  val canShowLabelExistError: LiveData<Boolean> get() = _canShowLabelExistError

  private val _canShowSecretBadCharError = MutableLiveData(false)
  val canShowSecretBadCharError: LiveData<Boolean> get() = _canShowSecretBadCharError

  private val _canShowSecretBadSizeError = MutableLiveData(false)
  val canShowSecretBadSizeError: LiveData<Boolean> get() = _canShowSecretBadSizeError

  private val _canShowSecretNotEqualError = MutableLiveData(false)
  val canShowSecretNotEqualError: LiveData<Boolean> get() = _canShowSecretNotEqualError

  private val _hasSavedSecret = MutableLiveData(false)
  val hasSavedSecret: LiveData<Boolean> get() = _hasSavedSecret

  fun save() {
    viewModelScope.launch {
      when {
        secretLabel.trim() == "" -> _canShowLabelEmptyError.value = true
        secretLabel.trim() in dao.getAllSecrets().map { it.label } -> _canShowLabelExistError.value = true
        secret !in ' '..'~' -> _canShowSecretBadCharError.value = true
        secret.length < 16 || secret.length > 64 -> _canShowSecretBadSizeError.value = true
        secret != retypeSecret -> _canShowSecretNotEqualError.value = true
        else -> {
          dao.insert(Secret(secretLabel.trim(), secret))
          _hasSavedSecret.value = true
        }
      }
    }
  }

  fun doneShowLabelEmptyError() {
    _canShowLabelEmptyError.value = false
  }

  fun doneShowLabelExistError() {
    _canShowLabelExistError.value = false
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

  fun doneReactHasSavedSecret() {
    _hasSavedSecret.value = false
  }
}
