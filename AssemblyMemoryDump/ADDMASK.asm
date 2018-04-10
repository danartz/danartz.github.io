.ORIG	x6000
ST	R7, SAVE_R73
ST	R0, SAVE_R0



; This trap adds final mask to right shifted characters
CHECK_HEX_BEGIN1   ; check first char see what it is if a char at all
AND	R2, R2, #0
AND	R0, R0, #0
LD	R0, SAVE_R0   ; load first character in address
LD	R3, NUMERIC_MASK ; add number mask
ADD	R0, R0, R3

; Test value to see if with mask it's in range of a numeric value. If not branch to add alpha mask instead
;CHECK LOWER NUMBER BOUNDRY
CHECK_HEX0


AND	R3, R3, #0	; clear register 3
AND	R1, R1, #0	; Clear register 1
LD	R4, NEG_0	; load negative ascii 0
ADD	R3, R0, R4	; compare input to ascii 0
BRn	ADD_ALPHA_MASK	; if invalid error
BRz	OUTPUT_NUM1	; branch to increment part of loop
BRp	CHECK_UPPER_NUM	; if positive check upper boundry for numbers next

;CHECK UPPER NUMBER BOUNDRY
CHECK_UPPER_NUM

AND	R3, R3, #0	; clear register 3
AND	R4, R4, #0	; clear register 4
LD	R4, NEG_9	; load r4 with negative 9
ADD	R3, R0, R4	; compare neg 0 to user input
BRn	OUTPUT_NUM1 ; change to branch to convert to number mask then to incremennt
BRz	OUTPUT_NUM1 ; change to branch to convert to number mask then to increment
BRp	ADD_ALPHA_MASK
;ADD	R2, R2, #1
BR	CHECK_HEX0


; Add alpha mask if not a number
ADD_ALPHA_MASK
AND	R2, R2, #0
AND	R0, R0, #0
LD	R0, SAVE_R0   ; load first character in address
LD	R3, ALPHA_MASK
ADD	R0, R0, R3
OUT
BR	FINISHEDDD

OUTPUT_NUM1
OUT

BR	FINISHEDDD


OUTPUT_NUM4
OUT

FINISHEDDD
;;;;;;;










LD	R7, SAVE_R73

RET


SAVE_R0	.BLKW 1


TEST5	.STRINGZ "P"
SAVE_R73	.FILL x0000
;Masks
NUMERIC_MASK	.FILL x0030
NEG_NUMERIC	.FILL xFFD0
ALPHA_MASK	.FILL x0037
TAB		.FILL x0009
;PRINT_X		.FILL x0078

SAVE_R7		.BLKW 1
;ASCII CHECK TABLE
NEG_0	.FILL	xFFD0
NEG_9	.FILL	xFFC7
NEG_A	.FILL	xFFBF
NEG_F	.FILL	xFFBA

;char addresses
;CHAR_1	.BLKW	1
;CHAR_2	.FILL x50B5
;CHAR_3	.FILL x50B6
;CHAR_4  .FILL x50B7

;char addresses
CHAR_1	.BLKW	1
CHAR_2	.BLKW	1
CHAR_3	.BLKW	1
CHAR_4  .BLKW	1

.END