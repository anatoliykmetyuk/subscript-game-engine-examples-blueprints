live = new InitialScreen / exit

class InitialScreen {script..
  live = newGameCmd new Level
       + exitCmd    exit.trigger
}


class Level {script..
  live = modelDependencies && gui && [new Game / gameOver] ...

  modelDependencies = && [score ~~(s: Int)~~> let level = score % 100]
                         [level ~~(l: Int)~~> let speed = <arctan(l) with 10 as an asymptote>]

  gui              = lScore && lLevel && lSpeed && lNextFigure && lPause
  lPause.live      = [pause [delay: 500 let enabled = !enabled] / unpause] ...
  lNextFigure.live =  nextFigure ~~(figId: Int)~~> setFigure(figId)
}


class Game {script..
  game = let nextFigure = random
         clearScene resetScore
         [  
           val currentFigure = new Figure(nextFigure)
           nextFigure = random

           currentFigure ~~(positions: List[Int, Int])~~> updateFigureWell: positions
           remove: currentFigure

           removeFilledRows ~~(removedRowsIds: List[Int])~~> let score += removedRowsIds.size * 10
           if well.maxHeight >= well.height then gameOver.trigger
         ] ...

}

class Figure {script..
  live = [fall || controls] %/% pause ...
         components.map(_.position)^

  fall = delay: speed
         let this.y -= 1
         while (this.y > 0)

  controls = [leftCmd                    let this.x -= 1
           +  rightCmd                   let this.x += 1] delay: leftRightSpeed
           + [downCmd if this.y > 0 then let this.y -= 1] delay: downCmdFallSpeed
             ...
}
