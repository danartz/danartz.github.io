#ifndef INVENTORYCOSTCLASS_H
#define INVENTORYCOSTCLASS_H
#include <iostream>
#include <string>
#include "InventoryClass.h"
using namespace std;

// class decleration
class InventoryCostClass : public InventoryClass
{
private:
	double wholesaleCost;
	double retailPrice;
	string updateDate;

public:
	// constructors
	InventoryCostClass() : InventoryClass()
	{
		wholesaleCost = 0;
		retailPrice = 0;
		updateDate = "";
	}
	
	// setters
	void setWholeSale(double wSale)
	{
		wholesaleCost = wSale;
	}
	void setRetailPrice(double retailP)
	{
		retailPrice = retailP;
	}
	void setUpdateDate(string dateEntry)
	{
		updateDate = dateEntry;
	}
	void setinventoryUpdateDate()
	{
		updateDate = getTodaysDate();
	}

	// getters
	double getWholeSale() const
	{
		return wholesaleCost;
	}
	double getRetailPrice()const
	{
		return retailPrice;
	}
	string getDate()const
	{
		return updateDate;
	}
	// get date function
	string getTodaysDate();
	// print function overrides base class
	virtual void printData() override;
};







#endif
