#include <iostream>
#include <cassert>
#include <string>
using namespace std;


class ListNode {
private:
	ListNode(int itm, ListNode* nxt = nullptr) {
		data = itm;
		next = nxt;
	}
	int data;
	ListNode* next;
	friend class MyList;
	friend class stack;
};


class MyList
{
public:
	// Default constructor
	MyList()
	{
		head = tail = nullptr;
		sizeCounter = 0;
	}

	// Copy constructor
	MyList(const MyList& rhsStk)
	{
		copy(rhsStk);
	}

	// Destructor calls clear function and empties and deletes each node in the list
	~MyList()
	{
		clear();
	}

	//Same as stack push. Place item on top of list
	void insertFirst(int itm)
	{
		if (empty())
			head = tail = new ListNode(itm, head);
		else
			head = new ListNode(itm, head);
	}

	//Insert item to the last position of the list
	void insertLast(int itm) {
		if (empty()) {
			head = tail = new ListNode(itm, head);
		}
		else {
			tail = tail->next = new ListNode(itm);
		}
	}

	// return top item
	int front() const
	{
		if (head == nullptr) {
			return 0;
		}
		return head->data;
	}

	// remove top item
	void removeFirst()
	{
		assert(head != nullptr);
		ListNode* tmp = head;
		head = head->next;
		delete tmp;
		if (head == nullptr) {
			tail = nullptr;
		}
	}

	// return true if empty
	bool empty() const
	{
		return head == nullptr;
	}

	// return size of list
	int getSize() {
		int size = 0;
		ListNode* tmpPtr = head;
		while (tmpPtr) {
			tmpPtr = tmpPtr->next;
			size++;
		}

		return size;
	}

	//Print entire list
	void printList() {
		ListNode* tmpPtr = head;
		while (tmpPtr) {
			cout << tmpPtr->data;
			tmpPtr = tmpPtr->next;
		}
	}
	//overload = operator
	MyList& operator= (const MyList& rhs)
	{
		if (this != &rhs)
		{
			clear();
			copy(rhs);
		}
		return *this;
	}
	// clear entire list node by node
	void clear()
	{
		ListNode * doomed = head;
		while (head != nullptr)
		{
			head = head->next;
			delete doomed;
			doomed = head;
		}
		head = nullptr;
		tail = nullptr;
	}
	// Deep copy. Copy one node at a time to a new list
	void copy(const MyList& rhs)
	{
		ListNode *tmpFirst, *tmpLast;
		if (rhs.empty())
		{
			head = nullptr;
		}
		else
		{
			tmpFirst = tmpLast = new ListNode(rhs.head->data);
			for (ListNode* tmp = rhs.head->next; tmp != nullptr; tmp = tmp->next)
			{
				tmpLast = tmpLast->next = new ListNode(tmp->data);
			}
			head = tmpFirst;
			tail = tmpLast;
		}
	}


private:
	ListNode* head;
	ListNode* tail;
	int sizeCounter;

	
};

