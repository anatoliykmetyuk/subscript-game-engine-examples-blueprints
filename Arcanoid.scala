game = new Ball && new Board && new DestructableTarget

class Ball {script..
  live = launchCmd let velocity = randomVector(from = (-1, 2), to = (1, 2))
         collision ~~(case b: Bottom)~~> gameOver.trigger && pause [var v = velocity; let velocity = 0] unpause [let velocity = v] ...
}

class Board {script..
  // cmdToDirection is a signal: may carry Boolean values, or may disappear when user releases the button
  live = cmdToDirection ~~(direction: Some[(Int, Int)])~~>> {force = direction getOrElse (0, 0)}

  cmdToDirection = keys ~~>> {
    case 'd' => (1 , 0)
    case 'a' => (-1, 0)
  }
}

class DestructableTarget {script..
  live = collision ~~(case hitPart: PartOfDestructableTarget)~~> [
    delete: hitPart
    let score += 1
    if noMorePartsLeft then victory.trigger
  ]
}