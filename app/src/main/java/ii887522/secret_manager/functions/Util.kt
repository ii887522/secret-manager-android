package ii887522.secret_manager.functions

operator fun CharRange.contains(str: String): Boolean {
  for (ch in str) {
    if (ch !in this) return false
  }
  return true
}
