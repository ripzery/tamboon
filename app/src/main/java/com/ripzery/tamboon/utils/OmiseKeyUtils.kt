package com.ripzery.tamboon.utils

/**
 * Created by ripzery on 9/16/17.
 */
object OmiseKeyUtils{
    init {
        System.loadLibrary("native-lib")
    }

    external fun pk(): String
}