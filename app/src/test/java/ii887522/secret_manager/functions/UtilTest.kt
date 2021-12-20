package ii887522.secret_manager.functions

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilTest {
  @Test fun testContains() {
    assertTrue("a" in 'a'..'a')
    assertFalse("b" in 'a'..'a')
    assertFalse("c" in 'a'..'a')
    assertTrue("aa" in 'a'..'a')
    assertFalse("ab" in 'a'..'a')
    assertFalse("ac" in 'a'..'a')
    assertFalse("bc" in 'a'..'a')
    assertFalse("cc" in 'a'..'a')
    assertTrue("aaa" in 'a'..'a')
    assertFalse("aab" in 'a'..'a')
    assertTrue("aab" in 'a'..'b')
    assertTrue("aab" in 'a'..'c')
    assertFalse("aab" in 'b'..'c')
    assertFalse("aab" in 'c'..'c')
  }
}
