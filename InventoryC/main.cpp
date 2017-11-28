//Daniel Artz
//5-03-16
//Final Project
//This program allows the user to create an inventory, modify that inventory with a data file and then display the old and new inventory.

/* Instructions - You must create an inventory by typing 1 at the main menu before you can proceed 
to any other options. After creating your original inventory, you can then display it, create a transaction file,
or project profits. After you create a transaction file, you will then need to type 4 to update the inventory to the transaction file.
After the update is completed you can then display the original inventory, display the updated inventory, or run
a profits projection on the newest inventory. 

When you update the inventory, it will ask you for a file name. Simply type the file name of the 
transaction file you just finished creating. If you add a file extension, my program will delete it;
if you don't add a file extension, my program will add it for you. It should be fool proof.

Don't update the inventory twice in a row or it'll add whatever you added to the inventory twice, etc.

The program should be fairly straight forward to use. I added a lot of validations to try to keep it as obvious
as possible. Be sure all CPP and header files are in the same folder, or added to the same project.
*/
#include <iostream>
#include "InventoryCostClass.h"
#include <string>
#include <fstream>
#include <iomanip>
#include <cctype>
using namespace std;

// global variables
const int TSIZE = 3;
const int DESCSIZE = 51;

// transaction file structure
struct transactionFile
{
	int itemId = 0;
	char transCode[TSIZE]; // 2 + 1 for the null terminator
	int quant = 0;
	double wholeSaleCost = 0;
	double retailPrice = 0;
	char desc[DESCSIZE] ="";  // 50 + 1 for the null terminator
};

// function prototypes
ofstream dataFile;
ifstream dataFileIn;
int displayMenu();
void loadInventory(InventoryCostClass[], int &, InventoryCostClass[]);
double verifyWholesaleCost(string);
double verifyRetailPrice(string);
void createTransFile(string, InventoryCostClass[]);
void updateInventory(InventoryCostClass[], int, int &, InventoryCostClass[]);
void printOrigInventory(InventoryCostClass[], int);
void printNewInventory(InventoryCostClass[], int);
void computeProfit(InventoryCostClass[], int);

int main()
{
	int userChoice, arrayCounter = 0, updateCounter = 0, marker = 0, marker2 = 0, marker3 = 0;
	string fileName;
	InventoryCostClass *inventoryItems;
	InventoryCostClass *inventoryItemsCopy;

	// dynamically created object arrays
	inventoryItemsCopy = new InventoryCostClass[50];
	inventoryItems = new InventoryCostClass[50];
	userChoice = displayMenu(); // calls displayMenu

	cout << setprecision(2) << fixed;

	while (1) // runs an infinte loop for the menu selection switch statement
	{
		switch (userChoice)
		{
		case 1:
			loadInventory(inventoryItems, arrayCounter, inventoryItemsCopy);
			marker = 1; // flags from 0 to 1 after the user selects this function
			break;
		case 2:
			if (marker == 1) // checks if user used the loadInventory function - if so allows use of createTransFile
			{
				printOrigInventory(inventoryItems, arrayCounter); // calls printOrigInventory function
			}
			else
				cout << "No inventory to print" << endl;
			break;
		case 3:
			if (marker == 1) // checks if user used the loadInventory function - if so allows use of createTransFile
			{
				cout << "What do you want to name your inventory file?: ";
				getline(cin, fileName); // asks for the file name the user wants for his transaction file
				createTransFile(fileName, inventoryItemsCopy); // calls createTransFile function passing file name
				marker2 = 1; // new flag set to 1 if user creats transaction file
			}
			else
				cout << "No Inventory Exists... Make one first. \n"; // if user didnt create inventory error couts
			cout << endl;
			break;
		case 4:
			if (marker2 == 1) // checks if a transaction file was created - if so user may use updateInventory function
			{
				cout << "Updating Inventory... \n" << endl;
				updateInventory(inventoryItems, arrayCounter, updateCounter, inventoryItemsCopy); // calls updateInventory function
				marker3 = 1; // sets new flag to 1
				cout << "--UPDATE COMPLETE-- \n" << endl;
			}
			else
				cout << "Be sure you have an Inventory and created a transaction file \n";
			cout << endl;
			break;
		case 5:
			if (marker3 == 1) // if user updated their inventory, then they can print the updated inventory
			{
				printNewInventory(inventoryItemsCopy, updateCounter);
			}
			else
				cout << "The inventory hasn't been updated yet \n"; // if not, an error couts
			break;
		case 6:
			if (marker == 1)
			{
				computeProfit(inventoryItemsCopy, arrayCounter); // calls computeProfit function
			}
			else
			{
				cout << "You must enter an inventory before projecting profit." << endl;
			}
			break;
		case 7:
			cout << "Exiting the Program \n"; // exits if user types 7
			break;
		default:
			cout << "User selection is invalid. Select a number between 1 and 7. \n";
			cout << "Enter your selection : ";
		}
		
		if (userChoice == 7) // 7 ends loop
			break;
		userChoice = displayMenu(); // calls menu and returns the user's selection again
	}

	delete [] inventoryItems; // deletes pointer to inventoryItems object array
	delete[] inventoryItemsCopy; // deletes pointer to inventoryItemsCopy array
	system("pause");
	return 0;
}

