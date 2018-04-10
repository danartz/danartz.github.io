.ORIG	x4000

ST	R7, SAVE_R7 ; save return address to memory R7
AND	R1, R1, #0	
AND	R5, R5, #0  ; clear r5 to use as a counter


;Cycle through ascii address to count how many digits


AND	R1, R1, #0
ST	R0, SAVE_R0  ; save user typed address of R0 to memory
AND	R0, R0, #0
LD	R2, SAVE_R0  ; load address of usser input string
CYCLE_ADD1
AND	R3, R3, #0	; clear register 3
LDR	R0, R2, #0	; load STR1 to register 0
;OUT
ADD	R2, R2, #1	; increment to next spot in address
ADD 	R1, R1, #1	; keep track of how many digits
LDR	R0, R2, #0	; load next value to check if its a 0
BRz	DIGIT_CHECK	; if its x0000 then address is done branch out
BR	CYCLE_ADD1	; unconditional loop to top

DIGIT_CHECK
;See if input is 4 digits

ADD	R3, R1, #-4	; add negative 4 to counter to see if it's the opposite
BRz	CHECK_HEX_BEGIN	 ; if zero begin evaluation
LD	R0, ERROR_1
BR	ERROR_REPORT	; else error

CHECK_HEX_BEGIN
AND	R2, R2, #0
LD	R2, SAVE_R0   ; load first character in address


;CHECK LOWER NUMBER BOUNDRY
CHECK_HEX0


AND	R3, R3, #0	; clear register 3
AND	R1, R1, #0	; Clear register 1
LDR	R0, R2, #0	; load STR1 to register 0
LD	R4, NEG_0	; load negative ascii 0
ADD	R3, R0, R4	; compare input to ascii 0
BRn	ERROR_REPORT	; if invalid error
BRz	ADD_NUM_MASK	; branch to increment part of loop
BRp	CHECK_UPPER_NUM	; if positive check upper boundry for numbers next

;CHECK UPPER NUMBER BOUNDRY
CHECK_UPPER_NUM

AND	R3, R3, #0	; clear register 3
AND	R4, R4, #0	; clear register 4
LD	R4, NEG_9	; load r4 with negative 9
ADD	R3, R0, R4	; compare neg 0 to user input
BRn	ADD_NUM_MASK ; change to branch to convert to number mask then to incremennt
CONTINUE_LOOP
BRz	ADD_NUM_MASK ; change to branch to convert to number mask then to increment
BRp	CHECK_LOWER_ALPHA
ADD	R2, R2, #1
;BR	CHECK_HEX0


;CHECK LOWER ALPHA BOUNDRY
CHECK_LOWER_ALPHA
AND	R3, R3, #0
AND	R4, R4, #0
LD	R4, NEG_A
ADD	R3, R0, R4
BRn	ERROR_REPORT
BRz	ADD_ALPHA_MASK	; change to branch to convert to alpha then to increment with a BR
BRp	CHECK_UPPER_ALPHA

;CHECK UPPER ALPHA BOUNDRY
CHECK_UPPER_ALPHA
AND	R3, R3, #0
AND	R4, R4, #0
LD	R4, NEG_F
ADD	R3, R0, R4
BRn	ADD_ALPHA_MASK ; change to branch to convert to alpha then to increment with a BR
BRz	ADD_ALPHA_MASK ; convert to to alpha then increment position
BRp	ERROR_REPORT

;ERROR INVALID
ERROR_REPORT
LD	R0, STR2 ; loads R0 with x0000 for error
LD	R7, SAVE_R7
RET

; ADD NUMBER MASK to strip ASCII values
ADD_NUM_MASK

AND	R3, R3, #0	; clear R3
AND	R4, R4, #0	; clear R4
AND	R1, R1, #0
; load mask
LD	R4, NUMBER_MASK	; Load mask
ADD	R3, R0, R4	; add mask to R0 and put in R3
; check if counter is 0



LD	R6, CHECK_0	; Load check for 0
ADD	R1, R5, #0	; check 0 against counter
BRz	SHIFT_12_NUM



AND	R1, R1, #0
LD	R6, CHECK_1	; Load check for 1
ADD	R1, R5, #-1	; check 1 against counter
BRz	SHIFT_8_NUM



AND	R1, R1, #0
LD	R6, CHECK_2	; Load check for 2
ADD	R1, R5, #-2	; check 2 against counter
BRz	SHIFT_4_NUM

AND	R1, R1, #0
LD	R6, CHECK_2	; Load check for 3
ADD	R1, R5, #-3	; check 3 against counter
BRz	SHIFT_0_NUM


;BR	INCREMENT_POSITION

