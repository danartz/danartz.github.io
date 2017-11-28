#include "InventoryCostClass.h"
#include <iostream>
#include <string>
#include <ctime>
using namespace std;


string InventoryCostClass::getTodaysDate()
{
	string todaysDate;
	int LocalDays, LocalMonths, LocalYears;

	// time returns the number of seconds elapsed since midnight, Jan 1, 1970 UTC. 
	time_t now = time(0);
	tm localTime;

	// localtime_s converts the value referenced by the "now" variable from UTC date/time format
	// to the local date/time and places the value in the tm structure referenced in localTime.
	localtime_s(&localTime, &now);

	// The tm_mon is the month from 0 to 11 so I added 1 to make the range from 1 to 12.
	LocalMonths = (&localTime)->tm_mon + 1;
	LocalDays = (&localTime)->tm_mday;
	// The tm_year is the year since 1900 so I added 1900 to get the current year.
	LocalYears = (&localTime)->tm_year + 1900;

	todaysDate = to_string(LocalMonths);
	todaysDate += "/";
	todaysDate += to_string(LocalDays);
	todaysDate += "/";
	todaysDate += to_string(LocalYears);

	return todaysDate;
}

void InventoryCostClass::printData()
{
	InventoryClass::printData(); // calls print from base class
	cout << "Wholesale Cost: $";
	cout << getWholeSale() << endl;
	cout << "Retail Price: $";
	cout << getRetailPrice() << endl;
	cout << "Updated Date: ";
	cout << getDate() << endl;
}