#ifndef INVENTORYCLASS_H
#define INVENTORYCLASS_H
#include <iostream>
#include <string>
using namespace std;


// Base Class
class InventoryClass
{
protected:
	int itemID;
	string itemDescrip; // max 50 characters
	int itemCount;
	 
public:
	// constructors
	InventoryClass()
	{ 
		itemID = 0, itemCount = 0, itemDescrip = "";
	}

	// getters
	int getItemID() const
	{
		return itemID;
	}
	int getItemCount() const
	{
		return itemCount;
	}
	string getDescrip() const
	{
		return itemDescrip;
	}

	// setters
	void setItemID(int id)
	{
		itemID = id;
	}
	void setItemCount(int iCount)
	{
		itemCount = iCount;
	}
	void setDescrip(string descrip)
	{
		itemDescrip = descrip;
	}
	// Main Print Function
	virtual void printData()
	{
		cout << "Item ID: #";
		cout << getItemID() << endl;
		cout << "Item Description: ";
		cout << getDescrip() << endl;
		cout << "Item Quantity: ";
		cout << getItemCount() << endl;
	}


};


#endif