.ORIG x3000
LEA R6, CHECK_D2
LEA	R1, PROMPT_STR_BEGIN


;Loop that loads contents from address stored in R1 to R0 and then increments outputting entire string until it finds a null
LOOP_BEGIN		
LDR			R0, R1, #0		; load register 0 with contents stored at position in memory register 1 points to
AND 			R2, R0, -1		; sets condition
BRz			END_LOOP		; branches out of loop if condition is 0, because string is finished
OUT						; Output character stored in register 0
ADD			R1, R1, #1		; increment register 1 to point to the next character in memory to load
BR			LOOP_BEGIN		; unconditional branch back to beginning of loop
END_LOOP   		OUT			; output contents of register 0

LD	R0, RETURN_ASCII ; load return ascii and output
OUT

;Get first input to check if it's an x dont treat as part of address
AND			R1, R1, #0
AND			R4, R4, #0		; clear R4
GETC						; get first input char to store and check if it's an x
ADD			R1, R1, #1		; increment counter of number of inputs
OUT
ADD			R4, R0, #0		;Store first input into register 4
AND 			R2, R2, #0		;clear register 2
LEA			R2, SAVE_ADD1 		

;Rest of 4 hex input
INPUT_BEGIN	        GETC		 	; get user input
OUT						;output what user types to screen
ADD			R1, R1, #1		; increment counter of number of inputs

AND 			R3, R3, #0
AND			R5, R5, #0		; clear register 5
LDR			R5, R6, #0		; Load negative return

ADD			R3, R5, R0		; check if return was entered
BRz			CHECK_FORX		; if return pressed branch to check for an x character being used
STR			R0, R2, #0		; dont save return. Save values user enters, up to 80. Will check for correct size in input trap
ADD			R2, R2, #1
BR			INPUT_BEGIN				; If first character isn't an x branch back to 0


; Validate stored x
CHECK_FORX		AND R2, R2, #0		;clear register 2
LEA			R5, CHECK_X
ST			R1, COUNT_1
AND 			R1, R1, #0		; clear register 1
AND			R3, R3, #0						

LDR	R2, R5, #0				; Load negative x
ADD	R3, R2, R4				; check if return was entered
BRz	FINAL_ADDRESS_PROMPT			; If user inputs x then pass to input trap, otherwise loop again
LEA	R1, PROMPT_STR_BEGIN
BR	LOOP_BEGIN			

FINAL_ADDRESS_PROMPT	LEA R1, PROMPT_STR_END
;Loop that loads contents from address stored in R1 to R0 and then increments outputting entire string until it finds a null
LOOP_BEGIN2	LDR	R0, R1, #0		; load register 0 with contents stored at position in memory register 1 points to
AND 			R2, R0, -1		; sets condition
BRz			END_LOOP2		; branches out of loop if condition is 0, because string is finished
OUT						; Output character stored in register 0
ADD			R1, R1, #1		; increment register 1 to point to the next character in memory to load
BR			LOOP_BEGIN2		; unconditional branch back to beginning of loop
END_LOOP2   		OUT			; output contents of register 0

LD	R0, RETURN_ASCII ; load return ascii and output
OUT

;Second address input starts here
;Get first input of second address to check if it's an x
AND			R1, R1, #0
AND			R4, R4, #0		; clear R4
GETC						; get first input char to store and check if it's an x
ADD			R1, R1, #1		; increment counter of number of inputs
OUT
ADD			R4, R0, #0		;Store first input into register 4
LEA			R2, SAVE_ADD2 

;Rest of 4 hex input
INPUT_BEGIN2	        GETC		 	; get user input
OUT						;output what user types to screen
ADD			R1, R1, #1		; increment counter of number of inputs


AND	R3, R3, #0
AND	R5, R5, #0				; clear register 5
LDR	R5, R6, #0				; Load negative return
ADD	R3, R5, R0				; check if return was entered
BRz	CHECK_FORX2				; if return pressed branch to check for an x character being used
STR	R0, R2, #0				; dont save return. Save values user enters, up to 80. Will check for correct size in input trap
ADD			R2, R2, #1
BR	INPUT_BEGIN2				; If first character isn't an x branch back to 0


; Validate stored x
CHECK_FORX2	AND R2, R2, #0					;clear register 2
LEA	R5, CHECK_X
ST		R1, COUNT_2
AND 		R1, R1, #0					; clear register 1
AND		R3, R3, #0					; clear register 3	

LDR	R2, R5, #0				; Load negative x
ADD	R3, R2, R4				; check if return was entered
BRz	VALIDATE_ORDER			; If user inputs x then pass to input trap, otherwise loop again
LEA	R1, PROMPT_STR_END		; load address to second string prompt again
BR	LOOP_BEGIN2			; unconditional branch back to the beginning of second output loop


VALIDATE_ORDER		LEA R5, SAVE_ADD1	; load address of first char in add1
LEA			R6, SAVE_ADD2		; load address of first char in add 2
BR	CONTINUE_VALIDATE1


CONTINUE_VALIDATE2
ADD		R6, R6, #1		; inrecrement position to check next char
ADD		R5, R5, #1		; increment position to check next char

