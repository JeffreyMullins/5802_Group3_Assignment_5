# Assignment 5 Part 1

## Defect Report
(the file Part1Program.java is the code being refered to whenever code lines are referenced)

## How To Run
The program doens't do anything in particular except print some statements. If you run the main function it will create an object that runs all functions, each of which produces a different bug from SpotBugs. In order to see the SpotBugs report you should use the SpotBugs GUI. This can be opened with the JAR file called SpotBugs in the "\5802_Group3_Assignment_5\spotbugs-4.9.8\lib" folder. Once the GUI is opened go to File->NewProject and add the path "C:\Users\jeffr\5802_Group3_Assignment_5\part_1\out\artifacts\part_1_jar\part_1.jar" to the Classpath For Analysis section. Then click the analyze button to see SpotBugs breakdown. For more information see this link from the SpotBugs website: https://spotbugs.readthedocs.io/en/latest/gui.html

### CORRECTNESS Bug
SpotBugs identifies 2 Correctness bugs, I will be talking about the "Null Pointer dereference" one. This bug is caused by the function at line 32. This function creates a null string and 50% of the time it will assign a value to that string. When it tries to print the length of the string this function will sometimes throw a null pointer exception. This really is a bug and in any case where such a thing existed in a real program I would add a check to see if the String is null prior to using it.

### BAD PRACTICE Bug
This bug is called "Method names should start with lowercase letter". This is caused by the constructor being named "Part1Program1". This is not a bug that affects any performance, so I wouldn't call it a real bug. However, coding conventions are important, so I would change this.

### INTERNATIONALIZATION Bug
This bug is caused from the function on line 63. This code uses the "toUpperCase" function to make a string with non-english symbols uppercase. This causes an incorrect symbol to be printed. This is absolutely a bug and the correct Locale should be given to the function to make it print the desired output.

### MULTITHREAD CORRECTNESS Bug
This bug is caused by the multithreadedBug function. The issue here is that the variable x is synced inconsistently and is locked and inaccessable to the the Part1Program1 object called "p". This is definitely a real bug, even though it is a simple example. This should be corrected by actually implementing a locking structure that ensures when setX is called it has the ability to update x.

### PERFORMANCE Bug
This is caused by the function on line 57 called performanceBug. It creates a String object with a String object inside of it. This is inefficient and might cause more unexpected issues if the string was ever called. This is definitely a bug and the declaration of this string should be updated to simply be a single String object.

### DODGY CODE Bug
There are several of these identified by SpotBugs, I will be talking about the "Useless object created" bug. This bug claims that the objected created in the multithreadBug() function is useless because it is not doing anything once created. This isn't actually a bug considering there are other underlying issues with this function, also the use of the function is not captured by SpotBugs here. So I don't think this is a real but, however it is really useful to see a report of what object instances are not being used.


# Assignment 5 Part 2 SpotBugs Static Analysis Defect Report

## 1. Environment and Tool Configuration
* **Tool Name:** SpotBugs
* **Tool Version:** 4.8.6.0
* **Java Version:** JDK 11
* **Configuration:** * Effort: `Max`
    * Threshold: `Low`
    * Analysis targets: Bytecode
* **How to Run:**
    1. Open a terminal in the project root directory.
    2. Execute the command: `mvn clean compile spotbugs:check`
    3. To view the interactive results, run: `mvn spotbugs:gui`

---

## 2. Identified Defects

### Bug #1: [Reliance on default encoding]
* **Source Location:** `Microwave.java` (Line 34)
* **Bug Pattern:** `DM_DEFAULT_ENCODING`
* **Analysis Output:**
    > "Found a call to a method which will perform a byte to String (or String to byte) conversion, and will assume that the default platform encoding is suitable. This will cause the application behavior to vary between platforms. Use an alternative API and specify a charset name or Charset object explicitly"

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Can cause unknown issues in a different platform]
    * **Proposed Fix/Mitigation:** Replace with: REPL: for (Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);; System.out.println()) {

---

### Bug #2: [Condition has no effect]
* **Source Location:** `Controller.java` (Line 162)
* **Bug Pattern:** `UC_USELESS_CONDITION`
* **Analysis Output:**
    > "This condition always produces the same result as the value of the involved variable that was narrowed before. Probably something else was meant or the condition can be removed."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Logic error]
    * **Proposed Fix/Mitigation:** Replace with: if (powerLevel < 1 || powerLevel > 10)

---

---

### Bug #3: [Be wary of letting constructors throw exceptions.]
* **Source Location:** `Controller.java` (Line 35)
* **Bug Pattern:** `CT_CONSTRUCTOR_THROW`
* **Analysis Output:**
    > "Classes that throw exceptions in their constructors are vulnerable to Finalizer attacks. A finalizer attack can be prevented, by declaring the class final, using an empty finalizer declared as final, or by a clever use of a private constructor. See SEI CERT Rule OBJ-11 for more information."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Can leave obj in partially initialized state and vulnerable to malicious attackers]
    * **Proposed Fix/Mitigation:** [Declare controller class as final]

---

---

### Bug #4: [An overridable method is called from a constructor]
* **Source Location:** `Counter.java` (Line 15)
* **Bug Pattern:** `MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR`
* **Analysis Output:**
    > "Calling an overridable method during in a constructor may result in the use of uninitialized data. It may also leak the this reference of the partially constructed object. Only static, final or private methods should be invoked from a constructor. See SEI CERT rule MET05-J. Ensure that constructors do not call overridable methods"

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Can be overridden and cause nullpointer exception issue]
    * **Proposed Fix/Mitigation:** [change access modifier of clear() to private]

