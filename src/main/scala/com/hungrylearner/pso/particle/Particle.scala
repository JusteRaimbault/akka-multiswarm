package com.hungrylearner.pso.particle

trait Particle[F,P] extends ParticleSpace[F,P] with Kinematic[F,P] {

  /**
   * Use the one best particle from the beginning of the step.
   *
   * Pass in the bestParticle fitness too. No need to calculate it for every particle.
   *
   * Need to return the fitness for each particle. From that we keep track of the
   * best particle at the end of the step
   *
   * @param iteration
   * @param bestPosition
   * @return A new personal best or none if the updated position is not a personal best.
   */
  def update( iteration: Int, bestPosition: Position[F,P]): Option[Position[F,P]]  = {
    updateVelocity( iteration, bestPosition)
    position.addVelocity( velocity, iteration)
    updatePersonalBest
  }

  def fittest( other: Particle[F,P]): Particle[F,P] =
    if( other.position < position) other else this

}

