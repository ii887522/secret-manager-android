package ii887522.secret_manager.any

import androidx.recyclerview.widget.DiffUtil
import ii887522.secret_manager.database.Secret

class SecretDiffCallback : DiffUtil.ItemCallback<Secret>() {
  override fun areItemsTheSame(oldItem: Secret, newItem: Secret) = oldItem.label == newItem.label
  override fun areContentsTheSame(oldItem: Secret, newItem: Secret) = oldItem == newItem
}
