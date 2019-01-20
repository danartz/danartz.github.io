#include <iostream>
#include "Slist.h"
#include "Stack-array.h"
#include "calculator.h"
#include <string>
using namespace std;

MyList flipResult(MyList);
void callAddition(MyList&, MyList&, Stack&, RpnCalc&);
void callMultiply(MyList, MyList, Stack&, RpnCalc&);
void callPower(MyList, MyList, Stack&, RpnCalc&);

int main() {

	
	char input;
	input = getchar();
	bool errorFlag = false;
	// main loop
	while (input != 'Q' && input != 'q') {
		Stack stack;
		// expression validation and calculation loop
		while (input != 'Q' && input != 'q') {
			MyList operand1;
			MyList operand2;
			RpnCalc calc;

			// check if input is an integer
			if (input >= '0' && input <= '9')
			{
				operand1.clear();
				// loop until leading zeroes are skipped
				while (input == '0')
				{
					input = getchar();
				}
				while (input >= '0' && input <= '9') {
					cout << input;
					int tmpValue = (int)input - 48;
					operand1.insertFirst(tmpValue);
					input = getchar();
					if (isspace(input)) {
						cout << " ";
					}
				}
				if (stack.atCapacity()) {
					cout << "Stack is at capacity, aborting request" << endl;
					break;
				}
				stack.push(operand1);
				MyList tempList;
				tempList = stack.peek();
			}
			// addition case
			else if (input == '+') {
				
				operand1.clear();
				operand2.clear();
				operand1 = stack.peek();
				stack.pop();
				// check for the correct number of operands
				if (stack.length() <= 0) {
					errorFlag = true;
				}
				// if correct run the addition function
				else {
					operand2 = stack.peek();
					cout << input;
					cout << " ";
					stack.pop();
					callAddition(operand1, operand2, stack, calc);
				}
				
					
			}
			// multiplication case
			else if (input == '*') {
				operand1.clear();
				operand2.clear();
				operand1 = stack.peek();
				stack.pop();
				// Check for the correct number of operands
				if (stack.length() <= 0) {
					errorFlag = true;
				}
				// if correct run the multiplication function
				else {
					operand2 = stack.peek();
					cout << input;
					cout << " ";
					stack.pop();
					callMultiply(operand1, operand2, stack, calc);
				}
				
			}
			// Power case
			else if (input == '^') {

				operand1.clear();
				operand2.clear();
				// Pop off the power
				operand1 = stack.peek();
				stack.pop();
				// check for the correct number of operands
				if (stack.length() <= 0) {
					errorFlag = true;
				}
				// if correct call the power function
				else {
					// Pop off the operand
					operand2 = stack.peek();
					cout << input;
					cout << " ";
					stack.pop();
					callPower(operand1, operand2, stack, calc);
				}
			}
			
			input = getchar();
			
		}
		
		// Get top stack item then reverse the list so it can be printed correctly
		MyList expression = stack.peek();
		MyList expressionFlipped = flipResult(expression);
		if (expressionFlipped.front() == 0) {
			cout << endl;
			cout << "0";
		}
		else if ((stack.length() - 1) != 0) {
			cout << endl;
			cout << "Expression Error" << endl;
		}
		else if (errorFlag) {
			cout << endl;
			cout << "Expression Error" << endl;
			errorFlag = false;
		}
		else {
			// Print reversed list
			cout << endl;
			cout << endl;
			expressionFlipped.printList();
		}
		// If Q get the next input and then consume the new line
		if (input == 'q' || input == 'Q') {
			cout << endl;
			input = getchar();
			input = getchar();
		}
		
	}



	//system("pause");
	
	return 0;
}
// Stack functions performed for addition and then calls the addition math function on the 2 operands
void callAddition(MyList& l1, MyList& l2, Stack& stack, RpnCalc& calc) {
	MyList sum;
	// Loop through digits and add them together until one of the operands is empty
	// This accounts for 2 operands of different lengths
	while (!(l1.empty() && l2.empty())) {
		if (l1.empty()) {
			sum.insertLast(calc.add(l1.front(), l2.front()));
			l2.removeFirst();
		}
		else if (l2.empty()) {
			sum.insertLast(calc.add(l1.front(), l2.front()));
			l1.removeFirst();
		}
		else {
			sum.insertLast(calc.add(l1.front(), l2.front()));
			l1.removeFirst();
			l2.removeFirst();
		}

	}
	// If a carry exists then insert it to the beginning of the list
	if (calc.getAddCarry() == 1) {
		sum.insertLast(1);
	}
	// If stack is holding 100 items an error is output to the screen and the item isn't pushed
	if (stack.atCapacity()) {
		cout << "Stack is at capacity, aborting request" << endl;
	}
	else {
		stack.push(sum);
	}
		
}
// Calls the multiply math function and addition functions with appropriate stack/list logic
void callMultiply(MyList l1, MyList l2, Stack& stack, RpnCalc& calc) {
	int count = 0;
	Stack prodStack;
	// Order matters, so check sizes of lists before multiplication
	if (l1.getSize() < l2.getSize()) {
		while (!l1.empty()) {
			MyList topOperandCopy;
			topOperandCopy.copy(l2);
			MyList product;
			// loops through l2 multiplying l1 by each digit one at a time
			while (!topOperandCopy.empty()) {
				product.insertLast(calc.multiply(l1.front(), topOperandCopy.front()));
				topOperandCopy.removeFirst();
			}
			count++;
			l1.removeFirst();
			// Inserts carry
			if (calc.getMultiplyCarry() != 0) {
				product.insertLast(calc.getMultiplyCarry());
			}
			//insert appropriate number of leading zeroes
			for (size_t i = 0; i < count - 1; i++)
			{
				product.insertFirst(0);
			}
			calc.resetCarries();
			prodStack.push(product);
			
		}
	}
	else {
		while (!l2.empty()) {
			MyList topOperandCopy;
			topOperandCopy.copy(l1);
			MyList product;
			// Loops through l1 multiplying l2 by each digit one at a time
			while (!topOperandCopy.empty()) {
				product.insertLast(calc.multiply(topOperandCopy.front(), l2.front()));
				topOperandCopy.removeFirst();
			}
			count++;
			l2.removeFirst();
			// if carry isn't a 0 insert carry
			if (calc.getMultiplyCarry() != 0) {
				product.insertLast(calc.getMultiplyCarry());
			}
			//insert appropriate number of leading zeroes
			for (size_t i = 0; i < count - 1; i++)
			{
				product.insertFirst(0);
			}
			calc.resetCarries();;
			prodStack.push(product);
		}
	}

	l1.clear();
	l2.clear();
	//Pop items off prodStack and sum them together. Push the final result onto the main stack
	while (prodStack.length() > 0) {
		l1.clear();
		l2.clear();
		l1 = prodStack.peek();
		prodStack.pop();
		if (prodStack.length() == 0) {
			break;
		}
		else {
			l2 = prodStack.peek();
			prodStack.pop();
			callAddition(l1, l2, prodStack, calc);
			calc.resetCarries();
			l1.clear();
			l1 = prodStack.peek();
		}
		
	}
	stack.push(l1);
	

}

