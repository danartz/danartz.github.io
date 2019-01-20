#include<iostream>
using namespace std;


class Stack {
private:

	int count;
	MyList stackArray[100];

public:
	Stack() {
		count = 0;
	}

	void push(MyList a) {
		stackArray[count] = a;
		count++;
	}

	MyList peek() {
		if (count > 0) {
			return stackArray[count - 1];
		}
		else {
			cout << "Error: Stack is Empty" << endl;
			return stackArray[0];
		}
	}

	void pop() {
		count--;
	}

	int length() {
		return count;
	}

	bool atCapacity() {
		return count > 99 ? true : false;
	}

	
};
