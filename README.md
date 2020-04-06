# Interview exercise - Silver Bars

[![Travis Build Status](https://api.travis-ci.org/tonyklawrence/silverbars.svg)](https://travis-ci.org/tonyklawrence/silverbars)

## How to build / test

My implementation of the silver bars exercise written in Kotlin.

The chosen build system is gradle 6.3, this seemed to be the quickest and simplest option for this kotlin build.
Development was done using IntelliJ IDEA on MacOS, hopefully it will all work ok on Windows systems.  The gradle wrapper is included in the project so you can run the tests with the following command:

    $gradlew test

## Design decisions / assumptions

* I have assumed that all money values are small and in pence and therefore can be represented using Int, likewise, weights are all in kilograms and using Double will suffice.  This is for the sake of simplicity, I would consider using at least BigDecimal or possibly some quantity library for real world situations.
* The orders will be stored in memory in a mutable array.  Again, this is to keep things simple, I will not concern myself with issues such as concurrent access, mutation of state.
* Although not required it felt natural to generate and return an order id.  This does require the implementation of an id generator (the test one included is simplistic and can fail) but allows us to distinguish orders with the same values.
* When cancelling an order the most basic option is to remove the order from the in-memory list.  Whilst this works in our example we are losing information, instead we could store a cancel event and project our final state, this would give us an immutable event history.
* Although it is not specified, cancelling a non-existing order results in an error.  I'm not a huge fan of exceptions hence using kotlin's optional OrderNotFound? instead.
* When summarising/combining orders, this is done on-demand every time.  It's not very efficient so some sort of running total could be implemented but not required for this exercise.
* I chose to return all orders when requesting the summary (alternative would have been to request them individually by direction), so I created a summary class to separate the buy and sell orders.  I could have used a Pair but I prefer having them named so they are less likely to be confused. 
