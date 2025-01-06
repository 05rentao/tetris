=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 33856603
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array, I used the 2D array to represent the state of the board, represent whether each of the
  10x20 space is occupied by a square. I have 2 versions of the board, displayBoard and board, the
  display board simply represent the board as the shape is moving down, while board represents actual
  blocks that were placed down.

  2. Inheritance and subtyping, I created an abstract class Shapes, which has a lot of general functions
  like move_left or move_right that all shapes would share the code for, but for each individual shape,
  LShape, IShape, OShape, SShape... etc, all had different representations for rotation, so I had to
  implement subclasses for each of the shapes that managed its own rotation and implemented its own
  rotate method.

  3. Collections, I used Maps to store the information on how many points was gained, how many points
  of each color, etc. The key for the maps were the colors, or just "points", and the value was the
  amount of points.

  4.JUnit testable component, There are many movement functions that are crucial to Tetris, so I
  implemented tests that made sure that when a shape object is moved it moved correctly, and I also
  used the test to check that when the blocks filled to the top, the game correctly ends.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  InvalidMoveException: I just wanted to create an exception that was specific to this game

  IShape, JShape, LShape, OShape, SShape, TShape, ZShape: all of these classes are subclasses
  that extends Shapes, these classes only hold information on what itself is supposed to look
  like and what its own rotations looks like. Important functions in these classes are "rotate"
  and "updateRotation", which essentially manages its own rotation because the rotation for
  each shape looks different, while move_down is essentially the same for all shapes.

  Shapes: This is a crucial component of Tetris, each shape that is generated for playing is a
  Shapes object, each Shapes object contains a smaller 2D array that represents what itself
  looks like, and its own coordinate on the board, this class
  has an important function paintShape that takes the current state of the
  board(2d array) and essentially "draws" itself onto the board and returning a new 2D array
  with the shape painted on it. Other functions mainly consist of the movement functions:
  move_down, move_right, move_left, which updates the coordinate for each shape accordingly.
  These functions are shared by all subclasses of Shapes.

  TetrisBoard is the main class that is kind of the game itself, it has many internal states as
  variables, some of these are: points, the state of the board(what blocks are occupied), colors
  of the blocks, the current shape that is falling down, the time elapsed. The game itself only
  registers movements from up/down/left/right arrows. Important functions in this class is
  nextRound(), which sets down the current shape as permanent part of the board and generates
  another shape, tick, which constantly moves the shape down, and checks for failure when the
  blocks reach too high, or when the blocks clear out a row. And reset, which kind of kick-starts
  the game and reset many instance variables.

  RunTetris is the class that actually puts the game together on the screen, the layout is fairly
  simple, there is a reset button, and there is a section that displays the points the player have
  earned.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  I was really stumped on how to generate a random shape, like randomly create an object,
  I originally used a map and had the values be "new SShape(random int, random int)", and
  get elements of the map randomly, but then I realized that the shapes were set from the start
  and I was not creating new shapes, that tripped me up for a while.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think that overall if It weren't for the test class I had to make, the private
  state would be well encapsulated, but because I had to implement a test class, and it
  would take major reprogramming the game to change say the void nextRound() so that it
  can be called without instantiating a TetrisBoard object, so I just made some of the
  states "protected" so they could be assessed in the test class. I think that if I were
  to refactor, I could definitely organize the class better, maybe make it so that the
  TetrisBoard object paints the shape instead of the shape painting itself on the board.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used
  while implementing your game.

  https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java
  https://stackoverflow.com/questions/63608796/using-lambdas-to-create-and-populate-objects-as-an-argument
  https://www.youtube.com/watch?v=4edL_cwmiZ4
  https://stackoverflow.com/questions/8193801/how-to-set-specific-window-frame-size-in-java-swing