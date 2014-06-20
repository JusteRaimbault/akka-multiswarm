package com.hungrylearner.pso.particle


trait ParticleSpace[F,P] {

  type PositionBounds  // aka. Search Space
  type History

  def position: MutablePosition[F,P]
  def personalBest: Position[F,P]
  def positionBounds: PositionBounds
  def history: History


  /**
   * If the updated position is a new personal best, update personal best and return the position;
   * otherwise, return null.
   * @return A new personal best position or None
   */
  def updatePersonalBest: Option[Position[F,P]]

}




