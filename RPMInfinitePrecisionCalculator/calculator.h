#include <iostream>
#include <String>
using namespace std;

class RpnCalc {
public:
	RpnCalc() {
		addCarry = 0;
		multiplyCarry = 0;
	}


	// adds carry, and single position column of 2 different operands and returns result
	// also sets carry
	int add(int x, int y) {
		if (x + y + addCarry > 9) {
			string sValue = to_string(x + y + addCarry);
			char value = sValue[1];
			int finalAnswer = (int)value - 48;
			addCarry = 1;
			return finalAnswer;
		}
		else if (x + y + addCarry <= 9) {
			if (addCarry == 1) {
				addCarry = 0;
				return x + y + 1;
			}
			else {
				addCarry = 0;
				return x + y;
			}
		}
		
		
	}

	int getAddCarry() {
		return addCarry;
	}

	int multiply(int x, int y) {
		if ((x*y) + multiplyCarry > 9) {
			string sValue = to_string(x * y + multiplyCarry);
			char value = sValue[0];
			multiplyCarry = (int)value - 48;
			value = sValue[1];
			int finalAnswer = (int)value - 48;
			return finalAnswer;

		}
		else if((x*y) + multiplyCarry <=9){
			if (multiplyCarry != 0) {
				int tempCarry = multiplyCarry;
				multiplyCarry = 0;
				return x * y + tempCarry;
			}
			else {
				return x * y;
			}
		}
	}

	int getMultiplyCarry() {
		return multiplyCarry;
	}

	void resetCarries() {
		addCarry = 0;
		multiplyCarry = 0;
	}

private:
	int addCarry;
	int multiplyCarry;
	
};
