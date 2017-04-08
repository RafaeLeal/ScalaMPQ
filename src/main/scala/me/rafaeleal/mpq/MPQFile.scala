package me.rafaeleal.mpq

import java.io.{BufferedInputStream, FileInputStream}
import java.nio.{ByteBuffer, ByteOrder}

/**
  * Created by rafaeleal on 3/18/17.
  */

class MPQFile(filename: String) {
  private var reader = new BufferedInputStream(new FileInputStream(filename))
  val LONG_SIZE = 8 // bytes
  val INT_SIZE = 4 // bytes
  val SHORT_SIZE = 2 // bytes
  val CHAR_SIZE = 2 // bytes
  private def readByte = {
    val intRead = reader.read()
    val format = String.format("%16s", intRead.toBinaryString).replace(' ', '0')
//    val high = format.substring(0, 8).toByte
//    val low = format.substring(8, 16).toByte
    println(s"intRead: $intRead ")
    intRead.toByte
  }
  def read(num: Int): Array[Byte] = {
    var resp = List[Byte]()
    for (i <- 1 to num){
      print(s"Reading byte $i: ")
      val byte: Byte = readByte
      print(s"$byte \n")
      resp = resp :+ byte
    }
    println(s"resp = $resp")
    resp.toArray
  }
  private def arrayByteToInt(bytes: Array[Byte]) = {
    val reduce: Int = bytes.foldRight(0)((byt, acc) => {
      println(s"byt = $byt")
      acc * 256 + byt.toInt
    })
    println(s"reduce: $reduce")
    reduce
  }
  private def arrayByteToUnsignedInt(bytes: Array[Byte]) = {
    val reduce: Int = bytes.foldRight(0)((byt, acc) => {
      val unsign = byt & 0xff
      println(s"unsign = $unsign")
      acc * 256 + unsign
    })
    println(s"reduce: $reduce")
    reduce
  }
  def readChar(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Char = ByteBuffer.wrap(read(CHAR_SIZE)).order(order).getChar()
  def readUnsignedInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = arrayByteToUnsignedInt(read(INT_SIZE))
  def readInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = arrayByteToInt(read(INT_SIZE))
  def readLong(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Long = ByteBuffer.wrap(read(LONG_SIZE)).order(order).getLong()
  def readShort(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Short = ByteBuffer.wrap(read(SHORT_SIZE)).order(order).getShort()
  def seek(i: Int): Unit = {
//    reader.reset()
    reader = new BufferedInputStream(new FileInputStream(filename))
    read(i+4)
//    reader.skip(i.toLong)
  }
}
