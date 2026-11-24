package com.edsteine.androidfilepicker.permissions.utils

import android.os.Build

object AndroidVersionUtils {
    fun isAtLeastP(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    fun isAtLeastQ(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    fun isAtLeastS(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    fun isAtLeastTiramisu(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
    fun isAtLeastUpsideDownCake(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
    }

    fun isAtLeastVanillaIceCream(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM
    }
}