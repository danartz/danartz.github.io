import pandas as pd
import random as random

#minValue will increment by a random amount
def randomizeField(minValue, maxValue, value):
    x = random.randrange(1, 50)
    if(x == 50):
        return random.randrange(minValue, maxValue - value)
    return random.randrange(minValue, int(1/20 * float(maxValue)))

def incrementDayOfWeek():
    insertionsPerDay = random.randrange(0, 30)
    return insertionsPerDay

#Appends day of week random number of times and increments day after appending the correct number of data points
def appendDayOfWeek(args):
    if(args[0] < args[1]):
        args[0] += 1
        args[2].append(args[3][args[4]])
    else:
        args[4] += 1
        if(args[4] >= 6):
            args[4] = 0
        args[2].append(args[3][args[4]])
        args[0] = 0
        args[1] = incrementDayOfWeek()
    return args

#Build list of trash days in order
def buildTargetList(heightMeasurements, lastDayEmptied):
    nextTrashDay = []
    for i in range(len(heightMeasurements)):
        if(heightMeasurements[i] == 0):
            nextTrashDay.append(lastDayEmptied[i])
    return nextTrashDay
        
    


#height and weight should gradually increase each day until full and then reset

def generateData(maxwInput, maxhInput):
    heightMeasurements = []
    weightMeasurements = []
    dayOfWeek = ["sun", "mon", "tues", "wed", "thur", "fri", "sat"]
    dayOfWeekInsertionCounter = 0
    dayOfWeekCounter = 0
    dayOfWeekInsertions = 0
    generatedDayOfWeek = []
    height = 0
    weight = 0
    maxWeight = maxwInput
    maxHeight = maxhInput
    dataSize = 10000
    args = [dayOfWeekInsertionCounter, dayOfWeekInsertions, generatedDayOfWeek, dayOfWeek, dayOfWeekCounter]
    
    #if weight or height is between 80 and 100% increment day
    dayOfWeekInsertions = incrementDayOfWeek()
    # Add weight, height and day to dataset
    for i in range (dataSize):
        heightMeasurements.append(height)
        weightMeasurements.append(weight)
        
        #
        args = appendDayOfWeek(args)
        # change to held value
        
        #
        if (weight >= .80 * float(maxWeight)):
            weight = 0
            height = 0
            
            weightMeasurements.append(weight)
            heightMeasurements.append(height)
            #
            args = appendDayOfWeek(args)
            
            #
        if (height >= .80 * float(maxHeight)):
            height = 0
            weight = 0
            
            weightMeasurements.append(weight)
            heightMeasurements.append(height)
            #
            args = appendDayOfWeek(args)
            
            #
        weight += randomizeField(1, maxWeight, weight)
        height += randomizeField(1, maxHeight, height)
        
    
    #Add last day emptied to list    
    lastDayEmptiedString = ""
    lastDayEmptied = []
    for i in range(len(weightMeasurements)):
        if(heightMeasurements[i] == 0):
            lastDayEmptied.append(args[2][i])
            lastDayEmptiedString = args[2][i]
        else:
            lastDayEmptied.append(lastDayEmptiedString)

    #Add trash day target to dataset
    trashDayPredictionTarget = buildTargetList(heightMeasurements, lastDayEmptied)
    trashDayPredList = []
    targetCounter = 0
    for i in range(len(weightMeasurements)):
        if(weightMeasurements[i] == 0):
            if(targetCounter < len(trashDayPredictionTarget) - 1):
                targetCounter += 1
            trashDayPredList.append(trashDayPredictionTarget[targetCounter])
        else:
            trashDayPredList.append(trashDayPredictionTarget[targetCounter])

    # 
    dataDic = {'height': heightMeasurements, 'weight': weightMeasurements, 'day': args[2], 'lastDayEmptied': lastDayEmptied , 'trashDayTarget': trashDayPredList}
    return dataDic
    

def saveData(df):
    df.to_csv("training.csv")

def main():
   # maxwInput = int(input("Enter max weight:"))
    #maxhInput = int(input("Enter max height:"))
    dataDic = generateData(100, 100)
    df = pd.DataFrame(dataDic)
    saveData(df)
    print("Saved Dataset Size: " + str(len(df)) + " instances")


if __name__ == "__main__":
    main()


