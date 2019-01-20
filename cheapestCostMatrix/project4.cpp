#include <iostream>
#include <string>
#include "graph.h"
#include <sstream>
using namespace std;

string removeWhiteAndSelection(string);

int main() {
	string userInput;
	char selection;
	getline(cin, userInput);
	Graph testGraph;

	while(userInput != "q" && userInput != "Q") {
		if (userInput.empty()) {
			cout << "Invalid Input";
			cout << endl;
		}
		else {
			selection = userInput.at(0);
			string fileName;
			string temp;
			stringstream sstream;
			switch (selection) {
			case 'r':
				fileName = removeWhiteAndSelection(userInput);
				testGraph.readGraph(fileName);
				break;
			case 's':
				testGraph.computePaths();
				break;
			case 'c':
				testGraph.costMatrix();
				break;
			case 'p':
				testGraph.pathMatrix();
				break;
			case 'd':
				int count;
				count = 0;
				int disPathNodes[2];
				// Load full string of input into string stream
				sstream << userInput;
				while (!sstream.eof()) {
					// load inter etc into temp string
					sstream >> temp;
					// if temp is an integer add it to the integer array and increase count
					if (stringstream(temp) >> disPathNodes[count]) {
						count++;
					}
					// reset temp
					temp = "";
				}
				
				testGraph.displayPath(disPathNodes[0], disPathNodes[1]);
				break;
			case 'e':
				testGraph.empty();
				break;
			default:
				cout << "Invalid input" << endl;
				break;
			}
		}
		getline(cin, userInput);
	}

	

	return 0;
}

// Removes leading white space and extracts the filename of the graph file
string removeWhiteAndSelection(string s) {
	string temp;
	char tempChar;
	bool alphaNumericFlag = false;
	for (int i = 0; i < s.size(); i++)
	{
		if (i != 0) {
			while (alphaNumericFlag == false) {
				char trashChar = s.at(i);
				if (trashChar >= '0' && trashChar <= 'z') {
					alphaNumericFlag = true;
					break;
				}
				i++;
			}
			if (alphaNumericFlag) {
				tempChar = s.at(i);
				temp += tempChar;
			}

		}
	}
	return temp;
}