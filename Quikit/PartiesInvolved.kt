package com.example.quickreport

import java.io.Serializable

/**
 *  Object that holds PartiesInvolved related fields
 */

class PartiesInvolved(dateTimeField: String, carMakeField: String, carModelField: String, licencePlateField: String,
                      firstNameField: String, lastNameField: String, ssnField: Long, phoneNumField: Long,
                      homeAddressField: String, stateField: String, cityField: String) : Serializable {
    private var carMake: String = ""
    private var carModel: String = ""
    private var lisencePlate: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var ssn: Long= 0
    private var phoneNum: Long= 0
    private var homeAddress: String = ""
    private var state: String = ""
    private var city: String = ""
    private var dateTime: String = ""
    private var partyId = -1

    fun getCarMake(): String{return carMake }
    fun getCarModel(): String{return carModel }
    fun getLisencePlate(): String{return lisencePlate }
    fun getFirstName(): String{return firstName }
    fun getLastName(): String{return lastName}
    fun getSsn(): Long{return ssn}
    fun getPhoneNum(): Long{return phoneNum}
    fun getHomeAddress(): String{return homeAddress}
    fun getState(): String{return state}
    fun getCity(): String{return city}
    fun getDateTime(): String{return dateTime}
    fun getPartyId(): Int{return partyId}
    fun setPartyId(partyId: Int) {this.partyId = partyId}
    fun setDateTime(date: String){this.dateTime = date}

    init{
        this.carMake = carMakeField
        this.carModel = carModelField
        this.lisencePlate = licencePlateField
        this.firstName = firstNameField
        this.lastName = lastNameField
        this.ssn = ssnField
        this.phoneNum = phoneNumField
        this.homeAddress = homeAddressField
        this.state = stateField
        this.city = cityField
        this.dateTime = dateTimeField
    }

    override fun toString(): String {
        return "$lastName, $firstName"
    }

    companion object {
        val name_array = ArrayList<String>()
    }

}