package ii887522.secret_manager.copy_secret

import androidx.lifecycle.ViewModel
import ii887522.secret_manager.database.SecretManagerDao

class CopySecretViewModel(dao: SecretManagerDao) : ViewModel() {
  val secrets = dao.getAllLiveSecrets()
}
