
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

    uInput = input("Select a File slot to save over: ")
    if int(uInput) > (len(saves) + 1):
        print("Invalid file slot!")
        return -1
    return uInput

def select_file_load(saves):
    print("Saved files -- LOAD TO GAME")
    for i in range(len(saves)):
        print("  " + str(i + 1) + "|   |" + saves[i])
    uInput = int(input("Select a File to load: "))
    return saves[uInput - 1]

def main_options():
    print("########################################################")
    print("################ SAVE-IT FILE BACKUP TOOL ##############")
    print("  s |   | save (backup current save)")
    print("  l |   | load (overrides current save!)")
    print("  q |   | launch the game")
    userInput = input("Choose one of the following options:")
    return userInput

saves = os.listdir(path2)
userInput = main_options()

while userInput != "q":
    if userInput == "s":
        saveIndex = select_file_save(saves)
        if(saveIndex != -1):
            saveFile = path2 + "\\NoitaSave" + str(saveIndex)
            copy_file(path1, saveFile)
            print(saveFile + " saved -- backed up")
    elif userInput == "l":
        loadIndex = select_file_load(saves)
        loadFile = path2 + "\\" + str(loadIndex)
        load_file(path1, loadFile)
        print(loadFile + " loaded")
    saves = os.listdir(path2)
    userInput = main_options()

# Launch game
#print(os.listdir(gamePath))
os.chdir(gamePath)
os.system(game)
    
    




