package com.jlbennett.syncsports

class MatchTime(var state: State, var minutes: Int, var seconds: Int) {
    enum class State {
        PRE_MATCH, FIRST_HALF, HALF_TIME, SECOND_HALF, FULL_TIME
    }
}