---

---

### Bug #5: [Field should be package protected]
* **Source Location:** `Microwave.java` (Line 9)
* **Bug Pattern:** `MS_PKGPROTECT`
* **Analysis Output:**
    > "A mutable static field could be changed by malicious code or by accident. The field could be made package protected to avoid this vulnerability."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Encapsulation issue]
    * **Proposed Fix/Mitigation:** [Change access modifier to private]

---

---

### Bug #6: [Inconsistent synchronization]
* **Source Location:** `Controller.java` (Line 43)
* **Bug Pattern:** `IS2_INCONSISTENT_SYNC`
* **Analysis Output:**
    > "The fields of this class appear to be accessed inconsistently with respect to synchronization.  This bug report indicates that the bug pattern detector judged that The class contains a mix of locked and unlocked accesses, The class is not annotated as javax.annotation.concurrent.NotThreadSafe, At least one locked access was performed by one of the class's own methods, and The number of unsynchronized field accesses (reads and writes) was no more than one third of all accesses, with writes being weighed twice as high as reads A typical bug matching this bug pattern is forgetting to synchronize one of the methods in a class that is intended to be thread-safe.You can select the nodes labeled "Unsynchronized access" to show the code locations where the detector believed that a field was accessed without synchronization. Note that there are various sources of inaccuracy in this detector; for example, the detector cannot statically detect all situations in which a lock is held.  Also, even when the detector is accurate in distinguishing locked vs. unlocked accesses, the code in question may still be correct."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Thread safety issue]
    * **Proposed Fix/Mitigation:** [Add synchronized to reset()]

---

---

### Bug #7: [Inconsistent synchronization]
* **Source Location:** `Controller.java` (Line 157)
* **Bug Pattern:** `IS2_INCONSISTENT_SYNC`
* **Analysis Output:**
    > "The fields of this class appear to be accessed inconsistently with respect to synchronization.  This bug report indicates that the bug pattern detector judged that The class contains a mix of locked and unlocked accesses, The class is not annotated as javax.annotation.concurrent.NotThreadSafe, At least one locked access was performed by one of the class's own methods, and The number of unsynchronized field accesses (reads and writes) was no more than one third of all accesses, with writes being weighed twice as high as reads A typical bug matching this bug pattern is forgetting to synchronize one of the methods in a class that is intended to be thread-safe. You can select the nodes labeled "Unsynchronized access" to show the code locations where the detector believed that a field was accessed without synchronization. Note that there are various sources of inaccuracy in this detector; for example, the detector cannot statically detect all situations in which a lock is held.  Also, even when the detector is accurate in distinguishing locked vs. unlocked accesses, the code in question may still be correct."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Thread safety issue]
    * **Proposed Fix/Mitigation:** [Add synchronized to setOpen()]

---

---

### Bug #8: [Inconsistent synchronization]
* **Source Location:** `Controller.java` (Line 197)
* **Bug Pattern:** `IS2_INCONSISTENT_SYNC`
* **Analysis Output:**
    > "The fields of this class appear to be accessed inconsistently with respect to synchronization.  This bug report indicates that the bug pattern detector judged that The class contains a mix of locked and unlocked accesses, The class is not annotated as javax.annotation.concurrent.NotThreadSafe, At least one locked access was performed by one of the class's own methods, and The number of unsynchronized field accesses (reads and writes) was no more than one third of all accesses, with writes being weighed twice as high as reads A typical bug matching this bug pattern is forgetting to synchronize one of the methods in a class that is intended to be thread-safe. You can select the nodes labeled "Unsynchronized access" to show the code locations where the detector believed that a field was accessed without synchronization. Note that there are various sources of inaccuracy in this detector; for example, the detector cannot statically detect all situations in which a lock is held.  Also, even when the detector is accurate in distinguishing locked vs. unlocked accesses, the code in question may still be correct."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Thread Safety issue]
    * **Proposed Fix/Mitigation:** [Add synchronized to getPowerLevel()]

---

---

### Bug #9: [Invokes run on a thread (did you mean to start it instead?)]
* **Source Location:** `Microwave.java` (Line 31)
* **Bug Pattern:** `RU_INVOKE_RUN`
* **Analysis Output:**
    > "This method explicitly invokes run() on an object.  In general, classes implement the Runnable interface because they are going to have their run() method invoked in a new thread, in which case Thread.start() is the right method to call."

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Concurrency error]
    * **Proposed Fix/Mitigation:** [Change to: ticker.start();]

---

---

### Bug #10: [Method concatenates strings using + in a loop]
* **Source Location:** `Timer.java` (Line 73)
* **Bug Pattern:** `SBSC_USE_STRINGBUFFER_CONCATENATION`
* **Analysis Output:**
> "The method seems to be building a String using concatenation in a loop. In each iteration, the String is converted to a StringBuffer/StringBuilder, appended to, and converted back to a String. This can lead to a cost quadratic in the number of iterations, as the growing string is recopied in each iteration.
Better performance can be obtained by using a StringBuffer (or StringBuilder in Java 5) explicitly.
For example:
// This is bad
String s = "";
for (int i = 0; i < field.length; ++i) {
    s = s + field[i];
}

// This is better
StringBuffer buf = new StringBuffer();
for (int i = 0; i < field.length; ++i) {
    buf.append(field[i]);
}
String s = buf.toString();
"

* **Defect Analysis:**
    * **Classification:** [Real Issue]
    * **Justification:** [Performance impact]
    * **Proposed Fix/Mitigation:** [Initialize a StringBuilder outside of the loop and use the .append() method within the loop, then return the result using builder.toString()]

---


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
