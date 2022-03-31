package ii887522.secret_manager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Secret::class], version = 1, exportSchema = false) abstract class SecretManagerDatabase :
  RoomDatabase() {

  abstract val dao: SecretManagerDao

  companion object {
    @Volatile private var instance: SecretManagerDatabase? = null

    fun getInstance(context: Context): SecretManagerDatabase = synchronized(this) {
      var l_instance = instance
      if (l_instance === null) {
        l_instance = Room.databaseBuilder(
          context.applicationContext, SecretManagerDatabase::class.java, "secret_manager"
        )
          .fallbackToDestructiveMigration()
          .build()
        instance = l_instance
      }
      return l_instance
    }
  }
}
