package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import messages._

class BossActor
(
  worker: ActorRef,
  count: Int
)
extends Actor with ActorLogging {
  override def preStart(): Unit = {
    var i = 0;
    while (i < count) {
      worker ! Job

      i += 1
    }

    worker ! Finish
  }

  override def receive: Receive = {
    case _ =>
  }
}

object BossActor {
  def props(worker: ActorRef, count: Int): Props = Props(classOf[BossActor], worker, count)
}
