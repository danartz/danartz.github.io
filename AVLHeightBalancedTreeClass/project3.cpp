#include "AVLTree.h"
#include <iostream>
using namespace std;

int main() {
	string value;
	AVLTree test;
	vector<string> testVector;
	test.insert(10, "ten");
	test.insert(11, "eleven");
	test.insert(8, "eight");
	test.insert(9, "nine"); 
	test.insert(5, "five");
	test.insert(4, "four");
	test.insert(13, "thirteen");
	cout << endl;
	/*bool findTest = test.find(2, value);
	cout << endl;
	cout << findTest;
	cout << endl;
	cout << value;
	cout << endl;*/
	cout << "Tree's Height: " << test.getHeight();
	cout << endl;
	testVector = test.findRange(1, 14);
	cout << endl;
	for (int i = 0; i < testVector.size(); i++)
	{
		cout << testVector.at(i);
		cout << " ";
	}
	cout << endl;
	cout << test;
	return 0;
}