package me.rafaeleal.mpq.file

import me.rafaeleal.mpq.{MPQHeader, MPQUserDataHeader}

/**
  * Created by rafaeleal on 4/8/17.
  */
case class MPQFileHeader(magic: Array[Byte],
                         headerSize: Int,
                         archiveSize: Int,
                         formatVersion: Short,
                         sectorSizeShift: Short,
                         hashTableOffset: Int,
                         blockTableOffset: Int,
                         hashTableEntries: Int,
                         blockTableEntries: Int,
                         offset: Int,
                         userDataHeader: Option[MPQUserDataHeader]) extends MPQHeader
