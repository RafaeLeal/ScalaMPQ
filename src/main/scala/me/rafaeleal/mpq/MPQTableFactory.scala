package me.rafaeleal.mpq

import me.rafaeleal.mpq.hash.MPQHashTable

/**
  * Created by rafaeleal on 4/8/17.
  */
object MPQTableFactory {
  def createHashTable(archive: MPQArchive): MPQHashTable = {
    val tableType = "hash"
    val tableOffset = archive.header.hashTableOffset
    val tableEntries = archive.header.hashTableEntries
    new MPQHashTable
  }
}
