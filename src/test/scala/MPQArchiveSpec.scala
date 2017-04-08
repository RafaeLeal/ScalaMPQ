import java.io.File

import me.rafaeleal.mpq.MPQArchive
import org.scalatest.FlatSpec

/**
  * Created by rafaeleal on 3/18/17.
  */
class MPQArchiveSpec extends FlatSpec {
  "MyReplay" should "create me.rafaeleal.mpq.MPQHeader" in {
    val path = new File(".").getAbsolutePath
    println(path)

    val archive = new MPQArchive(s"$path/src/test/resources/MyReplay.SC2Replay")
    assert(archive.header.headerSize == 208)
    assert(archive.header.archiveSize == 89710)
    assert(archive.header.offset == 1024)
    assert(archive.header.formatVersion == 3)
    assert(archive.header.sectorSizeShift == 5)
    assert(archive.header.hashTableOffset == 88926)
    assert(archive.header.blockTableOffset == 89438)
    assert(archive.header.hashTableEntries == 32)
    assert(archive.header.blockTableEntries == 17)
    assert(archive.header.userDataHeader.isDefined)
    val userDataHeader = archive.header.userDataHeader.get
    assert(userDataHeader.userDataSize == 512)
    assert(userDataHeader.userDataHeaderSize == 115)
    assert(userDataHeader.mpqHeaderOffset == 1024)
    println(archive.header)
  }

  "MyReplay2" should "create me.rafaeleal.mpq.MPQHeader" in {
    val path = new File(".").getAbsolutePath()
    println(path)

    val archive = new MPQArchive(s"$path/src/test/resources/MyReplay2.SC2Replay")
    assert(archive.header.headerSize == 208)
    assert(archive.header.archiveSize == 105109)
    assert(archive.header.offset == 1024)
    assert(archive.header.formatVersion == 3)
    assert(archive.header.sectorSizeShift == 5)
    assert(archive.header.hashTableOffset == 104325)
    assert(archive.header.blockTableOffset == 104837)
    assert(archive.header.hashTableEntries == 32)
    assert(archive.header.blockTableEntries == 17)
    assert(archive.header.userDataHeader.isDefined)
    val userDataHeader = archive.header.userDataHeader.get
    assert(userDataHeader.userDataSize == 512)
    assert(userDataHeader.userDataHeaderSize == 115)
    assert(userDataHeader.mpqHeaderOffset == 1024)
    println(archive.header)
  }
}
