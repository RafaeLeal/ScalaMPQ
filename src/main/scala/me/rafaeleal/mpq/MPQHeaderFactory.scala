package me.rafaeleal.mpq

/**
  * Created by rafaeleal on 4/8/17.
  */
object MPQHeaderFactory {
  def create(file:MPQFile): MPQHeader = {
    val magic: Array[Byte] = file.read(4)
    println(magic.toList)
    if (magic.last == 0x1a) {
      /** file reading.. order matters! **/
      println("read headerSize")
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
      println(s"userDataSize = $userDataSize")
      val mpqHeaderOffset = file.readInt()
      println(s"mpqHeaderOffset = $mpqHeaderOffset")
      val userDataHeaderSize = file.readInt()
      println(s"userDataHeaderSize = $userDataHeaderSize")
      println(userDataHeaderSize)
      val content: Array[Byte] = file.read(userDataHeaderSize)
      print("content: ")
      println(content.toList)
      val userDataHeader = MPQUserDataHeader(magic, userDataSize, mpqHeaderOffset, userDataHeaderSize, content)
      val offset = mpqHeaderOffset
      file.seek(mpqHeaderOffset)
      println(s"read headerSize $offset")

      val headerSize = file.readUnsignedInt()
      println(s"headerSize = $headerSize")
      val archiveSize = file.readUnsignedInt()
      println(s"archiveSize = $archiveSize")
      val formatVersion = file.readShort()
      println(s"formatVersion = $formatVersion")
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