// computeProfit function
void computeProfit(InventoryCostClass items[], int itemCounter)
{
	string idStr = "";
	transactionFile transF[51];
	int idMarker, transCount = 0;
	bool flag = false;
	double ProfitProjection = 0.0;

	while (1) // infinite loop
	{
		idStr.clear(); // clears idStr variable
		cout << endl;
		cout << "Enter the Item ID or enter -1 to abort: ";
		cin >> idStr;
		cin.ignore();
		if (idStr == "-1") // if user types -1 the loop ends as does the function
			break;
		while (idStr.length() != 6) // a loop that checks if the user's input is 6 characters long
		{
			cout << "The item ID must be 6 characters long. \n";
			cout << "Enter the item ID: ";
			idStr.clear();
			cin >> idStr;
			cin.ignore();
		}
		transF[transCount].itemId = atoi(idStr.c_str()); // converts user's input to an integer
		flag = false;
		while (flag == false)
		{
			for (int i = 0; i < 50; i++) // checks if user's input matches an item in the inventory. If it doesn't then the user must retype
			{
				if (transF[transCount].itemId != items[i].getItemID()) // flag stays false if there isn't a match
					flag = false;
				else if (transF[transCount].itemId == items[i].getItemID()) // flag changes to true if there is a match
				{
					flag = true;
					idMarker = i; // variable idMarker is set to the same as the counter variable i so the same item can be used outside of scope
					ProfitProjection = 0; // profit reset to 0
					// profit projection for specific item in the array is calculated
					ProfitProjection = (((items[i].getRetailPrice()) * (items[i].getItemCount())) - ((items[i].getWholeSale()) * (items[i].getItemCount())));
					
					break;
				}
				else if (transF[transCount].itemId == -1)
				{
					break; // if user types -1 loop is terminated
				}
			}
			if (flag == true) // if flag switches to true then data is pulled from array that relates to the item's ID
			{
				cout << "Item ID: ";
				cout << items[idMarker].getItemID() << endl;
				cout << "Item Description: ";
				cout << items[idMarker].getDescrip() << endl;
				cout << "Profit Projection: $";
				cout << ProfitProjection;
				cout << endl;
				break;
			}
			if (transF[transCount].itemId == -1) // breaks out of function if user aborts
				break; // exits final loop if user types -1
			cout << "Your entry does not match an item in stock. Please re-enter" << endl;
			cout << "Enter the Item ID or enter -1 to abort: ";
			cin >> transF[transCount].itemId;
			cin.ignore();
		}
		if (transF[transCount].itemId == -1) // breaks out of function if user aborts
			break;
		transCount++;
		
	}
}

// printOrigInventory function
void printOrigInventory(InventoryCostClass origInven[], int invenCount)
{
	cout << "Original Invntory: " << endl;
	for (int i = 0; i < invenCount; i++) // calls printData function in a forloop that couts everything in the original loop
	{
		origInven[i].printData();
		cout << endl;
	}
}

// printNewInventory function
void printNewInventory(InventoryCostClass updatedInven[], int updateCount)
{
	cout << "Updated Invntory: " << endl;
	for (int i = 0; i < updateCount; i++) // prints everything in the updated inventory array
	{
		updatedInven[i].printData();
		cout << endl;
	}
}

