package com.hungrylearner.pso.particle

trait Particle[F,P] extends ParticleSpace[F,P] with Kinematic[F,P] {

  /**
   * Update our velocity and position for this iteration.
   *
   * @param iteration
   * @param bestPosition Regional best position.
   * @return A new personal best or none if our updated position is not a personal best.
   */
  def update( iteration: Int, bestPosition: Position[F,P]): Option[Position[F,P]]  = {
    updateVelocity( iteration, bestPosition)
    position.addVelocity( velocity, iteration)
    personalBest.setIfBest( position)
  }

  def fittest( other: Particle[F,P]): Particle[F,P] =
    if( other.position < position) other else this

}