CONTINUE_VALIDATE1
LD	R3, COUNT_1
LD	R2, COUNT_2
NOT	R3, R3
ADD	R3, R3, #1
ADD	R2, R2, R3
BRp	CONTINUE_VALIDATE
BRz	CONTINUE_VALIDATE
;Reload strings if user error requires them to be reprompted
LEA		R1, PROMPT_STR_BEGIN		; load address of first char of first prompt again
LEA R6, CHECK_D2
BRn	LOOP_BEGIN


; checks size char from string 1 to string 2
CONTINUE_VALIDATE
LDR		R1, R5, #0			; load char in address 1
LDR		R4, R6, #0			; load char in address 2
AND		R2, R2, #0			; clearing R2
NOT		R4, R4				; getting negative of R4 putting in R3
ADD		R4, R4, #1
ADD		R2, R4, R1			; Add to see if first or second char is larger	
BRn	PRINT_INPUT	; if char is larger end evaluation
BRz		CONTINUE_VALIDATE2		; if first input runs out of characters end evaluation change to brz



;Reload strings if user error requires them to be reprompted
LEA		R1, PROMPT_STR_BEGIN		; load address of first char of first prompt again
LEA R6, CHECK_D2
BRp		LOOP_BEGIN   	; loop back to beginning if second number is smaller than the first number
	


; prints various ascii for approriate formatting
PRINT_INPUT
;Output final prompt

LEA	R0, FINALOUTPUT1
PUTS
LEA	R0, SAVE_ADD1
PUTS
LEA	R0, FINALOUTPUT2
PUTS
LEA	R0, SAVE_ADD2
PUTS
LD	R0, COLON_ASCII
OUT
LD	R0, RETURN_ASCII
OUT

; start validating address 1 and address 2
LEA	R0, SAVE_ADD1
TRAP	x40
ST	R0, STR1_CONVERT  ; store converted address in memory here
AND	R1, R1, #0  ; begin check to see if x0000 was returned
ADD	R1, R0, #0  ; check why value isnt x0000 ERROR HERE!!!!!!!!
BRz	END_PROGRAM ; if error x0000 is returned end program
LEA	R0, SAVE_ADD2
TRAP	x40
ST	R0, STR2_CONVERT  ; store converted address in memory here
AND	R1, R1, #0  ; begin check to see if x0000 was returned
ADD	R1, R0, #0  ; check why value isnt x0000 ERROR HERE!!!!!!!!
BRz	END_PROGRAM ; if error x0000 is returned end program



;Loop that processes and outputs data from x41 trap and x42 which I had to add because of having too much code
LD	R6, STR2_CONVERT
LD	R3, STR1_CONVERT
;actual beginning of loop
CONTINUE_DATAOUTPUT
ST	R3, STR_TEMP_HOLDER	; store data at a temp location while itterating through the loop
LD	R0, STR_TEMP_HOLDER
TRAP	x41
LD	R0, TAB_ASCII		;loads tab for formatted output
OUT
LD	R0, STR_TEMP_HOLDER
LDR	R0, R0, #0
TRAP	x41
LD	R0, RETURN_ASCII
OUT
LD	R0, STR_TEMP_HOLDER	; loads back current position in memory
NOT	R1, R6
ADD	R1, R1, #1
ADD	R5, R1, R0		; checks if current position is last position in loop
BRz	END_PROGRAM
ADD	R3, R3, #1	; Clearing register 1
AND	R1, R1, #1	; Clearing register 1
BR	CONTINUE_DATAOUTPUT

		
END_PROGRAM


		
		HALT
SAVE_FINAL		.BLKW	1 ; added this so if user types a number larger than the second number, then the next time the user types something it won't combine with the first in memory
STR_TEMP_HOLDER		.BLKW	1
FINALOUTPUT1		.STRINGZ "Memory contents x"
FINALOUTPUT2		.STRINGZ " to x"
COLON_ASCII		.FILL	x003A
TAB_ASCII		.FILL	x0009
RETURN_ASCII		.FILL   x000A
SAVE_R723		.FILL	x0000		
COUNT_2			.FILL	x0000					; counter keeps track of how many characters long string 2 is
COUNT_1			.FILL	x0000					; counter keeps track of how many characters long string 1 is
PROMPT_STR_BEGIN	.STRINGZ "Enter starting memory address:"
PROMPT_STR_END		.STRINGZ "Enter ending memory address:"
INPUT_FIRST		.FILL	x3200
CHECK_X			.FILL	xFF88
CHECK_D2		.FILL 	xFFF6 ; negative ascii for return
SAVE_ADD1		.BLKW	#25 ;first address
SAVE_ADD2		.BLKW	#25 ;second address
STR_HOLDER		.BLKW	#25 ; temp holder
STR_HOLDER2		.BLKW	#25 ; temp holder
STR1_CONVERT		.BLKW	1
STR1_CONVERTHOLDER	.FILL	x0000
STR1_CONVERTHOLDER2	.BLKW	1
STR2_CONVERT		.BLKW	1
SUCCESS			.STRINGZ "Address 1 smaller than address 2"
		.END