// updateInventory function
void updateInventory(InventoryCostClass origInven [], int invenCoun, int &updateCount, InventoryCostClass inventoryCopy[])
{
	bool flag = false;
	int transCount = 0;
	string fNameOpen = "", fileName = "", userInput = "1";
	string wholeSale = "", retail = "", idStr = "";
	transactionFile transU[50];

	cout << "Enter the name of the data file: ";
	cin >> fileName;
	cin.ignore();
	
	updateCount = invenCoun; // update the inventory copy's count

	for (int i = 0; i < fileName.length(); i++) // checks if user typed an extension. If they did, it's ignored.
	{
		if (fileName[i] == '.')
		{
			break;
		}
		fNameOpen += fileName[i];
	}
	dataFileIn.open(fNameOpen + ".dat", ios::in | ios::binary); // opens a binary file named what the user chose
	if (dataFileIn)
	{

		while (!dataFileIn.eof()) // reads in transactionFile file until EOF
		{
			dataFileIn.read(reinterpret_cast<char *>(&transU[transCount]), sizeof(transU[transCount]));
			transCount++; // keeps track of how many items are in the file
		}
		cout << endl;
		for (int i = 0; i < invenCoun; i++) //determines what to do based on the transaction code read in from the transaction file
		{
			for (int j = 0; j < transCount; j++)
			{
				if (origInven[i].getItemID() == transU[j].itemId)
				{
					if (transU[j].transCode[0] == 'A' && transU[j].transCode[1] == 'I')
					{
						inventoryCopy[i].setItemCount(inventoryCopy[i].getItemCount()+transU[j].quant); // adds quantity from file to current quantity
						inventoryCopy[i].setinventoryUpdateDate(); // updates date
					}
					else if (transU[j].transCode[0] == 'R' && transU[j].transCode[1] == 'I')
					{
						inventoryCopy[i].setItemCount(inventoryCopy[i].getItemCount() - transU[j].quant); // subtracts quantity from file from current quantity
						if (inventoryCopy[i].getItemCount() <= 0)
							inventoryCopy[i].setItemCount(0); // if value dips below 0 then value is set to 0
						inventoryCopy[i].setinventoryUpdateDate(); // date is updated
					}
					else if (transU[j].transCode[0] == 'C' && transU[j].transCode[1] == 'W')
					{
						inventoryCopy[i].setWholeSale(transU[j].wholeSaleCost); // updates wholesale cost
					}
					else if (transU[j].transCode[0] == 'C' && transU[j].transCode[1] == 'R')
					{
						inventoryCopy[i].setRetailPrice(transU[j].retailPrice); // updates retail price
					}
					else if (transU[j].transCode[0] == 'C' && transU[j].transCode[1] == 'D')
					{
						inventoryCopy[i].setDescrip(transU[j].desc); // updates item description
					}
				}
			}
		}
		
		dataFileIn.close(); // closes data file
	}
	else
		cout << "File Not Found. \n";
}

