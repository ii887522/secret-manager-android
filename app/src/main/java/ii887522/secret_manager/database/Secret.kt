package ii887522.secret_manager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "secret") data class Secret(@PrimaryKey val label: String, val value: String)
