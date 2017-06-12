package me.rafaeleal.mpq

import me.rafaeleal.mpq.hash.{HashType, MPQHash, MPQHashTable}

import scala.util.Try

/**
  * Created by rafaeleal on 4/8/17.
  */
object MPQTableFactory {
  def createHashTable(archive: MPQArchive): MPQHashTable = {
    val tableType = "hash"
    val tableOffset = archive.header.hashTableOffset
    val tableEntries = archive.header.hashTableEntries
    val key: BigInt = MPQHash(s"($tableType table)", HashType.TABLE)
    val index = tableOffset + archive.header.offset
    archive.file.seek(index)
    val data = archive.file.read(tableEntries * 16)
    val decrypt = MPQHash.decrypt(data, key)
    decrypt.foreach(println)
    new MPQHashTable
  }
}
