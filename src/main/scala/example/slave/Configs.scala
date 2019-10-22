package example.slave

import chisel3._
import freechips.rocketchip.config.{Parameters, Config}
import freechips.rocketchip.subsystem.{WithRoccExample, WithNMemoryChannels, WithNBigCores, WithRV32}
import freechips.rocketchip.devices.tilelink.BootROMParams
import freechips.rocketchip.diplomacy.{LazyModule, ValName}
import freechips.rocketchip.tile.XLen

object ConfigValName {
  implicit val valName = ValName("TestHarness")
}
import ConfigValName._
/*
// 存在しないのでコメントアウト
// testchipip自体は以下にあるので、これを組み込む事自体は可能なはず。
// https://github.com/ucb-bar/testchipip
class WithBootROM extends Config((site, here, up) => {
  case BootROMParams => BootROMParams(
    contentFileName = s"./testchipip/bootrom/bootrom.rv${site(XLen)}.img")
})
*/

class WithPWM extends Config((site, here, up) => {
  case BuildTop => (clock: Clock, reset: Bool, p: Parameters) =>
    Module(LazyModule(new ExampleTopWithPWM()(p)).module)
})

class BaseExampleConfig extends Config(
  //new WithBootROM ++
  new freechips.rocketchip.system.DefaultConfig)

class PWMConfig extends Config(new WithPWM ++ new BaseExampleConfig)

