package me.rafaeleal.mpq

/**
  * Created by rafaeleal on 4/8/17.
  */
trait MPQHeader {
  def magic: Array[Byte]
  def headerSize: Int
  def archiveSize: Int
  def formatVersion: Short
  def sectorSizeShift: Short
  def hashTableOffset: Int
  def blockTableOffset: Int
  def hashTableEntries: Int
  def blockTableEntries: Int
  def offset: Int
  def userDataHeader: Option[MPQUserDataHeader]
}
