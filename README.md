# RMI Calculator and File Transfer - Complete User Guide

This guide will help you set up and run both the RMI Calculator and File Transfer applications. The guide is designed for beginners with no prior experience with Java RMI.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Directory Setup](#directory-setup)
3. [Compiling the Applications](#compiling-the-applications)
4. [RMI Calculator Application](#rmi-calculator-application)
   - [Running on the Same Machine](#running-calculator-on-the-same-machine)
   - [Running on Different Machines](#running-calculator-on-different-machines)
5. [RMI File Transfer Application](#rmi-file-transfer-application)
   - [Setting Up Files to Transfer](#setting-up-files-to-transfer)
   - [Running on the Same Machine](#running-file-transfer-on-the-same-machine)
   - [Running on Different Machines](#running-file-transfer-on-different-machines)
6. [Understanding the Logs](#understanding-the-logs)
7. [Troubleshooting](#troubleshooting)

## Prerequisites

- Java Development Kit (JDK) 8 or later installed on all machines
- Basic knowledge of using command line/terminal
- Machines on the same network for across-machine testing
- Firewall allowing traffic on port 1099 (RMI's default port)

## Directory Setup

1. Create a new directory for the project on all machines:
   ```
   mkdir rmi_demo
   cd rmi_demo
   ```

2. Save all the following Java files in this directory:
   - `CalcInterface.java`
   - `CalcImpl.java`
   - `CalcServer.java`
   - `CalcClient.java`
   - `FileTransferInterface.java`
   - `FileTransferImpl.java`
   - `FileTransferServer.java`
   - `FileTransferClient.java`

## Compiling the Applications

1. Navigate to your project directory and compile all files:
   ```
   javac *.java
   ```

2. If successful, you should see `.class` files for each Java file in the directory.

## RMI Calculator Application

### Running Calculator on the Same Machine

1. **Start the RMI Registry and Server:**
   Open a terminal/command prompt and run:
   ```
   java CalcServer
   ```
   You should see: "Calculator Server is running..."

2. **Start the Client:**
   Open another terminal/command prompt and run:
   ```
   java CalcClient
   ```

3. **Use the Calculator:**
   - Select operation (1-4) from the menu
   - Enter numbers when prompted
   - View results
   - Choose option 5 to exit

### Running Calculator on Different Machines

1. **On the Server Machine:**
   - Determine the IP address of the server (use `ipconfig` on Windows or `ifconfig` on Linux/Mac)
   - Run the server with the IP address parameter:
     ```
     java "-Djava.rmi.server.hostname=SERVER_IP_ADDRESS" CalcServer
     ```
     Replace SERVER_IP_ADDRESS with your actual server IP (e.g., 192.168.1.100)

2. **On the Client Machine:**
   - Run the client with the server's IP address:
     ```
     java CalcClient SERVER_IP_ADDRESS
     ```
     Replace SERVER_IP_ADDRESS with the same IP you used on the server

3. **Use the Calculator** as described above.

## RMI File Transfer Application

### Setting Up Files to Transfer

1. **Create a directory for server files:**
   ```
   mkdir server_files
   ```

2. **Add some test files** to the server_files directory (text files, images, etc.)

### Running File Transfer on the Same Machine

1. **Start the Server:**
   Open a terminal/command prompt and run:
   ```
   java FileTransferServer
   ```
   You should see: "File Transfer Server is running..."

2. **Start the Client:**
   Open another terminal/command prompt and run:
   ```
   java FileTransferClient
   ```

3. **Use the File Transfer Application:**
   - Option 1: List files available on the server
   - Option 2: Download a file (enter the file name when prompted)
   - Option 3: Exit
   - Downloaded files will be saved in the "client_files" directory

### Running File Transfer on Different Machines

1. **On the Server Machine:**
   - Create and populate the server_files directory as described earlier
   - Determine the IP address of the server
   - Run the server with the IP address parameter:
     ```
     java "-Djava.rmi.server.hostname=SERVER_IP_ADDRESS" FileTransferServer
     ```
     Replace SERVER_IP_ADDRESS with your actual server IP

2. **On the Client Machine:**
   - Run the client with the server's IP address:
     ```
     java FileTransferClient SERVER_IP_ADDRESS
     ```
     Replace SERVER_IP_ADDRESS with the same IP you used on the server

3. **Use the File Transfer Application** as described above.

## Understanding the Logs

The applications create log files that record timing information:

- `calculator_server_log.txt`: Logs operations performed on the calculator server
- `calculator_client_log.txt`: Logs client-side calculator operations
- `server_log.txt`: Logs file transfer operations on the server
- `client_log.txt`: Logs client-side file transfer operations

Each log entry includes:
- Timestamp (YYYY-MM-DD HH:MM:SS.SSS)
- Operation description
- Time taken in milliseconds

Example log entry:
```
2025-03-01 15:30:22.456 - Received file data (1048576 bytes) - Time taken: 345ms
```

## Troubleshooting

### Connection Issues

1. **Could not find or load main class error:**
   - Ensure you're in the directory containing the .class files
   - Check for typos in the command

2. **Connection refused error:**
   - Verify the server is running
   - Check if the IP address is correct
   - Ensure port 1099 is not blocked by firewall

3. **Hostname syntax error:**
   - Make sure there's no space after `-D` in the command:
     ```
     # CORRECT:
     java "-Djava.rmi.server.hostname=192.168.1.100" FileTransferServer
     
     # INCORRECT:
     java -D java.rmi.server.hostname=192.168.1.100 FileTransferServer
     ```

### File Transfer Issues

1. **File not found error:**
   - Check that the file exists in the server_files directory
   - Verify file name spelling (case-sensitive)

2. **Permission errors:**
   - Ensure you have read/write permissions for the directories

### General Tips

1. **Restart the registry if needed:**
   - If you encounter registry issues, try manually starting it:
     ```
     start rmiregistry
     ```
     (On Windows) or
     ```
     rmiregistry &
     ```
     (On Linux/Mac)

2. **Check logs for specific errors:**
   - The log files will contain detailed information about what's happening

3. **Recompile if you make changes:**
   - If you modify any Java files, recompile all files before running again
