package me.rafaeleal.mpq

import me.rafaeleal.mpq.file.MPQFile
import me.rafaeleal.mpq.hash.MPQHashTable

/**
  * Created by rafaeleal on 4/8/17.
  */
class MPQArchive(filename: String) {
  val file: MPQFile = new MPQFile(filename)
  val header: MPQHeader = MPQHeaderFactory.create(file)
  val hashTable: MPQHashTable = MPQTableFactory.createHashTable(this)
  val blockTable: MPQBlockTable = new MPQBlockTable
}
