package com.jlbennett.syncsports.util

import android.os.Parcel
import android.os.Parcelable


enum class State {
    PRE_MATCH, FIRST_HALF, HALF_TIME, SECOND_HALF, FULL_TIME
}

data class MatchTime(var state: State, var minutes: Int, var seconds: Int, var quarterSeconds: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        State.values()[parcel.readInt()],
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(state.ordinal)
        parcel.writeInt(minutes)
        parcel.writeInt(seconds)
        parcel.writeInt(quarterSeconds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MatchTime> {
        override fun createFromParcel(parcel: Parcel): MatchTime {
            return MatchTime(parcel)
        }

        override fun newArray(size: Int): Array<MatchTime?> {
            return arrayOfNulls(size)
        }
    }

    fun readableString(): String {
        val minString = String.format("%02d", minutes)
        val secString = String.format("%02d", seconds)
        return "$minString:$secString"
    }
}