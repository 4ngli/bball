# bball
cs1951w bouncing ball assignment

## UI
- Left click on balls to change color
- Left click on blank space to add new balls
- Press and hold for 3 seconds to reset board

## TODOs
- Theme selector

## Files
- `pom.xml`: `maven` config
- `edu.brown.cs.angli.bball`:
  - `Main.java`: main function
  - `BouncingBallEx`: GUI window
  - `AnimationBoard`: board to host the animation
  - `Ball.java`: class to record individual ball related data, cloneable
  - `CollisionHandler.java`: a collection of static functions to handle collision
  - `ConcurrentCollisionHandler.java`: concurrent collision handling using `Executors.newCachedThreadPool` and `Future`
  - `SingleBallHandler`: the callable collision handler for a single ball 
  - `class_diag.ucls`: generated UML class diagram
- `target/bball-xxx.jar`: compiled jar
