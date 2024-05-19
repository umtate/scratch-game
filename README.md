# Scratch Game Application

This is a Java application for a scratch game.

## Prerequisites

Make sure you have the following installed:

- Java 17
- Maven

## Getting Started

To get started with the Scratch Game Application, follow these steps:

1. Clone this repository to your local machine.
2. Navigate to the project directory.

```bash
git clone git@github.com:umtate/scratch-game.git
cd scratch-game
```

3. Run Maven clean package command to build the project.

```bash
mvn clean package
```

## Usage

After building the project, you can run the application using the following command:

```bash
java -jar build/scratch-game.jar --config {configFileLocation} --betting-amount {betAmount}
```

Replace `{configFileLocation}` with the location of your configuration file and `{betAmount}` with the desired betting amount.
Your configuration should include the columns and rows of the matrix, as they are not optional.

Enjoy.
