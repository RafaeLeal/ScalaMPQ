package me.rafaeleal.mpq.hash

/**
  * Created by rafaeleal on 4/8/17.
  */

object HashType {
  val TABLE_OFFSET = 0
  val HASH_A = 1
  val HASH_B = 2
  val TABLE = 3
}
object MPQHash {
  val seed1 : BigInt = BigInt("7FED7FED", 16)
  val seed2 : BigInt = BigInt("EEEEEEEE", 16)
  val encryptionTable = new MPQEncryptionTable()
  def apply(str: String, hashType: Int): BigInt = {
    str.foldLeft((seed1, seed2))((acc,ch) => {
      val seed1 = acc._1
      val seed2 = acc._2
      val unicode = ch.toUpper.toInt
      val encryptionKey = (hashType.toLong << 8) + unicode
      val value = encryptionTable(encryptionKey)
      val xor = value ^ (seed1 + seed2)
      val newSeed1 = xor & BigInt("FFFFFFFF", 16)
      val newSeed2 = unicode + newSeed1 + seed2 + (seed2 << 5) + 3 & BigInt("FFFFFFFF", 16)
      val partial = seed2 * BigInt(scala.math.pow(2, 5).toLong)
      (newSeed1, newSeed2)
    })._1
  }
}
