package ii887522.secret_manager.any

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ii887522.secret_manager.database.Secret
import ii887522.secret_manager.databinding.SecretButtonBinding

class SecretListAdapter(private val onClick: (Secret) -> Unit) : ListAdapter<Secret, SecretButtonHolder>(SecretDiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    SecretButtonHolder(SecretButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: SecretButtonHolder, position: Int) {
    holder.binding.label = getItem(position).label
    holder.binding.secretButton.setOnClickListener {
      onClick(getItem(position))
    }
  }
}
