package me.rafaeleal.mpq.file

import java.io.{BufferedInputStream, FileInputStream}
import java.nio.file.{Files, Paths}
import java.nio.{ByteBuffer, ByteOrder}

import me.rafaeleal.mpq.utils.StructBytes

/**
  * Created by rafaeleal on 3/18/17.
  */

class MPQFile(filename: String) {
  val fileBytes: Array[Byte] = Files.readAllBytes(Paths.get(filename))
  private var index = 0
  private def readByte = {
    index += 1
    fileBytes(index - 1)
  }
  def read(num: Int): Array[Byte] = {
    var resp = List[Byte]()
    for (i <- 1 to num){
      val byte: Byte = readByte
      resp = resp :+ byte
    }
    resp.toArray
  }

  def readChar(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Char = StructBytes.arrayByteToChar(read(StructBytes.CHAR_SIZE))
  def readUnsignedInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = StructBytes.arrayByteToUnsignedInt(read(StructBytes.INT_SIZE))
  def readInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = StructBytes.arrayByteToInt(read(StructBytes.INT_SIZE))
  def readLong(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Long = StructBytes.arrayByteToLong(read(StructBytes.LONG_SIZE))
  def readShort(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Short = StructBytes.arrayByteToShort(read(StructBytes.SHORT_SIZE))
  def seek(i: Int): Unit = {
      index = i
  }
}
