CT414: Distributed Systems & Co Operative Computing

## How to run the Exam System Server and Client
1. Open	the	ExamEngine.java class and run it (runs the exam	server)
2. Open	the	MathsClient.java class and run it (runs	the	exam client)
 - This	will return	an error as	the	RMI	registry is	not	running	yet
 - Note	that a directory called	out will be	created	within the Exam	System project directory
 - This	directory is where we need to run the RMI directory
3. Assuming	your machine can run `javac` – run the following commands	to run the RMI registry:
`$ cd ~/java-workspace/ExamSystem/out/production/ExamSystem`
`$ rmiregistry`
Commands are applicable	to Unix	based operating	systems
4. Re-run the ExamEngine.java class	and	the	MathsClient.java class

## How to use the Exam System
1. If the ExamEngine.java is running, run MathsClient.java:
Enter your username	and	password when prompted
. Username: a
. Password: a
2. Following a successful login	you	are	asked:
Would you like a summary of	assignments?
Yes	– gives	you	a summary of the assignments available to the student
No	– you will be logged out
3. If you answered yes to part (2):
Enter the name of the assessment you would like	to complete
4. Follow the prompts to complete/submit/re-try	the assessment
