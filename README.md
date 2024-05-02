# CodeAnalyzer

## About

A small JavaFX application that analyzes your code for complexity and identifies parts 
that do not adhere to common naming conventions.
Complexity metrics are determined by the number of conditional statements within a method. 
Evaluation of code style is contingent on method names adhering to camelCase conventions.

## Setup

1. Clone the repository
    ```shell
    git clone https://github.com/kiiril/CodeAnalyzer
    ```
2. The start method in the Main class serves as the entry point for the application. Done!

## Overview

The primary idea is to parse Java files into an Abstract Syntax Tree (AST) and calculate essential metrics for visualization purposes. 
This is achieved by leveraging the JavaParser library (https://javaparser.org/getting-started.html) for parsing operations. 
The main logic for computing these metrics is encapsulated within the FileVisitor and MethodVisitor classes.
The graphical user interface (GUI) is divided into two scenes: the welcome scene and the main scene.
The welcome scene serves as an introduction to the application and provides the selection of a directory for analyze,
while the main scene hosts comprehensive visualizations based on the acquired metrics, presenting them in an intuitive manner.
The MethodComplexity class functions as a data object, aggregating all metrics related to a method. 
The MethodComplexityListProvider serves as a utility class that supplies data about all methods to be visualized.

## Demo

https://github.com/kiiril/CodeAnalyzer/assets/119041402/c4f03224-c00c-424b-9c2c-0b122c9a2b6b

## Testing

To conduct testing, you may use the /codebase directory from the resources.
