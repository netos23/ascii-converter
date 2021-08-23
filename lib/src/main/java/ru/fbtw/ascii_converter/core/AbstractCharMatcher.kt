package ru.fbtw.ascii_converter.core

abstract class AbstractCharMatcher<P : Comparable<P>> : CharMatcher {
    abstract val charset: List<MatchData<P>>
    abstract val isReady: Boolean
}