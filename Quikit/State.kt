package com.example.quickreport

class State{
    class ReportState{
        companion object{
            private var newReportScreenState = false
            private var clickedOffReportScreenState = false
            fun getReportScreenState(): Boolean{return this.newReportScreenState}

            /**
             * Sets state to 1 after report is created for unique functionality on ViewEditReport screen
             */
            fun setReportStateCreated(){this.newReportScreenState = true}
            fun clickedOffTrue(){this.clickedOffReportScreenState = true}

            /**
             * Sets state back to 0 which is the default state
             */
            fun setReportStateDefault(){
                this.newReportScreenState = false
                this.clickedOffReportScreenState = false
            }

            fun setClickedOffReportDefault(){this.clickedOffReportScreenState = false}
        }
    }


}