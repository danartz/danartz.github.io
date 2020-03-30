I do not claim ownership over any game titles this tool can be used with, 
including the example game being used in the script "noita". I do not claim
ownership of Steam. These were just applications being used as examples.

This script will remain free to use and will receive additional features in the future.

##########################################################################################

1.) The user will first need to install Python 3 on their computer. I haven't tested this
script with Python 2, but that should also be adequate.
2.) Verify python was installed correctly by running the command "python --version" or "py --version"
3.) If Python install was successful the user can follow the instructions in the script to get
started.

  a.) Within the quotation marks exchange "noita.exe" with whatever the game executable is.
      eg: "game.exe"
  b.) Change the string in gamePath to whatever your game directory is for the game you
      wish to play. eg: "C:\\Steam\\steamapps\\common\\game". 
     --IMPORTANT--
      YOU MUST USE 2 BACK SLASHES RATHER THAN 1.. EXACTLY LIKE IN THE EXAMPLE!
  c.) Change the string in path1 to the exact path of the game you want to play's save file,
      exactly like in the example.. Again.. use 2 back slashes in the path.
  d.) Change the string in path2 to the exact of where you'd like to back up your roguelike
      save files. --IMPORTANT-- The directory must exist before using it. Create the directory
      before attempting to to reference it in this script.
  e.) Finally, the user can run the script by typing "python save_backup.py" in the command prompt window.
      If "python save_backup.py" doesn't work, check the name of your python installation. On my computer I run the command
      "py save_backup.py"
