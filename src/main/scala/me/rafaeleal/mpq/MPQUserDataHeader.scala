package me.rafaeleal.mpq

/**
  * Created by rafaeleal on 4/8/17.
  */
case class MPQUserDataHeader(magic: Array[Byte],
                             userDataSize: Int,
                             mpqHeaderOffset: Int,
                             userDataHeaderSize: Int,
                             content: Array[Byte])
