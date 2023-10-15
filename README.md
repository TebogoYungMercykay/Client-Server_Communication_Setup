# Project: Client-Server Chat Application Simulation

This project aims to test all the concepts learned so far regarding mutual exclusion and locking via practical implementation. The task is to simulate a chat application using a client-server communication setup without the use of sockets, but with multi-threading.

## `1. Implementation`

The client-server communication allows multiple clients to communicate with each other by sending a message destined for another client via a central server. The server knows all the clients in the network. Each client communication will be handled by two threads, a reader and a writer.

- **Reader**: Checks for any new message on the server for the client and logs the message in the client's chat.
- **Writer**: Sends new messages, destined for a specific client, to the server and logs the message in the client's chat.

At least four client objects will be created and initialized with at least ten random messages to send to a randomly selected client.

The server will be represented by a data structure holding messages for clients. Each thread has a fixed location on the server where it receives new messages. The server does not keep chat history; a new message for a specific client overrides the old one.

## `2. Mutual Exclusion`

Mutual exclusion will be enforced at the following key points:

- **Client Chat**: The reader and writer shall log messages using mutual exclusion.
- **Server**: Mutual exclusion shall be enforced to manage writers writing to the same client.

## `3. Output`

The following output will be produced:

- When a thread attempts to send a message: `(SEND) [Thread-Name]: { sender:[client-name] , recipient:[client-name]}`.
- When a thread successfully sends a message: `(SEND) [Thread-Name]: SUCCESSFUL`.
- When a thread reads a new message: `(RECEIVE) [Thread-Name]: { recipient:[client-name], sender:[client-name] }`.

## `4. Notes`

You will have to get creative with handling threads, queues, and locks. Each queue should have their own lock when accessed. Any locks used in the program will be written from scratch, i.e., no using Java's pre-built locks. Some of Java's pre-built data structures might be used. Any locks used will be fair.

## `5. Concepts`

The following concepts will be tested in this project:

- Consensus
- Semaphores
- TASLock
- TTASLock
- Exponential Backoff
- ALock
- CLH Lock
- Composite Lock
- Fine grain synchronisation
- OptimisticList synchronisation

# `Requirements before running codes`:

- Install an `IDE` that `compiles` and `runs` Java codes. Recommendation `VS Code`
- How to setup `WSL` Ubuntu terminal shell and run it from `Visual Studio Code`:
  visit: https://www.youtube.com/watch?v=fp45HpZuhS8&t=112s
- How to Install `Java JDK 17` on `Windows 11`: https://www.youtube.com/watch?v=ykAhL1IoQUM&t=136s
- #### `Installing Oracle JDK on Windows subsystem for Linux`.

  - Run WSL as Administrator
  - set -ex
  - NB: Update links for other JDK Versions
  - export JDK_URL=http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz
  - export UNLIMITED_STRENGTH_URL=http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip
  - wget --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" ${JDK_URL}
  - Extract the archive: tar -xzvf jdk-*.tar.gz
  - Clean up the tar: rm -fr jdk-*.tar.gz
  - Make the jvm dir: sudo mkdir -p /usr/lib/jvm
  - Move the server jre: sudo mv jdk1.8* /usr/lib/jvm/oracle_jdk8
  - Install unlimited strength policy: wget --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" ${UNLIMITED_STRENGTH_URL}
  - unzip jce_policy-8.zip
  - mv UnlimitedJCEPolicyJDK8/local_policy.jar /usr/lib/jvm/oracle_jdk8/jre/lib/security/
  - mv UnlimitedJCEPolicyJDK8/US_export_policy.jar /usr/lib/jvm/oracle_jdk8/jre/lib/security/
  - sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/oracle_jdk8/jre/bin/java 2000
  - sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/oracle_jdk8/bin/javac 2000
  - sudo echo "export J2SDKDIR=/usr/lib/jvm/oracle_jdk8 export J2REDIR=/usr/lib/jvm/oracle_jdk8/jre export PATH=$PATH:/usr/lib/jvm/oracle_jdk8/bin:/usr/lib/jvm/oracle_jdk8/db/bin:/usr/lib/jvm/oracle_jdk8/jre/bin export JAVA_HOME=/usr/lib/jvm/oracle_jdk8 export DERBY_HOME=/usr/lib/jvm/oracle_jdk8/db" | sudo tee -a /etc/profile.d/oraclejdk.sh

---

# `Makefile`

##### NB: A makefile Is Included to compile and run the codes on the terminal with the following commands:=

- make clean
- make
- make run

```Java
default:
	javac *.java
run:
	java Main
clean:
	rm -f *.class
	reset
	clear
tar:
	tar -cvz *.java -f Code.tar.gz
untar:
	tar -zxvf *.tar.gz
```

---

---

<p align="center">The End, Thank You</p>

---