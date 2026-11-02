# TCG-storage
Repo for final project for CS193
Project Goal: Trading card games are very popular, especially games like Pokémon or MTG, and have always been interesting to me. Unfortunately, when you have hundreds or thousands of cards it can be exceedingly difficult to keep track of the ones you have. It can also be a little scary taking the cards out if for some reason you need to look through them, especially when you have rarer cards that you would rather keep sealed away as to prevent damage. This can be quite an inconvenience for those who it matters to.

MVP Scope: My MVP will at have at minimum the following: User text entry for each card and Saving/Loading

Schedule - (For V2.0+)
 UI/visuals
 sorting/display for ease of use
color schemes
etc

Tech Stack: I'm going to use Java to write my program/app. Both the front-end and back-end will be in Java. I'll use the common Java libraries, such as Java.util (for storage, etc), Java.io (for saving, serializing objects), Java.Swing (for UI), and Java.awt (for events, etc). I will most likely use a csv or a plain text file to store data in. For data sources, I believe I won't need to add this unless I add newer features later on since this is mainly going to be used as a self-contained app.

Chunked Build Plan:

1. Set up the basic repo and coding environment
2. Create a basic user-entry system in which they enter name, type, stats, notes
3. Make it so that each time the user presses a button or closes the app it saves
4. Load the cards each time the user opens the app, and include the ones that were saved from previous closes
5. Allow the user to edit/remove cards, and keep an image url as another instance variable in the object
6. Set up the UI and make the basic functional UI with an add button, and a view button
7. Make the UI visually appealing, adding color schemes, etc