// createTransFile function
void createTransFile(string fName, InventoryCostClass inventory[])
{
	bool flag = false, firstadd = false, firstsubb = false;
	long offset = 0;
	int transCount = 0, idMarker;
	double wholesaleCost = 0, retailPrice = 0;
	string fNameEntry, userInput = "1", wholeSaleEntry = "", retailEntry = "";
	string idStr="";
	transactionFile transF[50];

	for (int i = 0; i < fName.length(); i++) // checks if user typed an extension. If they did, it's ignored.
	{
		if (fName[i] == '.')
		{
			break;
		}
		fNameEntry += fName[i];
	}
	dataFile.open(fNameEntry + ".dat", ios::out | ios::binary); // starts a new binary file named what the user chose
	
	do
	{
		idStr.clear();
		cout << "Enter the Item ID or enter -1 to abort: ";
		cin >> idStr;
		cin.ignore();
		if (idStr == "-1")
			break;
		while (idStr.length() != 6) // a loop that checks if the user's input is 6 characters long
		{
			cout << "The item ID must be 6 characters long. \n";
			cout << "Enter the item ID: ";
			idStr.clear();
			cin >> idStr;
			cin.ignore();
		}
		transF[transCount].itemId = atoi(idStr.c_str()); // converts user's input to an integer
		flag = false;
		while (flag == false)
		{
			for (int i = 0; i < 50; i++) // checks if user's input matches an item in the inventory. If it doesn't then the user must retype
			{
				if (transF[transCount].itemId != inventory[i].getItemID())
					flag = false;
				else if (transF[transCount].itemId == inventory[i].getItemID())
				{
					flag = true;
					idMarker = i;
					break;
				}
				else if (transF[transCount].itemId == -1)
				{
					break;
				}
			}
			if (flag == true)
				break;
			cout << "Your entry does not match an item in stock. Please re-enter" << endl;
			idStr.clear();
			cout << "Enter the Item ID or enter -1 to abort: ";
			cin >> idStr;
			cin.ignore();
			if (idStr == "-1") // exits loop if user types -1
				break;
			while (idStr.length() != 6) // a loop that checks if the user's input is 6 characters long
			{
				cout << "The item ID must be 6 characters long. \n";
				cout << "Enter the item ID: ";
				idStr.clear();
				cin >> idStr;
				cin.ignore();
			}
			transF[transCount].itemId = atoi(idStr.c_str()); // converts user's input to an integer
		}
		if (idStr == "-1")
			break;

		cout << endl;
		cout << "Enter the Transaction Code; AI(Add to Inventory)" << endl;
		cout << "RI(Reduce From Inventory),"
			<< "CW(Change Wholesale Cost), " << endl;
		cout << "CR(Change Retail Price), CD(Change item Description) : ";
		cout << endl;

		cin.getline(transF[transCount].transCode, sizeof(transF[transCount].transCode));
		for (int i = 0; i < TSIZE; i++) // capitalizes user input
		{
			transF[transCount].transCode[i] = (toupper(transF[transCount].transCode[i]));
		}
		while (1)
		{
			if (transF[transCount].transCode[0] == 'A' && transF[transCount].transCode[1] == 'I') // if user types AI then the user is prompted to type how many to add to the structure
			{
				cout << "How Much Would You Like To Add To The Item's Inventory: ";
				cin >> transF[transCount].quant;
				cin.ignore();
				break;
			}
			else if (transF[transCount].transCode[0] == 'R' && transF[transCount].transCode[1] == 'I') // if user types RI then the user is prompted to type how many to subtract from the array and that value is sent to the structure
			{
				cout << "How Much Would You Like To Subtract From The Item's Inventory: ";
				cin >> transF[transCount].quant;
				cin.ignore();
				break;
			}
			else if (transF[transCount].transCode[0] == 'C' && transF[transCount].transCode[1] == 'W') // overrites wholesale
			{
				cout << "Enter new Wholesale Cost: ";
				cin >> wholeSaleEntry;
				cin.ignore();
				wholesaleCost = verifyWholesaleCost(wholeSaleEntry); // makes sure the value is greater than 0
				while (wholesaleCost > (inventory[idMarker].getRetailPrice())) // verifies the wholesale cost is less than the retail price
				{
					cout << "The wholesale Cost is greater than the Retail Price of this item. \n";
					cout << "Re-enter with a Retail Price that is greater than the Wholesale Cost. \n";
					cout << "Enter the Wholesale Cost: ";
					cin >> wholeSaleEntry;
					cin.ignore();
					wholesaleCost = verifyWholesaleCost(wholeSaleEntry);

				}
				transF[transCount].wholeSaleCost = verifyWholesaleCost(wholeSaleEntry);
				break;
			}
			else if (transF[transCount].transCode[0] == 'C' && transF[transCount].transCode[1] == 'R')
			{
				cout << "Enter new Retail Price: ";
				cin >> retailEntry;
				cin.ignore();
				retailPrice = verifyRetailPrice(retailEntry); // makes sure value is greater than 0
				while (retailPrice < (inventory[idMarker].getWholeSale())) // verifies that the wholesale cost is less than the retail price
				{
					cout << "The wholesale Cost is greater than the Retail Price of this item. \n";
					cout << "Re-enter with a Retail Price that is greater than the Wholesale Cost. \n";
				
					cout << "Enter the Retail Price: ";
					cin >> retailEntry;
					cin.ignore();
					retailPrice = verifyRetailPrice(retailEntry);
				}

				transF[transCount].retailPrice = verifyRetailPrice(retailEntry);
				break;
			}
			else if (transF[transCount].transCode[0] == 'C' && transF[transCount].transCode[1] == 'D') // changes description
			{
				cout << "Enter new Item Description (up to 50 characters): " << endl;
				cin.getline(transF[transCount].desc, sizeof(transF[transCount].desc));
				break;
			}
			else
			{
				cout << "Invalid Entry" << endl;
				cout << endl;
				cout << "Enter the Transaction Code; AI(Add to Inventory)" << endl;
				cout << "RI(Reduce From Inventory),"
					<< "CW(Change Wholesale Cost), " << endl;
				cout << "CR(Change Retail Price), CD(Change item Description) : ";
				cout << endl;
				cin.getline(transF[transCount].transCode, sizeof(transF[transCount].transCode));
				for (int i = 0; i < 2; i++) // capitalizes user input
				{
					transF[transCount].transCode[i] = (toupper(transF[transCount].transCode[i])); // writes transaction code to data file
				}
			}
		}
		dataFile.write(reinterpret_cast<char *>(&transF[transCount]), sizeof(transF[transCount])); // writes structure array to data file
		transCount++;
		offset = transCount;
		
		
		if (transCount == 49)
			break; // breaks before array reaches max quantity
		cout << "Would you to perform another transaction? (1) for Yes (2) for No: ";
		cin >> userInput;
		cin.ignore();
		while (userInput != "1" && userInput != "2")
		{
			cout << "Invalid Input, Select either 1 or 2 \n";
			cout << "Would you to perform another transaction? (1) for Yes (2) for No: ";
			cin >> userInput;
			cin.ignore();
		}
		
	} while (userInput != "2"); // 2 aborts the function
	
	dataFile.close();
}