; ADD ALPHA MASK TO STRIP ASCII values 
ADD_ALPHA_MASK
AND	R3, R3, #0	; clear R3
AND	R4, R4, #0	; clear R4
;AND	R1, R1, #0
;AND	R6, R6, #0
; load mask
LD	R4, ALPHA_MASK	; Load mask
ADD	R3, R0, R4	; add mask to R0
; check if counter is 0
LD	R6, CHECK_0	; Load check for 0
ADD	R1, R5, #0	; check 0 against counter

BRz	SHIFT_12_NUM
AND	R1, R1, #0
LD	R6, CHECK_1	; Load check for 1
ADD	R1, R5, #-1	; check 1 against counter

BRz	SHIFT_8_NUM
AND	R1, R1, #0
LD	R6, CHECK_2	; Load check for 2
ADD	R1, R5, #-2	; check 2 against counter

BRz	SHIFT_4_NUM

; may never get hit
AND	R1, R1, #0
LD	R6, CHECK_2	; Load check for 3
ADD	R1, R5, #-3	; check 3 against counter
BRz	SHIFT_0_NUM


BR	INCREMENT_POSITION

; if num shift 12 to the left
SHIFT_12_NUM

ST	R3, SAVE_NUM1T
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ST	R3, SAVE_NUM1
BR	INCREMENT_POSITION

; if num shift 8 to the left
SHIFT_8_NUM

ST	R3, SAVE_NUM2T
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ST	R3, SAVE_NUM2
BR	INCREMENT_POSITION

; if num shift 4 to the left
SHIFT_4_NUM


ST	R3, SAVE_NUM3T
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ST	R3, SAVE_NUM3
BR	INCREMENT_POSITION

; if num shift 0 to the left
SHIFT_0_NUM

ST	R3, SAVE_NUM4
BR	RETURN_TO_MAIN



;
;
;
;
;
;INCREMENT TO NEXT SPOT IN ADDRESS AND THEN CONVERT TO BINARY
INCREMENT_POSITION


;LD	R0, ERROR_2
;OUT

ADD	R5, R5, #1
ADD	R2, R2, #1
AND	R0, R0, #0
ADD	R0, R5, -3
;BRz	SHIFT_0_NUM
BR	CHECK_HEX0


; return to main program
RETURN_TO_MAIN
AND	R3, R3, #0	;clear registers
AND	R7, R7, #0
AND	R1, R1, #0
AND	R4, R4, #0

;Combine all binary converted words into one sixteen bit word
LD	R3, SAVE_NUM1	; Loaded both num and alpha for every position to add them together. One of them will be x0000 so this doesn't create an issue
LD	R7, SAVE_NUM2
ADD	R1, R3, R7
LD	R3, SAVE_NUM3
LD	R7, SAVE_NUM4
ADD	R4, R3, R7
ADD	R1, R1, R4




ST	R1, FINAL_ANSWER ; test
LD	R0, FINAL_ANSWER

LD	R7, SAVE_R7	; load return address
; load binary conversion into register 0
RET

HALT

SAVE_R0	.BLKW 1

STR1	.FILL x3163
STR2	.FILL x0000
SAVE_R7		.FILL x0000
NEG_4	.FILL	xFFFC
COUNTER	.FILL	x0030

;ASCII CHECK TABLE
NEG_0	.FILL	xFFD0
NEG_9	.FILL	xFFC7
NEG_A	.FILL	xFFBF
NEG_F	.FILL	xFFBA

;DECIMAL COUNTER CHECK
CHECK_0	.FILL	x0000
CHECK_1 .FILL	xFFCF
CHECK_2 .FILL	xFFCE
CHECK_3 .FILL	xFFCD



;error checking debugging outputs
ERROR_1	.STRINGZ "1"
ERROR_2 .STRINGZ "2"
ERROR_3 .STRINGZ "3"
ERROR_4 .STRINGZ "4"
ERROR_5 .STRINGZ "5"

;Save characters in memory
SAVE_NUM1	.FILL x0000
SAVE_ALPHA1	.FILL x0000
SAVE_NUM2	.FILL x0000
SAVE_ALPHA2	.FILL x0000
SAVE_NUM3	.FILL x0000
SAVE_ALPHA3	.FILL x0000
SAVE_NUM4	.FILL x0000
SAVE_ALPHA4	.FILL x0000

;test saved values
SAVE_NUM1T	.FILL x0000
SAVE_ALPHA1T	.FILL x0000
SAVE_NUM2T	.FILL x0000
SAVE_ALPHA2T	.FILL x0000
SAVE_NUM3T	.FILL x0000
SAVE_ALPHA3T	.FILL x0000
SAVE_NUM4T	.FILL x0000
SAVE_ALPHA4T	.FILL x0000

NUMBER_MASK	.FILL xFFD0
ALPHA_MASK	.FILL xFFC9

FINAL_ANSWER	.FILL x0000
TEST3		.FILL x0039

MAIN_COUNTER	.FILL x0030

.END

