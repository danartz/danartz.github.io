#include <iostream>
#include <fstream>
#include <vector>
#include <iomanip>
#include <climits>
using namespace std;

class Graph {
public:
	Graph(){
		numOfVertices = 0;
	}

	~Graph() {
		empty();
	}

	// This function reads a graph from a file and loads it into a matrix
	void readGraph(string fileName) {
		empty();
		ifstream inFile;
		int a, b, c;
		inFile.open(fileName);
		// If file exists run following operations
		if (inFile.is_open()) {
			inFile >> numOfVertices;
			// Expand graphMatrix to the size of the graph and instantiate it
			graphCMatrix = new int*[numOfVertices];
			for (int i = 0; i < numOfVertices; i++)
			{
				graphCMatrix[i] = new int[numOfVertices];
			}
			// Expand graphPathMatrix to size of the graph and instantiate it
			graphPathMatrix = new int*[numOfVertices];
			for (int i = 0; i < numOfVertices; i++)
			{
				graphPathMatrix[i] = new int[numOfVertices];
			}
			// Set default values of matrixes
			initializeMatrixDefault();
			// Read file into graphMatrix and zero the diagonal
			while (1) {
				
				for (int i = 0; i < 3; i++)
				{
					vector <int> path;
					inFile >> a >> b >> c;
					
					graphCMatrix[a][b] = c;
					
					if (inFile.eof()) {
						inFile.close();
						break;
					}
				}
				// if end of file break out of loop
				if (inFile.eof()) {
					inFile.close();
					break;
				}
			}
			// Set diagonal values to infinity
			zeroDiagonal();
		}
		else {
			cout << "File not found";
		}


	}

	//Clear matrixes so they can be reused
	void empty() {
		for (int i = 0; i < numOfVertices; i++)
		{
			delete graphCMatrix[i];
			delete graphPathMatrix[i];
		}
		numOfVertices = 0;
	}

	//Set default values of graphMatrix and graphPathMatrix to -1. -1 represents infinity
	void initializeMatrixDefault() {
		for (int i = 0; i < numOfVertices; i++)
		{
			for (int j = 0; j < numOfVertices; j++)
			{
				graphCMatrix[i][j] = -1;
			}
		}
		for (int i = 0; i < numOfVertices; i++)
		{
			for (int j = 0; j < numOfVertices; j++)
			{
				graphPathMatrix[i][j] = -1;
			}
		}
	}

	//Sets graphMatrix zeroes the diagonal values
	void zeroDiagonal() {
		for (int i = 0; i < numOfVertices; i++)
		{
			for (int j = 0; j < numOfVertices; j++)
			{
				if(j == i)
					graphCMatrix[i][j] = 0;
			}
		}
	}

	//Function that calls the recursive helper to display the path between two nodes
	void displayPath(int i, int j) {
		if (numOfVertices == 0)
			cout << "" << endl;
		else if (i < 0 || j > numOfVertices) {
			cout << "Invalid Path";
			cout << endl;
		}
		else if (graphCMatrix[i][j] == -1) {
			cout << "Invalid Path";
			cout << endl;
		}
		else if (i == j) {
			cout << i;
			cout << " -> ";
			cout << j;
			cout << "  cost: ";
			cout << graphCMatrix[i][j];
			cout << endl;
		}
		else if (graphPathMatrix[i][j] == -1) {
			cout << i;
			cout << " -> ";
			cout << j;
			cout << "  cost: ";
			cout << graphCMatrix[i][j];
			cout << endl;
		}
		else {
			cout << i;
			displayPathHelper(i, j);
			cout << " -> ";
			cout << j;
			cout << "  cost: ";
			cout << graphCMatrix[i][j];
			cout << endl;
		}
		
	}

	//Recursive helper function that traverses through the matrix and prints the correct path
	void displayPathHelper(int i, int j) {
		if (graphPathMatrix[i][j] == -1) {
			return;
		}
		
		displayPathHelper(i, graphPathMatrix[i][j]);
		cout << " -> ";
		cout << graphPathMatrix[i][j];
		displayPathHelper(graphPathMatrix[i][j], j);
	
	}

	//Prints a formated cost matrix
	void costMatrix() {
		cout << "--COST MATRIX--" << endl;
		// Formatting
		cout << "  ";
		for (int j = 0; j < numOfVertices; j++)
		{
			cout << setw(4) << right;
			cout << j;	
		}
		cout << endl;
		cout << "  ";
		for (int j = 0; j < numOfVertices; j++)
		{
			cout << setw(4) << right;
			cout << '-';
		}
		cout << endl;
		//Outputs cost matrix to screen
		for (int i = 0; i < numOfVertices; i++)
		{
			cout << i;
			cout << '|';
			for (int j = 0; j < numOfVertices; j++)
			{
				cout << setw(4) << right;
				if (graphCMatrix[i][j] == -1)
					cout << "X";
				else
					cout << graphCMatrix[i][j];
			}
			cout << endl;
			cout << endl;
		}
	}

	//Outputs the pathMatrix to the screen
	void pathMatrix() {
		cout << "--PATH MATRIX--" << endl;
		//Formatting
		cout << "  ";
		for (int j = 0; j < numOfVertices; j++)
		{
			cout << setw(4) << right;
			cout << j;
		}
		cout << endl;
		cout << "  ";
		for (int j = 0; j < numOfVertices; j++)
		{
			cout << setw(4) << right;
			cout << '-';
		}
		cout << endl;
		//Output path matrix
		for (int i = 0; i < numOfVertices; i++)
		{
			cout << i;
			cout << '|';
			for (int j = 0; j < numOfVertices; j++)
			{
				// Checks graph matrix for infinity values and outputs them to screen as X
				cout << setw(4) << right;
				if (graphCMatrix[i][j] == -1) {
					cout << "X";
				}
				else if (i == j) {
					cout << "";
				}
				// Checks graphPathMatrix for adjacency which is also represnted by -1 and then outputs D
				else if(graphPathMatrix[i][j] == -1)
					cout << "D";
				else {
					cout << graphPathMatrix[i][j];
				}
			}
			cout << endl;
			cout << endl;
		}
	}

	// Runs algorithm to update the shortest path between two points in the cost matrix. The pathMatrix is also updated.
	void computePaths() {
		for (int k = 0; k < numOfVertices; k++)
		{
			for (int i = 0; i < numOfVertices; i++)
			{
				if (graphCMatrix[i][k] != -1) {
		
					for (int j = 0; j < numOfVertices; j++)
					{
						
						if (graphCMatrix[k][j] != -1) {
							
							if (graphCMatrix[i][j] == -1 || graphCMatrix[i][j] > graphCMatrix[i][k] + graphCMatrix[k][j]) {
								if (j != i) {
									graphCMatrix[i][j] = graphCMatrix[i][k] + graphCMatrix[k][j];
									graphPathMatrix[i][j] = k;
								}
							}
						}
						
					}
				}
				
			}
		}
	}

private:
	int ** graphCMatrix;
	int ** graphPathMatrix;
	int numOfVertices;

};
