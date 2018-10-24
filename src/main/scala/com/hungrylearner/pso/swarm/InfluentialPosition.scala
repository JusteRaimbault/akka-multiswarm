package com.hungrylearner.pso.swarm

import com.hungrylearner.pso.particle.EvaluatedPosition

/**
  * Tell a child swarm about an influential position. This is typically a local or global
  * best, but depends on the social strategy.
  *
  * @param evaluatedPosition
  * @param iteration The current iteration when message was sent.
  */
case class InfluentialPosition[F,P]( evaluatedPosition: EvaluatedPosition[F,P], iteration: Int) extends Influence


