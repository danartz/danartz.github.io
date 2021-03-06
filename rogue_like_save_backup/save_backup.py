
################################   EDIT THE PATHS BELLOW #############################################

#Name of game executable including .exe extension
game = "noita.exe"
# Change this to your game directory
gamePath = "E:\\SteamLibrary 2\\steamapps\\common\\Noita"
# Enter the path to your existing save file
path1= 'C:\\Users\\type-computer-username-here\\AppData\\LocalLow\\Nolla_Games_Noita\\save00'
# Enter an existing directory to store your backups in
path2='C:\\Users\\type-computer-username-here\\AppData\\LocalLow\\Nolla_Games_Noita\\save_backups'

######################################################################################################
######################################################################################################
######################################################################################################
###############################   DO NOT EDIT BELLOW THIS POINT ######################################

import os
import shutil
import subprocess
import datetime as datetime

def copy_file(src, dest):
    if(not os.path.isdir(dest)):
        shutil.copytree(src, dest)
    else:
        print("File " + str(dest) + " overriden")
        shutil.rmtree(dest)
        shutil.copytree(src, dest)

def load_file(src, dest):
    if(os.path.isdir(src)):
        shutil.rmtree(src)
    shutil.copytree(dest, src)

# Make slot number match save file name
def select_file_save(saves):
    print("Select a File to save over -- FILES WILL BE OVERRIDEN: ")
    for i in range(len(saves)):
        print("  " + str(i + 1) + "|   |" + saves[i])
    print("  " + str(len(saves) + 1) + "|   |"+"--EMPTY SLOT-- ")
    try:
        uInput = int(input("Select a File slot to save over: "))
        if int(uInput) > (len(saves) + 1):
            return -1
        return uInput
    except:
        return -1

def select_file_load(saves):
    print("Saved files -- LOAD TO GAME")
    for i in range(len(saves)):
        print("  " + str(i + 1) + "|   |" + saves[i])
    try:
        uInput = int(input("Select a File to load: "))
    except:
        return -1
    if uInput > len(saves):
        return -1
    return saves[uInput - 1]

def main_options():
    print("########################################################")
    print("################ ROGUELIKE SAVE BACKUP TOOL ############")
    print("  s |   | save (backup current save)")
    print("  l |   | load and launch the game(overrides current save!)")
    print("  q |   | quit")
    userInput = input("Choose one of the following options:")
    return userInput

saves = os.listdir(path2)
userInput = main_options()

while userInput != "q":
    # Backup current save file
    if userInput == "s":
        saveIndex = select_file_save(saves)
        if(saveIndex != -1):
            if int(saveIndex) == len(saves) + 1:
                saveFile = path2 + "\\" + str(datetime.datetime.today()).replace(" ", "-").replace(".", "-").replace(":", "-")
            elif int(saveIndex) <= len(saves):
                saveFile = path2 + "\\" + str(saves[saveIndex-1])
            copy_file(path1, saveFile)
            print(saveFile + " saved -- backed up")
        else:
            print("Invalid file slot!")
    # Load save file and launch game
    elif userInput == "l":
        loadIndex = select_file_load(saves)
        if(loadIndex != -1):
            loadFile = path2 + "\\" + str(loadIndex)
            load_file(path1, loadFile)
            print(loadFile + " loaded")
            #Launch game
            print("Launching " + game +" enjoy :)")
            os.chdir(gamePath)
            os.system(game)
        else:
            print("Invalid file slot!")
    saves = os.listdir(path2)
    userInput = main_options()
