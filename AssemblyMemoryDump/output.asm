.ORIG	x5000
ST	R7, SAVE_R7
ST	R6, SAVE_R6
ST	R5, SAVE_R5
ST	R3, SAVE_R3


AND	R3, R3 #0 ; clear R3 to use as counter
AND	R4, R4, #0 ; clear R4 to do checks
AND	R2, R2, #0
AND	R5, R5, #0
AND	R6, R6, #0
AND	R1, R1, #0
ST	R0, SAVE_R0  ; save user typed address of R0 to memory


; Shift Right subroutine
SHIFTR_12	ST  R1, R1SAVE		; Save callee-save registers					
		ST  R2, R2SAVE																					
		AND R1, R1, #0		; R1 holds the itermediate result				
		AND R2, R2, #0		; R2 holds count for number of bits examined			
		ADD R2, R2, #15										
													
NextBit		ADD R0, R0, #0		; Test MSB for 1 or 0						
		BRn MSBIS1									
													
MSBIS0		ADD R1, R1, R1		;shift result left 						
					;force LSB to 0							
		ADD R0, R0, R0		;shift original value left; check next bit			
		BR  TestDone									
													
MSBIS1		ADD R1, R1, R1		;shift running total right					
		ADD R1, R1, #1		;force LSB to 1							
		ADD R0, R0, R0		;shift original value left; check next bit			
													
TestDone	ADD R2, R2, #-1										
		BRP NextBit										
													
Done		ADD R0, R1, #0		; Move result to R0						
	        LD  R2, R2SAVE		; Restore callee-save registers					
                LD  R1, R1SAVE	
		
;;;;;
ST	R0, CHAR_1
ADD	R3, R3, #1

ADD	R4, R3, -12
BRz	SHIFT_8CLEAR
BR	SHIFTR_12						
        											
; Clear registers		
SHIFT_8CLEAR

LD	R0, SAVE_R0										
AND	R3, R3, #0 ; clear R3 again
AND	R4, R4, #0

; Shift right 8 times
;Shift Right subroutine
SHIFTR_8	ST  R1, R1SAVE		; Save callee-save registers					
		ST  R2, R2SAVE																					
		AND R1, R1, #0		; R1 holds the itermediate result				
		AND R2, R2, #0		; R2 holds count for number of bits examined			
		ADD R2, R2, #15										
													
NextBit_2	ADD R0, R0, #0		; Test MSB for 1 or 0						
		BRn MSBIS1_2									
													
MSBIS0_2	ADD R1, R1, R1		;shift result left 						
					;force LSB to 0							
		ADD R0, R0, R0		;shift original value left; check next bit			
		BR  TestDone_2									
													
MSBIS1_2	ADD R1, R1, R1		;shift running total right					
		ADD R1, R1, #1		;force LSB to 1							
		ADD R0, R0, R0		;shift original value left; check next bit			
													
TestDone_2	ADD R2, R2, #-1										
		BRP NextBit_2										
													
Done_2		ADD R0, R1, #0		; Move result to R0						
	        LD  R2, R2SAVE		; Restore callee-save registers					
               LD  R1, R1SAVE	

ST	R0, CHAR_2	

ADD	R3, R3, #1
ADD	R4, R3, -8
BRz	TRUNKATE_2
BR	SHIFTR_8	

;
;
;Clear extra digits to the left of desired number
TRUNKATE_2
;Shift last word over 4 spots and add its negative to the current word, truncating the furthest left bit
LD	R5, CHAR_1
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
NOT	R5, R5
ADD	R5, R5, #1
LD	R2, CHAR_2
ADD	R2, R2, R5
ST	R2, CHAR_2
BR	SHIFT_4CLEAR

; Shift to the right 4 times
SHIFT_4CLEAR

LD	R0, SAVE_R0										
AND	R3, R3, #0 ; clear R3 again
AND	R4, R4, #0
AND	R5, R5, #0
AND	R2, R2, #0

;Shift Right subroutine
SHIFTR_4	ST  R1, R1SAVE		; Save callee-save registers					
		ST  R2, R2SAVE																					
		AND R1, R1, #0		; R1 holds the itermediate result				
		AND R2, R2, #0		; R2 holds count for number of bits examined			
		ADD R2, R2, #15										
													
NextBit_3	ADD R0, R0, #0		; Test MSB for 1 or 0						
		BRn MSBIS1_3									
													
MSBIS0_3	ADD R1, R1, R1		;shift result left 						
					;force LSB to 0							
		ADD R0, R0, R0		;shift original value left; check next bit			
		BR  TestDone_3									
													
