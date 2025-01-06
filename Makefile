# Makefile for running and managing the project

.PHONY: all compile run test clean checkstyle format

# Default target
all: compile

# Compile the project
compile:
	mvn compile

# Run the project
run: compile
	mvn exec:java

# Run tests
test:
	mvn test

# Run tests with full stack trace
test-stack-trace:
	mvn -DtrimStackTrace=false test

# Checkstyle to enforce coding standards
checkstyle:
	mvn checkstyle:checkstyle

# Format code according to formatter rules
format:
	mvn formatter:format

# Clean build artifacts
clean:
	rm -f src/*.class bin/* test/*.class
	rm -rf target
	mvn clean
