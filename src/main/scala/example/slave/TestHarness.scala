package example.slave

import chisel3._
import freechips.rocketchip.diplomacy.LazyModule
import freechips.rocketchip.config.{Field, Parameters}
import freechips.rocketchip.system.Generator.{generateArtefacts, generateROMs, generateTestSuiteMakefrags, names}
import freechips.rocketchip.util.GeneratorApp

case object BuildTop extends Field[(Clock, Bool, Parameters) => ExampleTopModuleImp[ExampleTop]]

class TestHarness(implicit val p: Parameters) extends Module {
  val io = IO(new Bundle {
    val success = Output(Bool())
  })

  val dut = p(BuildTop)(clock, reset.toBool, p)
  dut.debug.get := DontCare
  dut.connectSimAXIMem()
  dut.dontTouchPorts()
  dut.tieOffInterrupts()
  //io.success := dut.connectSimSerial() // シリアルが存在しないのでコメントアウト
  io.success := true.B
}

object Generator extends GeneratorApp {
  // v1.2-0731119-SNAPSHOTからlongNameがlazy valになっためoverride lazy valで上書き
  override lazy val longName = names.topModuleProject + "." + names.configs
  generateFirrtl
  generateAnno
  generateTestSuiteMakefrags
  generateROMs
  generateArtefacts
}
