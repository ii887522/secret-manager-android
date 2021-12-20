package ii887522.secret_manager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao interface SecretManagerDao {
  @Query("select * from secret") suspend fun getAllSecrets(): List<Secret>
  @Query("select * from secret") fun getAllLiveSecrets(): LiveData<List<Secret>>
  @Insert suspend fun insert(secret: Secret)
  @Update suspend fun update(secret: Secret)
  @Delete suspend fun delete(secret: Secret)
}
