.ORIG	x3000
LEA	R0, HANOIOPENING
PUTS
GETC				; get user input
OUT				; output user input

ADD	R5, R0, #0
ST	R0, TMPUSRINPUT		; temporarily store user's ascii input
LD	R2, ASCIIMASK
ADD	R5, R5, R2
AND	R2, R2, #0

; push first local variable in main
STI	R5, STACKBOTTOM		; store main local variable n at bottom of stack
AND	R0, R0, #0
ADD	R0, R0, #2

; push 4th argument
LD	R6, STACKBOTTOM
ADD	R6, R6, #-1
STR	R0, R6, #0

;push 3rd argument
AND	R0, R0, #0
ADD	R0, R0, #3
ADD	R6, R6, #-1
STR	R0, R6, #0

;push 2nd argument
AND	R0, R0, #0
ADD	R0, R0, #1
ADD	R6, R6, #-1
STR	R0, R6, #0

;push 1st argument
LD	R5, STACKBOTTOM ; set R5 to point to bottom of stack
LDR	R0, R5, #0
ADD	R6, R6, #-1
STR	R0, R6, #0

; Output string move n disks from post 1 to 3
LEA	R0, INSTRUCTIONSSTR
PUTS
LD	R0, TMPUSRINPUT
OUT
LEA	R0, INSTRUCTIONSSTR2
PUTS


ST	R7, SAVER7
JSR	MOVEDISK
LD	R7, SAVER7





HALT



; Move disk subroutine
MOVEDISK	ADD	R6, R6, #-1	; push space for return value
; push return address
ADD	R6, R6, #-1
STR	R7, R6, #0

;push dynamic link
ADD	R6, R6, #-1
STR	R5, R6, #0

;set new frame pointer
ADD	R5, R6, #-1

; allocate space for local variables
ADD	R6, R6, #-2


; check for base case currently wrong offset
AND	R1, R1, #0
LDR	R1, R5, #4
ADD	R0, R1, #-1
BRnz	ENDRECURSION


; build caller stack frame for first recursive call
; push 4th param on stack
LDR	R0, R5, #6
ADD	R6, R6, #-1
STR	R0, R6, #0

;push 3rd param on stack
LDR	R0, R5, #7
ADD	R6, R6, #-1
STR	R0, R6, #0

; push 2nd param on stack
LDR	R0, R5, #5
ADD	R6, R6, #-1
STR	R0, R6, #0

; push 1st param on stack
LDR	R0, R5, #4
ADD	R0, R0, #-1
ADD	R6, R6, #-1
STR	R0, R6, #0

; First recursive call
JSR	MOVEDISK


;
; PrintF move disk n from startPost to EndPost
LEA	R0, MOVEDISKSTR		;load effective address of movediskstr string
PUTS
LD	R1, REVERSEASCIIMASK	; load mask to convert binary numbers back to ascii for output
LDR	R0, R5, #4		; load N from stack
ADD	R0, R0, R1		; add mask to N
OUT
LEA	R0, MOVEDISKSTR2	; load string to output
PUTS
LDR	R0, R5, #5		; load relative 5th value from stack
ADD	R0, R0, R1		; add ascii mask to value
OUT
LEA	R0, MOVEDISKSTR3
PUTS
LDR	R0, R5, #6		; load relative 6th value from stack
ADD	R0, R0, R1		; add ascii mask to value
OUT	
LEA	R0, MOVEDISKSTR4	; output string
PUTS

; build caller stack frame for SECOND recursive call
; push 4th param on stack
LDR	R0, R5, #5
ADD	R6, R6, #-1
STR	R0, R6, #0

;push 3rd param on stack
LDR	R0, R5, #6
ADD	R6, R6, #-1
STR	R0, R6, #0

; push 2nd param on stack
LDR	R0, R5, #7
ADD	R6, R6, #-1
STR	R0, R6, #0

; push 1st param on stack
LDR	R0, R5, #4
ADD	R0, R0, #-1
ADD	R6, R6, #-1
STR	R0, R6, #0

; Second recursive call
JSR	MOVEDISK


; Return if greater than 1 after recursive calls
;;;;;;;;;;;;;;;;;;;;;;;;;
; Start return process to end callee function
;Copy x0000 to return value slot of stack
LDR	R0, R5, #0
STR	R0, R5, #3

; pop local variables
ADD	R6, R5, #1

;pop dynamic link into R5
LDR	R5, R6, #0
ADD	R6, R6, #1

; pop return address into R7
LDR	R7, R6, #0
ADD	R6, R6, #1

; give control back to the caller function
RET
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



; if 1 or less than 1 then branch to here
ENDRECURSION
LEA	R0, MOVEDISKSTR
PUTS
LDR	R0, R5, #4
LD	R1, REVERSEASCIIMASK
ADD	R0, R0, R1
OUT
LEA	R0, MOVEDISKSTR2
PUTS
LDR	R0, R5, #5
;LD	R1, REVERSEASCIIMASK
ADD	R0, R0, R1
OUT
LEA	R0, MOVEDISKSTR3
PUTS
LDR	R0, R5, #6
;LD	R1, REVERSEASCIIMASK
ADD	R0, R0, R1
OUT
LEA	R0, MOVEDISKSTR4
PUTS


; Start return process to end callee function
;Copy x0000 to return value slot of stack
LDR	R0, R5, #0
STR	R0, R5, #3

; pop local variables
ADD	R6, R5, #1

;pop dynamic link into R5
LDR	R5, R6, #0
ADD	R6, R6, #1

; pop return address into R7
LDR	R7, R6, #0
ADD	R6, R6, #1

; give control back to the caller function
RET









; Strings
MOVEDISKSTR4	.STRINGZ "."
MOVEDISKSTR3	.STRINGZ " to post "
MOVEDISKSTR2	.STRINGZ " from post "
MOVEDISKSTR	.STRINGZ "\nMove disk "
INSTRUCTIONSSTR	.STRINGZ "\nInstructions to move "
INSTRUCTIONSSTR2 .STRINGZ " disks from post 1 to post 3:"
HANOIOPENING	.STRINGZ "--Towers of Hanoi-- \nHow many disks? "
SAVER7	.BLKW 1			; save spot for register 7
TMPUSRINPUT .BLKW 1		; store ascii values
STACKBOTTOM	.FILL	x5000	; address of first spot in stack
ASCIIMASK	.FILL xFFD0	; mask to remove ascii values
REVERSEASCIIMASK .FILL x0030	; mask to add ascii

.END