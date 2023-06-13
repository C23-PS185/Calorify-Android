package com.calorify.app.data.local

object FoodDict {
    val wordMap = mapOf(
        "baked_potato" to "Baked Potato",
        "bibimbap" to "Bibimbap",
        "cheesecake" to "Cheesecake",
        "chicken_curry" to "Chicken Curry",
        "chicken_wings" to "Chicken Wings",
        "crispy_chicken" to "Crispy Chicken",
        "donut" to "Donut",
        "fish_and_chips" to "Fish and Chips",
        "french_fries" to "French Fries",
        "fried_rice" to "Fried Rice",
        "hamburger" to "Hamburger",
        "hot_dog" to "Hot Dog",
        "ice_cream" to "Ice Cream",
        "omelette" to "Omelette",
        "pizza" to "Pizza",
        "ramen" to "Ramen",
        "sandwich" to "Sandwich",
        "spaghetti_bolognese" to "Spaghetti Bolognese",
        "spaghetti_carbonara" to "Spaghetti Carbonara",
        "steak" to "Steak",
        "sushi" to "Sushi",
        "taco" to "Taco",
        "takoyaki" to "Takoyaki",
        "taquito" to "Taquito",
        "waffles" to "Waffles",
        "ayam_goreng" to "Ayam Goreng",
        "daging_rendang" to "Daging Rendang",
    )
    val wordMapReverse: Map<String, String> = wordMap.entries.associate { (key, value) -> value to key }
}