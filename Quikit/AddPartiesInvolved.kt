package com.example.quickreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.add_parties_involved.*
import kotlinx.android.synthetic.main.add_parties_involved.appTitle
import kotlinx.android.synthetic.main.reportlistview.*

class AddPartiesInvolved : AppCompatActivity() {

    private lateinit var partiesInvolvedList: ArrayList<PartiesInvolved>
    private lateinit var report: Report
    private lateinit var party: PartiesInvolved
    private var creatingNew: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_parties_involved)
        var context = this

        this.creatingNew = intent.getBooleanExtra("CreatingNew", false)
        this.report = intent.getSerializableExtra("Report") as Report
        this.partiesInvolvedList = this.report.getPartiesInvolved()

        // Load in the party in if we're editing an existing party
        if (!creatingNew) {
            //this.partiesInvolvedList = report.getPartiesInvolved()
            this.party = this.partiesInvolvedList[intent.getIntExtra("Index", -1)]
            if(this.party.getPartyId() != -1)
            {
                Log.d("Party ID ", this.party.getPartyId().toString())
            }else{
                Log.d("bad Party ID ", this.party.getPartyId().toString())
            }
        }

        if(!this.creatingNew){
            this.edittext_lastName.setText(party.getLastName().toString())
            this.edittext_firstName.setText(party.getFirstName().toString())
            this.edittext_carMake.setText(party.getCarMake().toString())
            this.edittext_carModel.setText(party.getCarModel().toString())
            this.edittext_lisencePlate.setText(party.getLisencePlate().toString())
            this.edittext_ssn.setText(party.getSsn().toString())
            this.edittext_phoneNum.setText(party.getPhoneNum().toString())
            this.edittext_homeAddress.setText(party.getHomeAddress().toString())
            this.edittext_state.setText(party.getState().toString())
            this.edittext_city.setText(party.getCity().toString())
        }

        // Click to go to home screen, but lose all changes
        this.appTitle.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }

        addPartiesInvolved.setOnClickListener {
                var firstName = this.edittext_firstName.text.toString()
                var lastName = this.edittext_lastName.text.toString()
                var carMake = this.edittext_carMake.text.toString()
                var carModel = this.edittext_carModel.text.toString()
                var lisencePlate = this.edittext_lisencePlate.text.toString()
                var ssn = this.edittext_ssn.text.toString()
                var phoneNum = this.edittext_phoneNum.text.toString()
                var homeAddress = this.edittext_homeAddress.text.toString()
                var state = this.edittext_state.text.toString()
                var city = this.edittext_city.text.toString()

            // todo: instantiate partyinvolved object with text fields
            var ssnCheck: Long? = ssn.toLongOrNull()
            var phoneCheck: Long? = phoneNum.toLongOrNull()

            // Ensure key fields are filled in and done so properly, and begin party update / create
            if(checkFields()){
                var partyInvolved = PartiesInvolved(this.report.getDate(), carMake, carModel,
                    lisencePlate, firstName, lastName, ssnCheck!!.toLong(), phoneCheck!!.toLong(), homeAddress, state, city)

                //Update existing person
                if(!this.creatingNew){
                    this.partiesInvolvedList.removeAt(intent.getIntExtra("Index", -1))
                    Toast.makeText(context, "Party: $lastName.$firstName\tUpdated", Toast.LENGTH_SHORT).show()
                    val db = DatabaseManager(context)
                    if(this.party.getPartyId() == -1)
                        this.partiesInvolvedList.add(intent.getIntExtra("Index", -1), partyInvolved)
                    else{
                        db.updatePartyInvolved(this.party)
                        partyInvolved.setPartyId(this.party.getPartyId())
                        this.partiesInvolvedList.add(intent.getIntExtra("Index", -1), partyInvolved)
                    }

                }else{
                    Toast.makeText(context, "Party: $lastName.$firstName\tCreated", Toast.LENGTH_SHORT).show()
                    this.partiesInvolvedList.add(partyInvolved)
                }
                // May need to reassign this.report.partiesInvolvedList if this is found to make no change
                Log.d("Get First Name: ", firstName)
                val intent = Intent(this, ViewEditReport ::class.java)
                intent.putExtra("CreatingNew", false)
                intent.putExtra("Report", this.report)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "ERROR: A field is empty", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * Deletes partyInvolved from Report
         */

        deleteParty.setOnClickListener {
            val db = DatabaseManager(context)
            db.deletePartyInvolved(this.party.getPartyId())
            this.partiesInvolvedList.removeAt(intent.getIntExtra("Index", -1))
            val intent = Intent(this, ViewEditReport ::class.java)
            intent.putExtra("CreatingNew", false)
            intent.putExtra("Report", this.report)
            Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, ViewEditReport ::class.java)
            intent.putExtra("CreatingNew", false)
            intent.putExtra("Report", this.report)
            startActivity(intent)
        }
    }

    private fun checkFields(): Boolean {
        var firstName = this.edittext_firstName.text.toString()
        var lastName = this.edittext_lastName.text.toString()
        var carMake = this.edittext_carMake.text.toString()
        var carModel = this.edittext_carModel.text.toString()
        var lisencePlate = this.edittext_lisencePlate.text.toString()
        var ssn = this.edittext_ssn.text.toString()
        var phoneNum = this.edittext_phoneNum.text.toString()
        var homeAddress = this.edittext_homeAddress.text.toString()
        var state = this.edittext_state.text.toString()
        var city = this.edittext_city.text.toString()
        var ssnCheck: Long? = ssn.toLongOrNull()
        var phoneCheck: Long? = phoneNum.toLongOrNull()

        return ( firstName != "" &&
                lastName != "" &&
                carMake != "" &&
                carModel != "" &&
                lisencePlate != "" &&
                ssn != "" &&
                phoneNum != "" &&
                homeAddress != "" &&
                state != "" &&
                city != "" &&
                ssnCheck != null &&
                phoneCheck != null
                )
    }
}