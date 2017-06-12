package me.rafaeleal.mpq.hash

/**
  * Created by rafaeleal on 4/8/17.
  */
class MPQEncryptionTable(seed: Long = 0x00100001){
  val offset = 0x100
  val encryptionTable: Map[Long, Long] = (0 to 255).foldLeft((Map[Long, Long](), seed))((acc, i) => {
    val indexes = i to i + 4 * offset by offset
    indexes.foldLeft(acc)((accIndex, index) => {
      val partialCryptoTable = accIndex._1
      val currentSeed = accIndex._2
      val seed1: Long = (currentSeed * 125 + 3) % 0x2AAAAB
      val temp1: Long = (seed1 & 0xFFFF) << 0x10
      //println(s"1 | ${index.formatted("%5d")} | ${seed1.formatted("%7d")} | ${temp1.toLong}")

      val seed2 = (seed1 * 125 + 3) % 0x2AAAAB
      val temp2 = seed2 & 0xFFFF
      //println(s"2 | ${index.formatted("%5d")} | ${seed2.formatted("%7d")} | ${temp2.toLong}")
      val entry = index.toLong -> (temp1.toLong | temp2.toLong)
      (partialCryptoTable + entry, seed2)
    })
  })._1

  def apply(key: Long): Long = encryptionTable.apply(key)
  def apply(key: BigInt): BigInt = BigInt(encryptionTable.apply(key.toLong))
}