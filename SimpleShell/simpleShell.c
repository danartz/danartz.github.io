#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdbool.h>
#include <signal.h>

// Function: void parse(char *line, char **argv)
// Purpose : This function takes in a null terminated string pointed to by 
//           <line>.  It also takes in an array of pointers to char <argv>.
//           When the function returns, the string pointed to by the 
//           pointer <line> has ALL of its whitespace characters (space, 
//           tab, and newline) turned into null characters ('\0').  The
//           array of pointers to chars will be modified so that the zeroth
//           slot will point to the first non-null character in the string
//           pointed to by <line>, the oneth slot will point to the second 
//           non-null character in the string pointed to by <line>, and so
//           on. In other words, each subsequent pointer in argv will point
//           to each subsequent "token" (characters separated by white space)
//           IN the block of memory stored at the pointer <line>.  Since all
//           the white space is replaced by '\0', every one of these "tokens"
//           pointed to by subsequent entires of argv will be a valid string
//           The "last" entry in the argv array will be set to NULL.  This 
//           will mark the end of the tokens in the string.
//           


void  parse(char *line, char **argv)
{
     // We will assume that the input string is NULL terminated.  If it
     // is not, this code WILL break.  The rewriting of whitespace characters
     // and the updating of pointers in argv are interleaved.  Basically
     // we do a while loop that will go until we run out of characters in
     // the string (the outer while loop that goes until '\0').  Inside
     // that loop, we interleave between rewriting white space (space, tab,
     // and newline) with nulls ('\0') AND just skipping over non-whitespace.
     // Note that whenever we encounter a non-whitespace character, we record
     // that address in the array of address at argv and increment it.  When
     // we run out of tokens in the string, we make the last entry in the array
     // at argv NULL.  This marks the end of pointers to tokens.  Easy, right?
    
     while (*line != '\0') // outer loop.  keep going until the whole string is read
        { // keep moving forward the pointer into the input string until
          // we encounter a non-whitespace character.  While we're at it,
          // turn all those whitespace characters we're seeing into null chars.

          while (*line == ' ' || *line == '\t' || *line == '\n' || *line == '\r')
           { *line = '\0';     
             line++;
           }

          // If I got this far, I MUST be looking at a non-whitespace character,
          // or, the beginning of a token.  So, let's record the address of this
          // beginning of token to the address I'm pointing at now. (Put it in *argv)
          // then we'll increment argv so that the next time I store an address, it 
          // will be in the next slot of the array of integers.

          *argv++ = line;          /* save the argument position     */

          // Ok... now let's just keep incrementing the input line pointer until
          // I'm looking at whitespace again.  This "eats" the token I just found
          // and sets me up to look for the next.

          while (*line != '\0' && *line != ' ' && 
                 *line != '\t' && *line != '\n' && *line !='\r') 
               line++;             /* skip the argument until ...    */
        }

     // Heh, I ran out of characters in the input string.  I guess I'm out of tokens.
     // So, whatever slot of the array at argv I'm pointing at?  Yeah, put a NULL
     // there so we can mark the end of entries in the table.

     *argv = NULL;                 /* mark the end of argument list  */
}

//Purpose:  This function iterates through the environment variable passed through main and prints it 
void printEnv(char **envp){
  while (*envp != NULL){
    printf("%s", *envp);
    envp++;
  }
  printf("\n");
}

//Purpose: This function traverses through argv and returns the size. I wrote this because I
// had issues finding a built in funtion that gave me consistently correct results.
int argvSizeReturn(char **argv){
 int size = 0;
  while(*argv !=NULL){
   *argv++;
   size++;
  }
  return size;
}

// Purpose: Creates copy of pointers to argv and excludes the delimeter because exec cannot take an &
// I chose to copy argv incase I needed to use it for something later. Also given strtok's destructive nature
// I was concerned it would cause issues with argv.
void copyArg(char **argd, char **argv){
  char *token;
  while(*argv !=NULL){
  token = strtok(*argv, "&");
  *argd = token;
  token = strtok(NULL, "\n");
  *argd++;
  *argv++;
  }
  *argd = NULL;
 }

