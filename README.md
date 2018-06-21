# Solitaire
Baker's Dozen Solitaire for SWE3313

6/20/2018
User Interface works now 
Cards selected are highlighted on click
Moving cards into slots works
All of the logic of moving cards onto other cards or into empty slots works

Things to work on now
1.) Currently nothing happens when the player moves all the cards into the foundation slots
the game just keeps going and their not able to move cards anymore
We need to add a win condition and maybe do something special when they win

2.) Give cards an Image - Make a ImageLoader class that the game can use to upload images
and allow the view to draw them onto the CardGraphics depending on the rank/suit of the CardGraphics
Card member

3.) The GameOptions Panel
Add a drop down list that allows us to pick from a couple basic functions
such as New Game, Restart Game, Undo Move, Quit Game, etc

****Besides these first three - the rest of these are optional and are worth extra credit

4.) Stats 
We can keep stats based on how the user is playing
MoveCounter and TimePlayed are two things we could keep track of
and report to the user during their play time

5.) Undo Move Function
Keep a previous state of the game from a move ago in memory that we can
resort back to if we pick this from the GameOptions Panel

6.) Game Styles
Once we get alot of the main functionality out of the way we should start
figuring out how to make the game look pretty!
Allow the user to pick from a variety of styles that change how the background
and cards look.

