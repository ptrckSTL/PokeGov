package com.example.emptyexercise

import androidx.lifecycle.ViewModel
import com.example.emptyexercise.data.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

}