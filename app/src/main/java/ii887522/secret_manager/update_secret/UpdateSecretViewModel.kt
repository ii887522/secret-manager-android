package ii887522.secret_manager.update_secret

import androidx.lifecycle.ViewModel
import ii887522.secret_manager.database.SecretManagerDao

class UpdateSecretViewModel(dao: SecretManagerDao) : ViewModel() {
  val secrets = dao.getAllLiveSecrets()
}
