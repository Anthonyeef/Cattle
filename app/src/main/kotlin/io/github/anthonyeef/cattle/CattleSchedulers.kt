package io.github.anthonyeef.cattle

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

object CattleSchedulers {
  val io = Schedulers.io()
  val mainThread: Scheduler = AndroidSchedulers.mainThread()
}