import java.io.File

import me.rafaeleal.mpq.MPQArchive
import me.rafaeleal.mpq.hash.{HashType, MPQEncryptionTable, MPQHash}
import org.scalatest.FlatSpec


/**
  * Created by rafaeleal on 4/8/17.
  */
class MPQHashSpec extends FlatSpec {
  val path: String = new File(".").getAbsolutePath
  "EncryptionTable" should "work as Blizzard's" in {
    val table = new MPQEncryptionTable()
    val hashTable = scala.io.Source.fromFile(s"$path/src/test/resources/HashTable")
    val entries = hashTable.getLines()

    for(line <- entries) {
      val key = line.split(" ").head.toLong
      val value = line.split(" ").last.toLong

      assert(table.encryptionTable(key) === value)
    }
  }
  "MPQHash TABLE_OFFSET" should "work as Blizzard´s" in {
    val foo = MPQHash("foo", HashType.TABLE_OFFSET)
    val bar = MPQHash("bar", HashType.TABLE_OFFSET)

    assert(foo === BigInt("3847654972", 10))
    assert(bar === BigInt("2882684439", 10))

  }

  "MPQHash TABLE" should "work as Blizzard´s" in {
    val bar = MPQHash("bar", HashType.TABLE)
    assert(bar === BigInt("2097331149", 10))
  }

  "Decrypting" should "work as expected" in {
    val path = new File(".").getAbsolutePath
    val decryptValues = scala.io.Source.fromFile(s"$path/src/test/resources/DecryptValues").getLines().toList
    println(path)

    val archive = new MPQArchive(s"$path/src/test/resources/MyReplay.SC2Replay")
    val tableType = "hash"
    val tableOffset = archive.header.hashTableOffset
    val tableEntries = archive.header.hashTableEntries
    val key: BigInt = MPQHash(s"($tableType table)", HashType.TABLE)
    val index = tableOffset + archive.header.offset
    archive.file.seek(index)
    val data = archive.file.read(tableEntries * 16)
    val decrypt = MPQHash.decrypt(data, key)
    for(l <- decrypt.zipWithIndex) {
      assert(decryptValues(l._2).toLong === decrypt(l._2).toLong)
    }
  }
}
