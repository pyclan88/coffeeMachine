package machine

import kotlin.system.exitProcess

enum class Beverage(val water: Int, val milk: Int, val beans: Int, val price: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}

enum class State {
    MAIN,
    BUY,
    FILL,
    TAKE,
    REMAINING,
    EXIT
}

class CoffeeMachine(
    private var water: Int = 400,
    private var milk: Int = 540,
    private var beans: Int = 120,
    private var cups: Int = 9,
    private var money: Int = 550
) {

    private var state = State.MAIN

    fun executeCommand() {
        when (state) {
            State.MAIN -> chooseAction()
            State.BUY -> chooseCoffee()
            State.FILL -> fillCoffeeMachine()
            State.TAKE -> takeMoney()
            State.REMAINING -> showCoffeeMachine()
            else -> exitProcess(0)
        }
    }

    private fun chooseAction() {
        println("\nWrite action (buy, fill, take, remaining, exit):")
        when (readln()) {
            "buy" -> state = State.BUY
            "fill" -> state = State.FILL
            "take" -> state = State.TAKE
            "remaining" -> state = State.REMAINING
            "exit" -> state = State.EXIT
        }
    }

    private fun chooseCoffee() {
        println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        when (readln()) {
            "1" -> makeCoffee(Beverage.ESPRESSO)
            "2" -> makeCoffee(Beverage.LATTE)
            "3" -> makeCoffee(Beverage.CAPPUCCINO)
            "back" -> state = State.MAIN
        }
    }

    private fun makeCoffee(beverage: Beverage) {
        if (water < beverage.water) {
            println("Sorry, not enough water!")
        } else if (milk < beverage.milk) {
            println("Sorry, not enough milk")
        } else if (beans < beverage.beans) {
            println("Sorry, not enough coffee beans")
        } else {
            water -= beverage.water
            milk -= beverage.milk
            beans -= beverage.beans
            cups -= 1
            money += beverage.price
            println("I have enough resources, making you a coffee!")
        }
        state = State.MAIN
    }

    private fun fillCoffeeMachine() {
        println("Write how many ml of water you want to add:")
        water += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milk += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        beans += readln().toInt()
        println("Write how many disposable cups you want to add:")
        cups += readln().toInt()
        state = State.MAIN
    }

    private fun takeMoney() {
        println("I gave you $$money")
        money = 0
        state = State.MAIN
    }

    private fun showCoffeeMachine() {
        println("\nThe coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$beans g of coffee beans")
        println("$cups disposable cups")
        println("$$money of money")
        state = State.MAIN
    }
}

fun main() {
    val assistant = CoffeeMachine()
    while (true) {
        assistant.executeCommand()
    }
}