// Power function that calls multiplication
void callPower(MyList power, MyList operand1, Stack& stack, RpnCalc& calc) {
	int n = 0;
	int count = 0;
	string multiplierString = "1";
	MyList sum;
	Stack powerStack;
	// Convert the list version of the power to an integer by multiplying each
	// value by its place value and then summing the values together
	while (!power.empty()) {
		int tmpValue = power.front();
		power.removeFirst();
		for (int i = 0; i < count; i++)
		{
			multiplierString.append("0");
		}
		n += tmpValue * stoi(multiplierString);
		count++;
	}
	// Multiplies the operand to itself n number of times
	if (n != 0 && n > 1) {
		callMultiply(operand1, operand1, powerStack, calc);
		calc.resetCarries();
		MyList l1;
		for (int i = 0; i < n - 2; i++) {
			l1.clear();
			l1 = powerStack.peek();
			powerStack.pop();
			callMultiply(l1, operand1, powerStack, calc);
			calc.resetCarries();
		}
		l1.clear();
		l1 = powerStack.peek();
		stack.push(l1);
	}
	// Special case for power of 0
	else if (n == 0) {
		operand1.clear();
		operand1.insertLast(1);
		cout << "0";
		cout << " ";
		stack.push(operand1);
	}
	// Special case for operand raised to the first power
	else {
		stack.push(operand1);
		operand1.clear();
	}
}

MyList flipResult(MyList expression) {
	MyList expressionFlipped;
 	while (!expression.empty()) {
		expressionFlipped.insertFirst(expression.front());
		expression.removeFirst();
	}
	return expressionFlipped;
}
