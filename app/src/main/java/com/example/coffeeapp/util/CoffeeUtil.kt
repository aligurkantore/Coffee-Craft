package com.example.coffeeapp.util

import android.content.Context
import com.example.coffeeapp.R
import com.example.coffeeapp.models.category.Category
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.profile.Language
import javax.inject.Inject

class CoffeeUtil @Inject constructor() {
    fun getCoffeeList(context: Context): MutableList<CoffeeResponseModel> {
        val coffeeList = mutableListOf<CoffeeResponseModel>()
        coffeeList.add(
            CoffeeResponseModel(
                "21",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "22",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "23",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.ice),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "24",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "25",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.white_chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "26",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.vanilla),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "27",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.caramel),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "28",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.hazelnut),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "29",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.caramel),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "30",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)

            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "31",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "32",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.white_chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "33",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.hazelnut),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "34",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "35",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.ice),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "36",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "37",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.vanilla),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "38",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "39",
                context.getString(R.string.turkish_coffee),
                context.getString(R.string.turkish_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.turkish_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "40",
                context.getString(R.string.terebinth_coffee),
                context.getString(R.string.terebinth_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.terebinth_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "41",
                context.getString(R.string.ottoman_coffee),
                context.getString(R.string.ottoman_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.ottoman_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "42",
                context.getString(R.string.colombian_coffee),
                context.getString(R.string.colombian_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.colombian_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.white_chocolate),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "43",
                context.getString(R.string.italian_coffee),
                context.getString(R.string.italian_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.italian_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "44",
                context.getString(R.string.english_coffee),
                context.getString(R.string.english_coffee_description),
                context.getString(R.string.medium_roasted),
                R.drawable.english_coffee,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                3.99,
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "45",
                context.getString(R.string.strawberry_cake),
                context.getString(R.string.description_strawberry_cake),
                context.getString(R.string.medium_roasted),
                R.drawable.cake_1,
                context.getString(R.string.milk),
                null,
                6.99,
                4.3,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "46",
                context.getString(R.string.crepe),
                context.getString(R.string.description_crepe),
                context.getString(R.string.medium_roasted),
                R.drawable.cake_2,
                context.getString(R.string.milk),
                null,
                6.99,
                4.1,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "47",
                context.getString(R.string.waffle),
                context.getString(R.string.description_waffle),
                context.getString(R.string.medium_roasted),
                R.drawable.cake_3,
                context.getString(R.string.milk),
                null,
                6.99,
                4.9,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "48",
                context.getString(R.string.classic_cake),
                context.getString(R.string.classic_cake),
                context.getString(R.string.medium_roasted),
                R.drawable.cake_4,
                context.getString(R.string.milk),
                null,
                6.99,
                4.3,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "49",
                context.getString(R.string.cheesecake),
                context.getString(R.string.description_cheesecake),
                context.getString(R.string.medium_roasted),
                R.drawable.cake_5,
                context.getString(R.string.milk),
                null,
                6.99,
                4.8,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )



        return coffeeList
    }


    fun getCategoryNameList(context: Context): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(Category(null, context.getString(R.string.all)))
        categoryList.add(Category(null, context.getString(R.string.americano)))
        categoryList.add(Category(null, context.getString(R.string.black_coffee)))
        categoryList.add(Category(null, context.getString(R.string.cappuccino)))
        categoryList.add(Category(null, context.getString(R.string.espresso)))
        categoryList.add(Category(null, context.getString(R.string.latte)))
        categoryList.add(Category(null, context.getString(R.string.macchiato)))
        return categoryList
    }

    fun getProfileCategoryList(context: Context): List<Category> {
        val profileCategoryList = mutableListOf<Category>()
        profileCategoryList.add(
            Category(
                R.drawable.personel_information,
                context.getString(R.string.personel_information)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.location,
                context.getString(R.string.my_addresses)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.order_history,
                context.getString(R.string.order_history)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.shopping_bag,
                context.getString(R.string.my_cart)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.like,
                context.getString(R.string.my_favorites)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.payment,
                context.getString(R.string.payment_information)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.language,
                context.getString(R.string.languages)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.about_the_app,
                context.getString(R.string.about_app)
            )
        )
        profileCategoryList.add(
            Category
                (
                R.drawable.log_out,
                context.getString(R.string.log_out)
            )
        )
        return profileCategoryList
    }

    fun getPersonelInformationCategoryList(context: Context): List<Category> {
        val personelInformationCategoryList = mutableListOf<Category>()
        personelInformationCategoryList.add(
            Category(
                null,
                context.getString(R.string.account_information)
            )
        )
        personelInformationCategoryList.add(
            Category(
                null,
                context.getString(R.string.change_password)
            )
        )
        return personelInformationCategoryList
    }

    fun getLanguageList(context: Context): List<Language> {
        val languageList = mutableListOf<Language>()
        languageList.add(Language(context.getString(R.string.english)))
        languageList.add(Language(context.getString(R.string.turkish)))
        languageList.add(Language(context.getString(R.string.german)))
        languageList.add(Language(context.getString(R.string.french)))
        return languageList
    }
}