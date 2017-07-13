package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import messages._

import scala.collection.mutable

class WorkerActor extends Actor with ActorLogging {

  private var finished: Int = _

  private val jobMap = mutable.HashMap[ActorRef, Int]()

  override def receive: Receive = {
    case Job =>
      val sndr = sender()

      jobMap.get(sndr) match {
        case Some(jobCount) => jobMap.update(sndr, jobCount + 1)
        case None => jobMap.update(sndr, 1)
      }

    case Finish =>
      finished += 1

      if (finished >= jobMap.size) {
        log.info("Doing {} jobs", jobMap.foldLeft(0)(_ + _._2))

        context.system.terminate()
      }

    case _ =>
  }
}

object WorkerActor {
  def props: Props = Props(classOf[WorkerActor])
}
