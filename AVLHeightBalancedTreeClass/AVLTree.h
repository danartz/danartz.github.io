/* Daniel Artz
	This class allows for the creation of an AVL tree. The tree uses search properties and
	restructures itself to remain height balanced.
*/

#include <iostream>
#include <string>
#include <list>
#include <vector>

using namespace std;

class AVLTreeNode {
private:
	AVLTreeNode(int key, string value) {
		this->key = key;
		this->value = value;
		this->parentNode = nullptr;
		this->leftChild = nullptr;
		this->rightChild = nullptr;
	}
	int key;
	string value;
	AVLTreeNode* leftChild;
	AVLTreeNode* rightChild;
	AVLTreeNode* parentNode;
	friend class AVLTree;
};

class AVLTree {
public:
	AVLTree() {

	}

	// Destructor deletes all dynamic memory allocation
	~AVLTree() {
		for (int i = 0; i < getSize(); i++)
		{
			delete AVLTreeList.back();
			AVLTreeList.pop_back();
		}

	}

	// Overloaded ostream function call to allow a cout of the tree
	friend ostream& operator<<(ostream& os, const AVLTree& me)  {
		
		me.printTree(os);
		return os;
	}
	
	// Main insertion function, calls tree restructure functions and the recursive insertion function
	bool insert(int key, string value) {
		AVLTreeNode * iter = nullptr;
		int smallestHeight;
		int treeSize;
		if (AVLTreeList.size() == 0) {
			AVLTreeNode *treeNode;
			treeNode = new AVLTreeNode(key, value);
			AVLTreeList.push_back(treeNode);
			return true;
		}
		// This is repeated logic throughout the program. If no tree restructuring has occured, the root is the first
		// item in the list. Otherwise it assigns the new root to iter
		if (!rootChanged)
			iter = AVLTreeList.front();
		else
			iter = rootNode;
		AVLTreeNode * insertRecursive = insertHelper(key, value, iter);
		//restructure tree if it becomes unbalanced
		//
		if (insertRecursive != nullptr)
		{
			AVLTreeNode * tempNode = insertRecursive;
			// Loops through to find a point of imbalance in the tree and corrects it by performing rotations
			while (1) {
				// If unbalanced perform a right rotation
				AVLTreeNode * leftTree = nullptr;
				AVLTreeNode * rightTree = nullptr;
				if (tempNode->leftChild != nullptr) {
					leftTree = tempNode->leftChild;
				}
				if (tempNode->rightChild != nullptr) {
					rightTree = tempNode->rightChild;
				}
				if (balanceSubTree(leftTree, rightTree) >= 2) {
					// if tempNode leftTree, rightTree <= -1 do double right rotation
					if (balanceSubTree(leftTree, rightTree) <= -1) {
						singleLeftRotation(tempNode->leftChild);
					}
					//perform single right rotation and assign new root based on rotation
					AVLTreeNode * tempRoot = singleRightRotation(tempNode);
					assignRootNode(tempRoot);
					rootChanged = true;
					break;
				}
				else if (balanceSubTree(leftTree, rightTree) <= -2) {
					// if tempNode leftTree, rightTree >= 1 do double left rotation
					if (balanceSubTree(leftTree, rightTree) >= 1) {
						singleRightRotation(tempNode->rightChild);
					}
					AVLTreeNode * tempRoot = singleLeftRotation(tempNode);
					assignRootNode(tempRoot);
					rootChanged = true;
					break;
				}
				if (tempNode->parentNode == nullptr)
					break;
				tempNode = tempNode->parentNode;
			}
		}
		
		return insertRecursive;
	}

	// Recursive insertion function traverses to the bottom of the tree, inserting the number to a leaf position
	// Uses search tree properties
	AVLTreeNode * insertHelper(int key, string value, AVLTreeNode * iter) {
		if (iter->key == key)
			return nullptr;
		else if (key < iter->key) {
			if (iter->leftChild == nullptr) {
				AVLTreeNode *treeNode;
				treeNode = new AVLTreeNode(key, value);
				iter->leftChild = treeNode;
				treeNode->parentNode = iter;
				AVLTreeList.push_back(treeNode);

				return treeNode;
			}
			
			iter = iter->leftChild;
			return insertHelper(key, value, iter);
		}
		else {
			if (iter->rightChild == nullptr) {
				AVLTreeNode *treeNode;
				treeNode = new AVLTreeNode(key, value);
				iter->rightChild = treeNode;
				treeNode->parentNode = iter;
				AVLTreeList.push_back(treeNode);

				return treeNode;
			}

			iter = iter->rightChild;
			return insertHelper(key, value, iter);	
		}
			
	}