// displayMenu function
int displayMenu()
{
	int selection;

	cout << endl;
	cout << "-----------------------------------------------\n";
	cout << "1: Create an Inventory: \n";
	cout << "2: Display The Original Inventory \n";
	cout << "3: Create Transaction File \n";
	cout << "4: Update Inventory \n";
	cout << "5: Display Updated Inventory \n";
	cout << "6: Profit Projection \n";
	cout << "7: Exit \n";
	cout << "-----------------------------------------------\n";
	cout << "Enter your selection: ";
	cin >> selection;
	cin.ignore();
	while (selection < 0 || selection > 7)
	{
		cout << "User Choice Invalid. \n";
		cout << "Select a number between 1 and 6 \n";
		cout << "Enter your selection: ";
		cin >> selection;
		cin.ignore();
	}
	cout << endl;
	return selection; // returns user's choice as an int
}

// loadInventory function
void loadInventory(InventoryCostClass items[], int &count, InventoryCostClass inventoryCopy[])
{
	int iD, cont = 0, itemCo = 0;
	string descrip = "", date ="", idStr = "", wholesaleEntry = "", wholesaleCStr = "",
		retailEntry = "", retailCStr="";
	double wholesaleCost, retailPrice;
	
	count = 0;

	cout << "Creating Inventory: " << endl;
	while (1) // infinite loop
	{
		cout << "Enter the item ID: ";
		cin >> idStr;
		cin.ignore();
		while (idStr.length() != 6) // validates entry length
		{
			cout << "The item ID must be 6 characters long. \n";
			cout << "Enter the item ID: ";
			cin >> idStr;
			cin.ignore();
		}
		iD = atoi(idStr.c_str()); // converts entry to an int
		for (int i = 0; i < count; i++) // runs through array
		{
			while (iD == items[i].getItemID()) // checks if the item ID already exists
			{
				cout << "An item of that ID Number already exists \n";
				cout << "Enter the item ID: ";
				cin >> idStr;
				cin.ignore();
				while (idStr.length() != 6)
				{
					cout << "The item ID must be 6 characters long. \n";
					cout << "Enter the item ID: ";
					cin >> idStr;
					cin.ignore();
				}
				iD = atoi(idStr.c_str());
			}
		}
		cout << "Enter the Item Description: ";
		getline(cin, descrip);
		while (descrip.length() > 50) // validates description's length
		{
			cout << "The description is too long: Max Length is 50 characters" << endl;
			cout << "Enter the Item Description: ";
			getline(cin, descrip);
		}

		cout << "Enter the item's quantity: ";
		cin >> itemCo;
		cin.ignore();

		cout << "Enter the Wholesale Cost: ";
		cin >> wholesaleEntry;
		cin.ignore();
		wholesaleCost = verifyWholesaleCost(wholesaleEntry); // checks that whole sale cost is greater than 0

		cout << "Enter the Retail Price: ";
		cin >> retailEntry;
		cin.ignore();
		retailPrice = verifyRetailPrice(retailEntry); // checks that retail price is greater than 0

		while (wholesaleCost > retailPrice) // makes sure the retail price is greater than the wholesale cost
		{
			cout << "The wholesale Cost is greater than the Retail Price of this item. \n";
			cout << "Re-enter with a Retail Price that is greater than the Wholesale Cost. \n";
			cout << "Enter the Wholesale Cost: ";
			cin >> wholesaleEntry;
			cin.ignore();
			wholesaleCost = verifyWholesaleCost(wholesaleEntry);

			cout << "Enter the Retail Price: ";
			cin >> retailEntry;
			cin.ignore();
			retailPrice = verifyRetailPrice(retailEntry);
		}
		
		cout << "Enter the date mm/dd/yyyy: ";
		cin >> date; // user enters date manually

		// sets data for the item
		items[count].setItemID(iD);
		items[count].setDescrip(descrip);
		items[count].setItemCount(itemCo);
		items[count].setWholeSale(wholesaleCost);
		items[count].setRetailPrice(retailPrice);
		items[count].setUpdateDate(date);

		cout << endl;
		cout << "Would you like to enter another item? 1 for Yes, 2 for No: ";
		cin >> cont;
		while (cont < 1 || cont > 2)
		{
			cout << "Invalid Entry, select 1 or 2 \n";
			cout << "Would you like to enter another item? 1 for Yes, 2 for No: ";
			cin >> cont;
		}
		cout << endl;
		count++;

		if (cont == 2)
		{
			for (int i = 0; i < count; i++) // copies data to a duplicate array
			{
				inventoryCopy[i].setItemID(items[i].getItemID());
				inventoryCopy[i].setDescrip(items[i].getDescrip());
				inventoryCopy[i].setItemCount(items[i].getItemCount());
				inventoryCopy[i].setWholeSale(items[i].getWholeSale());
				inventoryCopy[i].setRetailPrice(items[i].getRetailPrice());
				inventoryCopy[i].setUpdateDate(items[i].getDate());
			}
			break;
		}
	}

}