MSBIS1_3	ADD R1, R1, R1		;shift running total right					
		ADD R1, R1, #1		;force LSB to 1							
		ADD R0, R0, R0		;shift original value left; check next bit			
													
TestDone_3	ADD R2, R2, #-1										
		BRP NextBit_3										
													
Done_3		ADD R0, R1, #0		; Move result to R0						
	        LD  R2, R2SAVE		; Restore callee-save registers					
               LD  R1, R1SAVE	

ST	R0, CHAR_3	

ADD	R3, R3, #1
ADD	R4, R3, -4
BRz	TRUNKATE_3
BR	SHIFTR_4	



;
;
;Clear 2 digits to the left of desired character
TRUNKATE_3
;Shift last word over 8 spots and add its negative to the current word, truncating the furthest left bit
AND	R4, R4, #0
AND	R3, R3, #0
LD	R3, CHAR_1
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
ADD	R3, R3, R3
NOT	R3, R3	        ; not R3
ADD	R3, R3, #1	; add 1 to get negative version of Char 1

; Shift the closest character 4 times and then negate it. Add it and the the other shifted left digit to remove both
LD	R5, CHAR_2
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
NOT	R5, R5		; not r5
ADD	R5, R5, #1	; add 1 to get negative version of char 2
LD	R2, CHAR_3
ADD	R2, R2, R3
ADD	R4, R2, R5
ST	R4, CHAR_3
BR	SHIFT_0CLEAR


; Clear appropriate registers
SHIFT_0CLEAR

LD	R0, SAVE_R0	
ST	R0, CHAR_4										
AND	R3, R3, #0 ; clear R3 again
AND	R4, R4, #0
AND	R5, R5, #0
AND	R2, R2, #0
AND	R1, R1, #0
AND	R6, R6, #0

; Dont shift to the right, just trunkate 3 digits to the left of the desired char
TRUNKATE_0
;Shift last word over 12 spots and add its negative to the current word, truncating the furthest left bit
AND	R4, R4, #0
AND	R3, R3, #0
LD	R3, CHAR_1
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
NOT	R3, R3
ADD	R3, R3, #1

; shift second digit 8 times
LD	R5, CHAR_2
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
ADD	R5, R5, R5
NOT	R5, R5
ADD	R5, R5, #1

; shift third digit 4 times
LD	R2, CHAR_3
ADD	R2, R2, R2
ADD	R2, R2, R2
ADD	R2, R2, R2
ADD	R2, R2, R2
NOT	R2, R2
ADD	R2, R2, #1

; add all negative versions to char4 to remove everything except char4
LD	R1, CHAR_4
ADD	R6, R5, R3
ADD	R4, R2, R6
ADD	R1, R1, R4
ST	R1, CHAR_4

LD	R0, PRINT_X
OUT
LD	R0, CHAR_1

; I had to create an extra trap. Dr. Gallagher aproved of this because I had too much code which pushed the save_r7 word too far away from where I attempted to load it
; Talk to him about it.
TRAP	x42

LD	R0, CHAR_2

TRAP	x42

LD	R0, CHAR_3

TRAP	x42

LD	R0, CHAR_4

TRAP	x42

LD	R7, SAVE_R7


LD	R6, SAVE_R6
LD	R3, SAVE_R3
LD	R5, SAVE_R5


RET


;Check if first value is alpha or numeric
;

; had to put in the middle of my code because it was too far away from the rest. OUT OF RANGE
SAVE_R0	.BLKW 1
SAVE_R6 .BLKW 1
SAVE_R3 .BLKW 1
SAVE_R5 .BLKW 1
R1SAVE		.BLKW 1											
R2SAVE		.BLKW 1	
ADDRESS_SR	.FILL x40EE
ADDRESS_DR	.FILL x0000
PRINT_X		.FILL x0078

;Masks
NUMERIC_MASK	.FILL x0030
NEG_NUMERIC	.FILL xFFD0
ALPHA_MASK	.FILL x0037

SAVE_R7		.BLKW 1
;ASCII CHECK TABLE
NEG_0	.FILL	xFFD0
NEG_9	.FILL	xFFC7
NEG_A	.FILL	xFFBF
NEG_F	.FILL	xFFBA

;slots for characters
CHAR_1		.FILL x0000
CHAR_2		.FILL x0000
CHAR_3		.FILL x0000
CHAR_4		.FILL x0000

TEST	.STRINGZ "T"


.END

