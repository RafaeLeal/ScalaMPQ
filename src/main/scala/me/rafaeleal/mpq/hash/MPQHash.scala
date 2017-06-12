package me.rafaeleal.mpq.hash

import me.rafaeleal.mpq.utils.StructBytes

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
  private val eightF = BigInt("FFFFFFFF", 16)

  def apply(str: String, hashType: Int): BigInt = {
    str.foldLeft((seed1, seed2))((acc,ch) => {
      val seed1 = acc._1
      val seed2 = acc._2
      val unicode = ch.toUpper.toInt
      val encryptionKey = (hashType.toLong << 8) + unicode
      val value = encryptionTable(encryptionKey)
      val xor = value ^ (seed1 + seed2)
      val newSeed1 = xor & eightF
      val newSeed2 = unicode + newSeed1 + seed2 + (seed2 << 5) + 3 & eightF
      val partial = seed2 * BigInt(scala.math.pow(2, 5).toLong)
      (newSeed1, newSeed2)
    })._1
  }
  def decrypt(data: Array[Byte], key: BigInt): List[BigInt] = {
    val offset = 0x400
    val seed1 = key
    val seed2 = BigInt("EEEEEEEE", 16)
    val tupled = data.sliding(4, 4).foldLeft((seed1, seed2, List[BigInt]())) { (acc, bytes) =>
      val seed1 = acc._1
      val seed2 = acc._2
      val result = acc._3
      val table = encryptionTable(offset + (seed1 & 0xFF))
      val valueSeed = (seed2 + table) & eightF
      val raw = StructBytes.arrayByteToUnsignedInt(bytes)
      val value = (raw ^ (seed1 + valueSeed)) & eightF
      val newSeed1: BigInt = (((~seed1 << 0x15) + BigInt("11111111", 16)) | (seed1 >> 0x0B)) & eightF
      val newSeed2: BigInt = value + valueSeed + (valueSeed << 5) + 3 & eightF
      (newSeed1, newSeed2, result :+ value)
    }
    tupled._3
  }
}
