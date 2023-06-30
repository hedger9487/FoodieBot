package com.foodiebot.app.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FoodPreferenceViewModel : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val _foodPreferences: MutableState<SnapshotStateList<String>> = mutableStateOf(SnapshotStateList())
    val foodPreferences: SnapshotStateList<String> get() = _foodPreferences.value


    init {
        // Initialize the food preferences with an empty list
        _foodPreferences.value = mutableStateListOf()
    }


    fun updateFoodPreference(food: String, isChecked: Boolean) {
        if (isChecked) {
            _foodPreferences.value.add(food)
        } else {
            _foodPreferences.value.remove(food)
        }
    }


    fun saveFoodPreferences(userId: String) {
        val preferencesRef = database.child("users").child(userId).child("foodPreferences")
        val foodPreferences = _foodPreferences.value.orEmpty()
        preferencesRef.setValue(foodPreferences)
    }
}
