package com.hungrylearner.pso.swarm

import akka.actor.ActorRef
import CompletedType._
import Report._
import TerminateCriteriaStatus._
import com.hungrylearner.pso.particle.EvaluatedPosition


/**
 * Report by sending progress report messages to parent actor.
 */
trait ParentReporter[F,P] extends ReportingStrategy[F,P] {
  val parent: ActorRef
  override def report( progressReport: ProgressReport[F,P]) {
    parent ! progressReport
  }
}

/**
 * Report all events we receive.
 */
trait ContinuousLocalReporting[F,P] extends LocalReportingStrategy[F,P] {

  override def reportOneIterationCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = report( ProgressReport( SwarmOneIterationCompleted, childIndex, evaluatedPosition, iteration, progress, TerminateCriteriaNotMet))
  override def reportSwarmAroundCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = report( ProgressReport( SwarmAroundCompleted, childIndex, evaluatedPosition, iteration, progress, TerminateCriteriaNotMet))
  override def reportSwarmingCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = report( ProgressReport( SwarmingCompleted, childIndex, evaluatedPosition, iteration, progress, terminateCriteriaStatus))
}

/**
 * Report SwarmAroundCompleted and SwarmingCompleted events. Do not report SwarmOneIterationCompleted.
 */
trait PeriodicLocalReporting[F,P] extends LocalReportingStrategy[F,P] {

  override def reportOneIterationCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = {}
  override def reportSwarmAroundCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = report( ProgressReport[F,P]( SwarmAroundCompleted, childIndex, evaluatedPosition, iteration, progress, TerminateCriteriaNotMet))
  override def reportSwarmingCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = {
    // TODO log here
    report(ProgressReport[F, P](SwarmingCompleted, childIndex, evaluatedPosition, iteration, progress, terminateCriteriaStatus))
  }
}

/**
 * Report only the final SwarmingCompleted event.
 */
trait SwarmingCompletedLocalReporting[F,P] extends LocalReportingStrategy[F,P] {

  override def reportOneIterationCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = {}
  override def reportSwarmAroundCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress) = {}
  override def reportSwarmingCompleted( childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], iteration: Int, progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = report( ProgressReport[F,P]( SwarmingCompleted, childIndex, evaluatedPosition, iteration, progress, terminateCriteriaStatus))
}


/**
 * Report all events we receive from the RegionalSupervisor.
 */
trait ContinuousRegionalReporting[F,P] extends RegionalReportingStrategy[F,P] {

  override def reportForRegion(progressReport: ProgressReport[F,P], childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = {
    val newProgressReport = ProgressReport(progressReport.completedType, childIndex, evaluatedPosition, progressReport.iteration, progress, terminateCriteriaStatus)
    report(newProgressReport)
  }
}

/**
 * Report SwarmAroundCompleted and SwarmingCompleted events. Do not report SwarmOneIterationCompleted.
 */
trait PeriodicRegionalReporting[F,P] extends RegionalReportingStrategy[F,P] {

  override def reportForRegion(progressReport: ProgressReport[F,P], childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = {
    if (progressReport.completedType != SwarmOneIterationCompleted) {
      val newProgressReport = ProgressReport(progressReport.completedType, childIndex, evaluatedPosition, progressReport.iteration, progress, terminateCriteriaStatus)
      report(newProgressReport)
    }
  }
}

/**
 * Report only the final SwarmingCompleted event.
 */
trait SwarmingCompletedRegionalReporting[F,P] extends RegionalReportingStrategy[F,P] {

  override def reportForRegion(progressReport: ProgressReport[F,P], childIndex: Int, evaluatedPosition: EvaluatedPosition[F,P], progress: Progress, terminateCriteriaStatus: TerminateCriteriaStatus) = {
    if (progressReport.completedType == SwarmingCompleted) {
      val newProgressReport = ProgressReport(progressReport.completedType, childIndex, evaluatedPosition, progressReport.iteration, progress, terminateCriteriaStatus)
      report(newProgressReport)
    }
  }
}

