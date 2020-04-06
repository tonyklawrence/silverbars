# Interview exercise - Silver Bars

## How to build / test

The chosen build system is gradle 6.3, this seemed to be the quickest and simplest option for this kotlin build.
Development was done using IntelliJ IDEA on MacOS, hopefully it will all work ok on Windows systems.  The gradle wrapper is included in the project so you can run the tests with the follow command:

    $gradlew test

## Design decisions / assumptions

* I have assumed that all money values are small and in pence and therefore can be represented using Int, likewise, weights are all in kilograms and using Double will suffice.  This is for the sake of simplicity, I would consider using at least BigDecimal or some quant library for real world situations.
* The orders will be stored in memory in a mutable array.  Again, this is to keep things simple, I will not concern myself with issues such as concurrent access, mutation of state.  