// verifyWholesaleCost function
double verifyWholesaleCost(string cost)
{
	string wholesaleCStr = "";
	double wholesaleC = 0.0;

	// only adds digits and 2 other characters to the wholesale entry, everything else gets dropped
	for (int i = 0; i < cost.length(); i++)
	{
		if (isdigit(cost[i]))
		{
			wholesaleCStr += cost[i];
		}
		else if (cost[i] == '.')
		{
			wholesaleCStr += cost[i];
		}
		else if (cost[i] == '-')
		{
			wholesaleCStr += cost[i];
		}
	}
	wholesaleC = atof(wholesaleCStr.c_str()); // converts string to a double
	while (wholesaleC <= 0) // verifies that wholesaleCost is greater than or equal to 0
	{
		wholesaleCStr.clear(); // clears wholesale string and prompts user to retype data
		cout << "Enter a cost greater than $0 \n";
		cout << "Enter the Wholesale Cost: ";
		cin >> cost;
		cin.ignore();

		for (int i = 0; i < cost.length(); i++)
		{
			if (isdigit(cost[i]))
			{
				wholesaleCStr += cost[i];
			}
			else if (cost[i] == '.')
			{
				wholesaleCStr += cost[i];
			}
			else if (cost[i] == '-')
			{
				wholesaleCStr += cost[i];
			}

		}
		wholesaleC = atof(wholesaleCStr.c_str());
	}

	return wholesaleC; // returns the wholesaleC as a double
}

// verifyRetailPrice functions runs the exact same way as the verifyWholeSale function
double verifyRetailPrice(string retailE)
{
	string retailCStr = "";
	double retailP = 0.0;

	for (int i = 0; i < retailE.length(); i++)
	{
		if (isdigit(retailE[i]))
		{
			retailCStr += retailE[i];
		}
		else if (retailE[i] == '.')
		{
			retailCStr += retailE[i];
		}
		else if (retailE[i] == '-')
		{
			retailCStr += retailE[i];
		}
	}

	retailP = atof(retailCStr.c_str());
	while (retailP <= 0)
	{
		retailCStr.clear();
		cout << "Enter a price greater than $0 \n";
		cout << "Enter the Retail Price: ";
		cin >> retailE;
		cin.ignore();

		for (int i = 0; i < retailE.length(); i++)
		{
			if (isdigit(retailE[i]))
			{
				retailCStr += retailE[i];
			}
			else if (retailE[i] == '.')
			{
				retailCStr += retailE[i];
			}
			else if (retailE[i] == '-')
			{
				retailCStr += retailE[i];
			}
		}
		retailP = atof(retailCStr.c_str());
	}

	return retailP;
}
