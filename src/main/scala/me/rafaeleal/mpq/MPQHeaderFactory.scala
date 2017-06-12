package me.rafaeleal.mpq

import me.rafaeleal.mpq.file.{MPQFile, MPQFileHeader}

/**
  * Created by rafaeleal on 4/8/17.
  */
object MPQHeaderFactory {
  def create(file:MPQFile): MPQHeader = {
    val magic: Array[Byte] = file.read(4)
    if (magic.last == 0x1a) {
      /** file reading.. order matters! **/
      val headerSize = file.readInt()
      val archiveSize = file.readInt()
      val formatVersion = file.readShort()
      val sectorSizeShift = file.readShort()
      val hashTableOffset = file.readInt()
      val blockTableOffset = file.readInt()
      val hashTableEntries = file.readInt()
      val blockTableEntries = file.readInt()
      val offset = 0
      val content = Array[Byte]()
      if (formatVersion == 1) {
        val extendedBlockTableOffset = file.readLong()
        val hashTableOffsetHigh = file.readLong()
        val blockTableOffsetHigh = file.readShort()
        MPQHeaderExtended(magic, headerSize, archiveSize, formatVersion, sectorSizeShift, hashTableOffset, blockTableOffset, hashTableEntries, blockTableEntries, offset, userDataHeader = None, extendedBlockTableOffset, hashTableOffsetHigh, blockTableOffsetHigh)
      } else MPQFileHeader(magic, headerSize, archiveSize, formatVersion, sectorSizeShift, hashTableOffset, blockTableOffset, hashTableEntries, blockTableEntries, offset, userDataHeader = None)
    } else {
      /** file reading.. order matters! **/
      val userDataSize = file.readInt()
      val mpqHeaderOffset = file.readInt()
      val userDataHeaderSize = file.readInt()
      val content: Array[Byte] = file.read(userDataHeaderSize)
      val userDataHeader = MPQUserDataHeader(magic, userDataSize, mpqHeaderOffset, userDataHeaderSize, content)
      val offset = mpqHeaderOffset
      file.seek(mpqHeaderOffset + 4)

      val headerSize = file.readUnsignedInt()
      val archiveSize = file.readUnsignedInt()
      val formatVersion = file.readShort()
      val sectorSizeShift = file.readShort()
      val hashTableOffset = file.readUnsignedInt()
      val blockTableOffset = file.readUnsignedInt()
      val hashTableEntries = file.readUnsignedInt()
      val blockTableEntries = file.readUnsignedInt()
      if (formatVersion == 1) {
        val extendedBlockTableOffset = file.readLong()
        val hashTableOffsetHigh = file.readLong()
        val blockTableOffsetHigh = file.readShort()
        MPQHeaderExtended(magic, headerSize, archiveSize, formatVersion, sectorSizeShift, hashTableOffset, blockTableOffset, hashTableEntries, blockTableEntries, offset, userDataHeader = Some(userDataHeader), extendedBlockTableOffset, hashTableOffsetHigh, blockTableOffsetHigh)
      } else MPQFileHeader(magic, headerSize, archiveSize, formatVersion, sectorSizeShift, hashTableOffset, blockTableOffset, hashTableEntries, blockTableEntries, offset, userDataHeader = Some(userDataHeader))
    }
  }
}
