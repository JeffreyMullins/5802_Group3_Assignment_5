# SpotBugs Static Analysis Defect Report

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