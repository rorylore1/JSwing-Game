# JSwing-Game

This is a simple farming game where the player can grow crops and manage their inventory. 
The player starts on the login page, where they can enter their username. After logging in, 
they are taken to the home page, where they can view instructions, actions, and their inventory.
Going to bed action will age their crops overnight, while going farming will allow the player to tend to their crops 
The game is designed to be simple and easy to play, with a focus on managing resources and growing crops.

The code structure of this game is organized into several classes. 
The Main class initializes the JFrame for each page and listens for events from the pages.
The DetailEvent and DetailListener classes are used to handle events from the pages that are triggered by the player's actions.
The Person class represents the player and their inventory, 
The Plot class represents the crops that the player can grow.
