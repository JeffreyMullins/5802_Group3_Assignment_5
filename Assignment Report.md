#Assignment 5 Part 1

##Defect Report
(the file Part1Program.java is the code being refered to whenever code lines are referenced)

##How To Run
The program doens't do anything in particular except print some statements. If you run the main function it will create an object that runs all functions, each of which produces a different bug from SpotBugs. In order to see the SpotBugs report you should use the SpotBugs GUI. This can be opened with the JAR file called SpotBugs in the "\5802_Group3_Assignment_5\spotbugs-4.9.8\lib" folder. Once the GUI is opened go to File->NewProject and add the path "C:\Users\jeffr\5802_Group3_Assignment_5\part_1\out\artifacts\part_1_jar\part_1.jar" to the Classpath For Analysis section. Then click the analyze button to see SpotBugs breakdown. For more information see this link from the SpotBugs website: https://spotbugs.readthedocs.io/en/latest/gui.html

###CORRECTNESS Bug
SpotBugs identifies 2 Correctness bugs, I will be talking about the "Null Pointer dereference" one. This bug is caused by the function at line 32. This function creates a null string and 50% of the time it will assign a value to that string. When it tries to print the length of the string this function will sometimes throw a null pointer exception. This really is a bug and in any case where such a thing existed in a real program I would add a check to see if the String is null prior to using it.

###BAD PRACTICE Bug
This bug is called "Method names should start with lowercase letter". This is caused by the constructor being named "Part1Program1". This is not a bug that affects any performance, so I wouldn't call it a real bug. However, coding conventions are important, so I would change this.

###INTERNATIONALIZATION Bug
This bug is caused from the function on line 63. This code uses the "toUpperCase" function to make a string with non-english symbols uppercase. This causes an incorrect symbol to be printed. This is absolutely a bug and the correct Locale should be given to the function to make it print the desired output.

###MULTITHREAD CORRECTNESS Bug
This bug is caused by the multithreadedBug function. The issue here is that the variable x is synced inconsistently and is locked and inaccessable to the the Part1Program1 object called "p". This is definitely a real bug, even though it is a simple example. This should be corrected by actually implementing a locking structure that ensures when setX is called it has the ability to update x.

###PERFORMANCE Bug
This is caused by the function on line 57 called performanceBug. It creates a String object with a String object inside of it. This is inefficient and might cause more unexpected issues if the string was ever called. This is definitely a bug and the declaration of this string should be updated to simply be a single String object.

###DODGY CODE Bug
There are several of these identified by SpotBugs, I will be talking about the "Useless object created" bug. This bug claims that the objected created in the multithreadBug() function is useless because it is not doing anything once created. This isn't actually a bug considering there are other underlying issues with this function, also the use of the function is not captured by SpotBugs here. So I don't think this is a real but, however it is really useful to see a report of what object instances are not being used.

#Assignment 5 Part 3

##Assertion 1:

assert !(doorOpen && mode == Mode.COOK);


Which means that the microwave can never be cooking while the door is open.


###Where the variables change:


doorOpen: setOpen(bool)
mode: pressStart(), stopCooking(), pressClear(), reset()


pressStart() is the only way to enter Mode.COOK and pressStart()requires both doorOpen == false and mode != COOK so the only way to enter the COOK mode would be for the door to be already closed. If the door happens to be opened while in COOK mode, openDoor() will call stopCooking() which if in the cooking mode, will switch from COOK to HOLD. Then openDoor() will call setOpen(true), the other way in which setOpen(bool) can be reached is through closeDoor() being called. So the 2 cases in which the assertion can be reached is openDoor() → calls stopCooking() then setOpen(true) and closeDoor() → calls setOpen(false)


###Case 1: closeDoor() → calls setOpen(false)


When closeDoor() executes, doorOpen becomes false and the assertion becomes:
!(false && mode == COOK)
Which is always true, meaning the assertion cannot fail when closing the door.




###Case 2: openDoor() → calls stopCooking() then setOpen(true)


As explained above, If the microwave was cooking, stopCooking() puts the mode  to HOLD before the door is opened. So by the time setOpen(true) runs, doorOpen = true
and mode is never COOK so the assertion becomes:
!(doorOpen && mode == COOK)


Which is always true.


Since openDoor(), pressStart() and tick() are all synchronized, no other methods that can put the controller into cook would be able to run during openDoor() and pressStart() will always refuse to run if the door is open so the assertion is not at risk of any race conditions that may interfere. Therefore the assertion can not be violated.


##Assertion 2

Assertion 2:

assert level == 0 || mode == Mode.COOK;


Which means that if the microwave is powered above 0, the controller must be in cook mode.


###Where the variables change:


level: level is used as a parameter in runDevice(int level), and the only places that call runDevice are reset() and tick()	
mode: pressStart(), stopCooking(), pressClear(), reset()




There are 2 ways to reach the assertion 2 in the program, the first is by calling reset(), which will then call runDevice(0). The other is when tick() is called. This will run if the controller is in mode.COOK and timer.tick() returns true. Then tick() calls runDevice(powerLevel). 




###Case 1: reset() → runDevice(0)


When reset() is called, it will always call runDevice(0) and because the case explicitly says that level can equal 0, this will always be a safe path and the assertion will never fail. When reached with reset().


###Case 2: tick() → could call runDevice(powerLevel)


When this is called it will first check if the microwave is currently in cooking. If it is, it continues assuming that the oven is still cooking and therefore it should be energized to heat up with the given power level. The program should say once the timing has confirmed the oven is in the cooking state, nothing will change that state until the routine finishes, basically just check if we are still cooking and apply power if yes.This assumption is what the assertion is meant to enforce. But because tick() is synchronized and pressClear() isn’t synchronized, the assumption is not protected, as a possible race condition is possible. If the oven is in COOK, the clock enters tick(), tick() checks if it is in cook mode and continues, but before runDevice(powerLevel) can be ran, pressClear() can be ran and switch mode to HOLD, and then tick() would run the runDevice(powerLevel). This would cause level to be greater than 0 and mode to not equal cook, which would violate the assertion.This is caused by tick() assuming that once it checks the mode to equal cook, it will stay cook for the entirety of tick() being ran, which allows pressClear(), that can be ran whenever to create a race condition. This makes the assertion not safe.
