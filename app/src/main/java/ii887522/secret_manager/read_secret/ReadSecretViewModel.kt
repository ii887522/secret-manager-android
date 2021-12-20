package ii887522.secret_manager.read_secret

import androidx.lifecycle.ViewModel
import ii887522.secret_manager.database.SecretManagerDao

class ReadSecretViewModel(dao: SecretManagerDao) : ViewModel() {
  val secrets = dao.getAllLiveSecrets()
}
