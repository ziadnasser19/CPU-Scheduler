
CPU Scheduling Simulator
Overview
The CPU Scheduling Simulator is a Java-based application designed to simulate various CPU scheduling algorithms. It provides a user-friendly graphical interface that allows users to add processes, select scheduling algorithms, and view results in a visual format, including Gantt charts. The simulator supports various scheduling algorithms to help users understand and compare how different scheduling strategies affect process management.

Features
Supported Scheduling Algorithms:

FCFS (First Come First Serve): A non-preemptive algorithm where processes are executed in the order they arrive.

SJF (Shortest Job First): Can be non-preemptive or preemptive. The shortest burst time process is scheduled first.

Priority Scheduling: Can be non-preemptive or preemptive. Processes are scheduled based on priority values.

Round Robin (RR): Preemptive algorithm where processes are given a fixed time slice (quantum) for execution.

Process Management:

Add processes with attributes such as Process ID, Burst Time, Priority, and Arrival Time.

The option to clear the list of processes if needed.

Quantum Configuration (for RR):

Allow users to specify the quantum time for the Round Robin algorithm.

Results Visualization:

Displays process execution results in a table.

Generates Gantt charts to visualize the execution order of processes.

Calculates and displays performance metrics, including:

Average Waiting Time

Average Turnaround Time

Average Response Time

Project Structure
graphql
Copy
Edit
src/
├── cpuscheduler/
│   ├── algorithms/
│   │   ├── FCFS.java               # Implements the FCFS scheduling algorithm
│   │   ├── SJFNonPreemptive.java   # Implements the non-preemptive SJF algorithm
│   │   ├── SJFPreemptive.java      # Implements the preemptive SJF algorithm
│   │   ├── RoundRobin.java         # Implements the Round Robin scheduling algorithm
│   │   └── PriorityNonPreemptive.java # Implements the non-preemptive Priority algorithm
│   ├── Process.java                # Represents a process with attributes like burst time, priority, and arrival time
│   ├── SchedulerResult.java        # Encapsulates the results of a scheduling algorithm
│   └── gui/                        # Contains GUI components
How to Use
Add Processes:

Enter the details for each process, including Burst Time, Priority, and Arrival Time.

Click the Add Process button to add the process.

Select Algorithm:

Choose one of the available scheduling algorithms from the dropdown.

For Round Robin, enter the Time Quantum value.

Run Scheduler:

Click the Start Scheduling button to run the selected algorithm.

Results, including a Gantt chart and performance metrics, will be displayed.

Clear Processes:

Reset the process list and input fields using the Clear All button.

Requirements
JDK: Version 8 or higher.

IDE: IntelliJ IDEA (recommended) or any Java-compatible IDE.

Installation
Clone the repository:

bash
Copy
Edit
git clone https://github.com/your-username/cpu-scheduling-simulator.git
Open the project in your preferred IDE (IntelliJ IDEA recommended).

Build and run the project.

Example Output
Gantt Chart: Displays the order of process execution.

Results Table: Shows process attributes along with computed performance metrics:

Waiting Time

Turnaround Time

Response Time