	// Logic that performs a single right rotation which reassigns all of the nodes their appropriate values
	AVLTreeNode * singleRightRotation(AVLTreeNode * iter) {
		if (iter->parentNode != nullptr)
			iter->parentNode->leftChild = iter->leftChild;
		if(iter->leftChild != nullptr)
			iter->leftChild->parentNode = iter->parentNode;
		iter->parentNode = iter->leftChild;
		if (iter->leftChild != nullptr) {
			iter->leftChild = iter->leftChild->rightChild;
			if(iter->leftChild != nullptr)
				iter->leftChild->parentNode = iter;
		}
		else {
			iter->leftChild = nullptr;
		}
		if(iter->parentNode != nullptr)
			iter->parentNode->rightChild = iter;
		return iter->parentNode;
	}

	// Logic that performs a single left rotation which reassigns all of the nodes their appropriate values
	AVLTreeNode * singleLeftRotation(AVLTreeNode * iter) {
		if (iter->parentNode != nullptr)
			iter->parentNode->rightChild = iter->rightChild;
		
		if (iter->rightChild != nullptr)
			iter->rightChild->parentNode = iter->parentNode;
		iter->parentNode = iter->rightChild;
		if (iter->rightChild != nullptr) {	
			iter->rightChild = iter->rightChild->leftChild;
			if(iter->rightChild != nullptr)
				iter->rightChild->parentNode = iter;
		}
		else
			iter->rightChild = nullptr;
		if(iter->parentNode != nullptr)
		iter->parentNode->leftChild = iter;
		return iter->parentNode;
	}

	//Find function performs recursive call to search for a given key value pair in the tree
	bool find(int key, string& value) {
		AVLTreeNode * iter = nullptr;
		if (!rootChanged)
			iter = AVLTreeList.front();
		else
			iter = rootNode;
		if (AVLTreeList.size() == 0) {
			return false;
		}
		if (key < iter->key) {
			iter = iter->leftChild;
			bool findKey = findHelper(key, value, iter);
			return findKey;
		}
		else if(key > iter -> key) {
			iter = iter->rightChild;
			bool findKey = findHelper(key, value, iter);
			return findKey;
		}
		else {
			value = iter->value;
			return true;
		}
		
	}

	// Helper recursive search function that returns false if an item isn't found along with assigning item not found to value
	// Else it returns true and assigns the value to value
	bool findHelper(int key, string& value, AVLTreeNode * iter) {
		if (iter == nullptr) {
			value = "Item not found";
			return false;
		}
		if (key == iter->key) {
			value = iter->value;
			return true;
		}
		if (key < iter->key) {
			iter = iter->leftChild;
			return findHelper(key, value, iter);
		}
		else {
			iter = iter->rightChild;
			return findHelper(key, value, iter);
		}	
	}

	// subtracts right subtree from left subtree to determine the balance factor
	int balanceSubTree(AVLTreeNode * leftTree, AVLTreeNode * rightTree) {
		int lHeight = 0;
		int rHeight = 0;
		if (AVLTreeList.size() <= 1)
			return 0;
		//return difference of the left and right sub-trees
		if (leftTree == nullptr) {
			lHeight = 0;
		}
		else {
			lHeight = (heightHelper(leftTree) + 1);
		}
		if (rightTree == nullptr) {
			rHeight = 0;
		}
		else {
			rHeight = (heightHelper(rightTree) + 1);
		}

		return lHeight - rHeight;
	}

	// Funtion that calls the recursive function height helper
	int getHeight() {
		if (AVLTreeList.size() <= 1)
			return 0;
		AVLTreeNode * iter = nullptr;
		if (!rootChanged)
			iter = AVLTreeList.front();
		else
			iter = rootNode;
		//return height after it's calculated recursively by heightHelper
		return heightHelper(iter);
	}

