package me.rafaeleal.mpq

/**
  * Created by rafaeleal on 4/8/17.
  */
case class MPQHeaderExtended(magic: Array[Byte],
                             headerSize: Int,
                             archiveSize: Int,
                             formatVersion: Short,
                             sectorSizeShift: Short,
                             hashTableOffset: Int,
                             blockTableOffset: Int,
                             hashTableEntries: Int,
                             blockTableEntries: Int,
                             offset: Int,
                             userDataHeader: Option[MPQUserDataHeader],
                             extendedBlockTableOffset: Long,
                             hashTableOffsetHigh: Long,
                             blockTableOffsetHigh: Short) extends MPQHeader
