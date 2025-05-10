# CPU Scheduling Simulator

## Overview

The **CPU Scheduling Simulator** is a Java-based application designed to simulate various CPU scheduling algorithms. It offers a user-friendly graphical interface that allows users to add processes, select scheduling algorithms, and view results in visual formats such as Gantt charts. The simulator supports multiple scheduling algorithms to help users understand and compare different scheduling strategies.

## Features

### Supported Scheduling Algorithms

* **First Come First Serve (FCFS):**
  Non-preemptive; processes are executed in the order they arrive.

* **Shortest Job First (SJF):**

  * *Non-preemptive:* The shortest burst time process is scheduled first without interruption.
  * *Preemptive:* (Shortest Remaining Time First) Allows interruption if a new process has a shorter burst time.

* **Priority Scheduling:**

  * *Non-preemptive:* Processes are scheduled based on priority values without interruption.
  * *Preemptive:* A higher priority process can interrupt the currently running process.

* **Round Robin (RR):**
  Preemptive; each process is given a fixed time slice (quantum) for execution.

### Process Management

* Add processes with attributes:

  * Process ID
  * Burst Time
  * Priority
  * Arrival Time
* Clear all processes with a single button.

### Quantum Configuration (for RR)

* Input and configure quantum time for the Round Robin algorithm.

### Results Visualization

* Displays process execution results in a table.
* Generates Gantt charts to visualize execution order.
* Calculates and displays performance metrics:

  * Average Waiting Time
  * Average Turnaround Time
  * Average Response Time

## Project Structure

```bash
src/
├── cpuscheduler/
│   ├── algorithms/
│   │   ├── FCFS.java                   # Implements the FCFS scheduling algorithm
│   │   ├── SJFNonPreemptive.java       # Implements the non-preemptive SJF algorithm
│   │   ├── SJFPreemptive.java          # Implements the preemptive SJF algorithm
│   │   ├── PriorityNonPreemptive.java  # Implements the non-preemptive Priority algorithm
│   │   ├── PriorityPreemptive.java     # Implements the preemptive Priority algorithm
│   │   └── RoundRobin.java             # Implements the Round Robin scheduling algorithm
│   ├── Process.java                    # Represents a process with attributes like burst time, priority, and arrival time
│   ├── ProcessManager.java             # Manages ready and finished processes
│   └── gui/                            # Contains GUI components
```

## How to Use

### Add Processes

1. Enter process details:

   * Burst Time
   * Priority
   * Arrival Time
2. Click the **"Add Process"** button to add the process to the list.

### Select Algorithm

1. Choose one of the available scheduling algorithms from the dropdown.
2. For **Round Robin**, input the Quantum Time value.

### Run Scheduler

1. Click the **"Start Scheduling"** button to run the selected algorithm.
2. View results, including the Gantt chart and performance metrics.

### Clear Processes

* Reset the process list and input fields using the **"Clear All"** button.

## Requirements

* **Java Development Kit (JDK):** Version 8 or higher.
* **IDE:** IntelliJ IDEA (recommended) or any Java-compatible IDE.

## Installation

```bash
git clone https://github.com/your-username/cpu-scheduling-simulator.git
```

1. Open the project in your preferred IDE (IntelliJ IDEA recommended).
2. Build and run the project.

## Example Output

* **Gantt Chart:**
  Displays the order of process execution visually.

* **Results Table:**
  Shows each process's attributes and calculated metrics:

  * Waiting Time
  * Turnaround Time
  * Response Time

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request.