	//Used to test heights at different nodes in the tree
	int getHeightTest() {
		if (AVLTreeList.size() <= 1)
			return 0;
		AVLTreeNode * iter = nullptr;
		if (!rootChanged)
			iter = AVLTreeList.front();
		else
			iter = rootNode;
		iter = iter->rightChild;
		cout << endl;
		int treeHeight = heightHelper(iter);
		return treeHeight;
	}

	//when a rotation occurs assign new root node as root - this prevents incorrect restructuring
	void assignRootNode(AVLTreeNode * iter) {
		while (iter->parentNode != nullptr) {
			iter = iter->parentNode;
		}
		rootNode = iter;
	}

	// Recursive function that returns the largest depth of a given subtree
	int heightHelper(AVLTreeNode * iter) const{
		if (iter == nullptr)
			return 0;
		// account for subtrees with one node
		else if (iter->leftChild == nullptr && iter->rightChild == nullptr)
			return 0;
		// recursively travel to the deepest node in each path and calculate the depth
		AVLTreeNode * iterTemp = iter->leftChild;
		int leftChild = heightHelper(iterTemp);
		iterTemp = iter->rightChild;
		int rightChild = heightHelper(iterTemp);
		// return the larger depth and increase counter
		if (leftChild > rightChild)
			return 1 + leftChild;
		else
			return 1 + rightChild;
	}

	// Main function that returns an empty vector if the tree has 0 nodes. otherwise it calls a recursive helper function
	vector<string> findRange(int lowkey, int highkey) {
		vector<string> rangeOfValues;
		if (getSize() == 0)
			return rangeOfValues;
		else {
			AVLTreeNode * iter = nullptr;
			if (!rootChanged)
				iter = AVLTreeList.front();
			else
				iter = rootNode;
			rangeOfValues = findRangeHelper(lowkey, highkey, iter, rangeOfValues);
			return rangeOfValues;
		}
	}

	/*Function returns empty vector if no items in range are in tree, otherwise it traverses
	down the left child if greater than highkey or down the right child if less than lowkey
	This pushes the iterator pointer to the min or max of the given range and then all items within the range
	are added to the vector.
	*/
	vector<string> findRangeHelper(int lowkey, int highkey, AVLTreeNode * iter, vector<string> rangeOfValues) {
		if (iter == nullptr) {
			return rangeOfValues;
		}
		//These two else if statements pushes the iterator to the correct range
		else if (iter->key > highkey)
			return findRangeHelper(lowkey, highkey, iter->leftChild, rangeOfValues);	
		else if (iter->key < lowkey)
			return findRangeHelper(lowkey, highkey, iter->rightChild, rangeOfValues);
		else{
			rangeOfValues = findRangeHelper(lowkey, highkey, iter->leftChild, rangeOfValues);
			rangeOfValues.push_back(iter->value);
			rangeOfValues = findRangeHelper(lowkey, highkey, iter->rightChild, rangeOfValues);
			return rangeOfValues;
		}
	}

	//Main printTree function that calls the recursive print tree helper
	void printTree(ostream& os = cout) const {
		AVLTreeNode * iter = nullptr;
		AVLTreeNode * temp = nullptr;
		if (!rootChanged) {
			iter = AVLTreeList.front();
			temp = AVLTreeList.front();
		}
		else {
			iter = rootNode;
			temp = rootNode;
		}
		printTreeHelper(iter, os, temp);
	}

	// Recursive function prints the tree starting from the largest value and printing the smallest value last
	void printTreeHelper(AVLTreeNode * iter, ostream&os, AVLTreeNode * temp) const{
		if (temp == nullptr)
			return;
		printTreeHelper(iter, os, temp->rightChild);
		int subTreeHeight = heightHelper(temp);

		//Calculated the number of tabs by subtracting the of the subtree from the height of the entire tree
		for (int i = 0; i < heightHelper(iter) - subTreeHeight; i++)
		{
			cout << "\t";
		}
		cout << temp->value;
		cout << endl;
		printTreeHelper(iter, os, temp->leftChild);
	}

	//Returns the number of nodes in the tree
	int getSize() {
		return AVLTreeList.size();
	}

private:
	list <AVLTreeNode*> AVLTreeList;
	AVLTreeNode * rootNode;
	bool rootChanged = false;

};

