package fr.isen.manonmartinezcastelbon.sweetsleepapp.model


import java.io.Serializable

class RegistreResult(val data: User) {}

class User(val id: Int): Serializable {}