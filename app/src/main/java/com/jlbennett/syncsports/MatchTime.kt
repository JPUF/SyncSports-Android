package com.jlbennett.syncsports

import android.os.Parcel
import android.os.Parcelable


enum class State {
    PRE_MATCH, FIRST_HALF, HALF_TIME, SECOND_HALF, FULL_TIME
}

data class MatchTime(val state: State, val minutes: Int, val seconds: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        State.values()[parcel.readInt()],
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(state.ordinal)
        parcel.writeInt(minutes)
        parcel.writeInt(seconds)
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
}