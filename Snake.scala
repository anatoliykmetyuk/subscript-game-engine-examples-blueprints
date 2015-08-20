live = new Snake || border || rabbits

class Snake {script..
  live = [move || controls] %/% pause

  move = delay: speed
         while (!border.collidesWith(head.position + direction) && !tail.collides(head.position + direction))
         advance()

  controls = [cmdToVector ~~> updateDirection ~~> if _ then delay: rapidMovementSpeed advance()] ...

  cmdToVector: (Int, Int) =+ upCmd    (0 , 1 )^
                             downCmd  (0 , -1)^
                             leftCmd  (-1, 0 )^
                             rightCmd (1 , 0 )^

  updateDirection(vec: (Int, Int)): Boolean = (direction == vec)^ let direction = vec
}

rabbits = [randomLocation ~~> (new Rabbit(_))] ...

class Rabbit {script..
  live = collision ~~> if _.isInstanceOf[Snake] then delete: this let score += 1
}