void execute(char **argv){
int status;
bool backgroundProcessFlag = false;
//must malloc argd or run into memory issues
char **argd;
argd = malloc(64 * (sizeof(char*)));
// copy argv to argd without &
copyArg(argd, argv);
pid_t forkProcessID;
// If error exit
 if (argv[1] == NULL){
  forkProcessID = fork();
   if(forkProcessID < 0){
     printf("fork didn't work \n");
     exit(0);
   }
   // If child execute process
   if(forkProcessID == 0){
     if(WIFSIGNALED(status)){
       execvp(*argd, argd);
       _exit(1);
     }
   }
   else{
      waitpid(forkProcessID, &status, 0);
   }

}
  else
     { 
     // If & turn on background process flag
       if(strcmp(argv[argvSizeReturn(argv) -1], "&") == 0){
         backgroundProcessFlag = true;
       }
       forkProcessID = fork();
       // If error exit
       if(forkProcessID < 0){
         printf("fork didn't work \n");
         exit(0);
         }
         // If child process execute command with additional arguments
       else if(forkProcessID == 0){
         if(WIFSIGNALED(status)){
           execvp(*argd, argd);
           _exit(1);
           }
           
         }
       else{
       // If background flag, use WNOHANG to allow the parent process to resume as normal. Child process is stored in process table
       // and can be viewed using ps command
         if (backgroundProcessFlag){
          waitpid(forkProcessID, &status, WNOHANG);
          }
         else
         // else wait on child process to finish execution before resuming.
           waitpid(forkProcessID, &status, 0);
       }
     }

     free(argd);
}


     
int main(int argc, char **argv, char **envp)
{
     char  line[1024];   // This is the string buffer that will hold
                         // the string typed in by the user.  This 
                         // string will be parsed.  The shell will do
                         // what it needs to do based on the tokens it
                         // finds.  Note that a user may NOT type in 
                         // an input line of greater than 1024 characters
                         // because that's the size of the array.
                                     
     char  *largv[64];    // This is a pointer to an array of 64 pointers to
                          // char, or, an array of pointers to strings. 
                          // after parsing, this array will hold pointers
                          // to memory INSIDE of the string pointed to by 
                          // the pointer line.  argv[0] will be the string
                          // version of the first token inside of line... 
                          // argv[1] will be the second... and so on... 
                          // See the routine parse() for details.

     char shell_prompt[15]; // This string will hold the shell prompt string

     // set the default prompt
     strcpy(shell_prompt, "SillyShell");
     //ignore control-c signal
     signal(SIGINT, SIG_IGN);
     // The shell by default goes forever... so... while forever ;)

     while (1) 
       {  printf("%s> ",shell_prompt);  // display the shell prompt

	// Using an if statement to evaluate the user's input as it's typed. If NULL then EOF which is what CTRL-D does	
	// Exit program if CTRL-D
         if(fgets(line, 1024, stdin) == NULL)
           exit(1);                ;  // use the safe fgets() function to read 
                                     // the user's command line.  Why wouldn't
                                     // we use gets() here?
          line[strlen(line)-1]='\0'; // This is a dirty hack.  Figure it out maybe?

          if (*line != '\0') // If something was actually typed, then do something...
            { // First, get all the addresses of all of the tokens inside the input line
              parse(line, largv);     //   parse the line to break it into token references

              // Check the first token to see if there are any built in commands
              // we want to handle directly.  Do this with an "if/then/else" ladder.
              // if we hit the end of the ladder, we assume the command line was requesting
              // an external program be run as a child process and do that....

              //Added an additional case for internal functionality of printenv
     	      if (strcmp(largv[0], "printenv")  == 0){
                   printEnv(envp); 
                 } else
              if (strcmp(largv[0], "exit")      == 0) exit(0); else
              if (strcmp(largv[0], "done")      == 0) exit(0); else
              if (strcmp(largv[0], "newprompt") == 0) { if (largv[1] != NULL)
                                                          strncpy(shell_prompt, largv[1], 15); 
                                                        else
                                                         strncpy(shell_prompt, "SillyShell", 15);
                                                     } else 
              execute(largv);           /* otherwise, execute the command */
            }
            
     }
}

                

