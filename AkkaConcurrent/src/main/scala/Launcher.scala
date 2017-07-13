import akka.actor.ActorSystem
import actors.{BossActor, WorkerActor}
import messages.Finish

object Launcher extends App {
  val system = ActorSystem("akka_concurrent")

  val worker = system.actorOf(WorkerActor.props)

  system.log.info("Begin to send messages to {}", worker)

  for (i <- 0 until 100)
    system.actorOf(BossActor.props(worker, 50000))
}
