package me.rafaeleal.mpq.utils

import java.nio.{ByteBuffer, ByteOrder}

/**
  * Created by rafaeleal on 4/9/17.
  */
object StructBytes {
  val LONG_SIZE = 8 // bytes
  val INT_SIZE = 4 // bytes
  val SHORT_SIZE = 2 // bytes
  val CHAR_SIZE = 2 // bytes
  def arrayByteToInt(bytes: Array[Byte], order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = {
    val reduce: Int = bytes.foldRight(0)((byt, acc) => {
      acc * 256 + byt.toInt
    })
    reduce
  }
  def arrayByteToUnsignedInt(bytes: Array[Byte], order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = {
    val reduce: Int = bytes.foldRight(0)((byt, acc) => {
      val unsign = byt & 0xff
      acc * 256 + unsign
    })
    reduce
  }
  def arrayByteToLong(bytes: Array[Byte], order: ByteOrder = ByteOrder.LITTLE_ENDIAN) : Long = ByteBuffer.wrap(bytes).order(order).getLong()
  def arrayByteToShort(bytes: Array[Byte], order: ByteOrder = ByteOrder.LITTLE_ENDIAN) : Short = ByteBuffer.wrap(bytes).order(order).getShort()
  def arrayByteToChar(bytes: Array[Byte], order: ByteOrder = ByteOrder.LITTLE_ENDIAN) : Char = ByteBuffer.wrap(bytes).order(order).getChar()
}
